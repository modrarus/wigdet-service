package org.modrarus.widget.service.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Реализация репозитория виджетов
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class WidgetRepositoryImpl implements WidgetRepository {
	/**
	 * Последний выданный идентификатор. Используется для генерации идентификаторов
	 */
	private AtomicLong lastId = new AtomicLong(0);
	/**
	 * Максимальный существующий zIndex
	 */
	private AtomicLong maxZIndex = new AtomicLong(Long.MIN_VALUE);
	/**
	 * Коллекция всех виджетов
	 */
	private Map<Long, Widget> data = new HashMap<>();
	/**
	 * Индекс по zIndex
	 */
	private Map<Long, Long>   index = new HashMap<>();
	/**
	 * Локер
	 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Override
	public Widget save(final Widget _widget) {
		lock.writeLock().lock();
		try {
			if (_widget.hasId() && !data.containsKey(_widget.getId())) {
				throw new UpdateForUnregisteredWidgetException(_widget);
			}
			
			final Long newId     = _widget.hasId() ? null : lastId.incrementAndGet();
			final Long newZIndex = _widget.hasZIndex() ? null : maxZIndex.incrementAndGet();
			
			final Widget widget = newId == null ? _widget :
				newZIndex == null ? _widget.register(newId) :
					_widget.register(newId, newZIndex);
			
			//Переупорядочивание zIndex в случае необходимости
			if (newZIndex == null && maxZIndex.get() > widget.getZIndex().longValue()) {
				reorderZIndex(widget);
			}
			
			//При обновлении удаление усларевшего индекса
			if (newId == null) {
				index.remove(data.get(widget.getId()).getZIndex());
			}
			
			data.put(widget.getId(), widget);
			index.put(widget.getZIndex(), widget.getId());
			
			//Обновление максимального zIndex в случае необходимости
			if (newZIndex == null && widget.getZIndex().longValue() > maxZIndex.get()) {
				maxZIndex.set(widget.getZIndex());
			}
			
			return widget;
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * Переупорядочивание zIndex в случае необходимости
	 * @param _widget Сохраняемый виджет
	 */
	private void reorderZIndex(final Widget _widget) {
		final Long existedId = index.get(_widget.getZIndex());
		if (existedId == null || existedId.equals(_widget.getId())) {
			return;
		}
		
		final List<Widget> reorders = new ArrayList<Widget>();
		for (final Widget widget : data.values()) {
			if (widget.getZIndex().compareTo(_widget.getZIndex()) <= 0) {
				reorders.add(widget);
			}
		}
		
		for (final Widget reorder : reorders) {
			final Widget reordered = reorder.reorderZIndex();
			data.put(reordered.getId(), reordered);
			index.remove(reorder.getZIndex());
			index.put(reordered.getZIndex(), reordered.getId());
		}
		
		//Поскольку смещаем всех на единицу, то и максимум смещаем на 1
		maxZIndex.incrementAndGet();
	}
	
	@Override
	public Optional<Widget> getById(final Long _id) {
		if (_id == null || lastId.get() > _id.longValue()) {
			return Optional.empty();
		}
		
		lock.readLock().lock();
		try {
			return Optional.ofNullable(data.get(_id));
		} finally {
			lock.readLock().unlock();
		}
	}
	
	@Override
	public boolean delete(final Widget _widget) {
		if (_widget == null || !_widget.hasId()) {
			return false;
		}
		
		lock.readLock().lock();
		try {
			return deleteById(_widget.getId());
		} finally {
			lock.readLock().unlock();
		}
	}
	
	@Override
	public boolean deleteById(final Long _id) {
		if (_id == null || lastId.get() > _id.longValue()) {
			return false;
		}
		
		lock.writeLock().lock();
		try {
			final Widget existed = data.remove(_id);
			if (existed == null) {
				return false;
			}
			
			//Удаление индекса
			index.remove(existed.getZIndex());
			if (data.isEmpty()) {
				maxZIndex.set(Long.MIN_VALUE);
			} else if (maxZIndex.get() == existed.getZIndex().longValue()) {
				maxZIndex.set(Collections.max(index.keySet()).longValue());
			}
			
			return true;
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public List<Widget> getAll() {
		lock.readLock().lock();
		try {
			final List<Widget> collection = new ArrayList<Widget>(data.values());
			collection.sort(new Comparator<Widget>() {
				@Override
				public int compare(final Widget _first, final Widget _second) {
					return _first.getZIndex().compareTo(_second.getZIndex());
				}
			});
			return collection;
		} finally {
			lock.readLock().unlock();
		}
	}
}
