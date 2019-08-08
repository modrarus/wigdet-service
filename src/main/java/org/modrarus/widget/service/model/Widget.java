package org.modrarus.widget.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Контейнер объекта-виджета
 */
public final class Widget {
	/**
	 * Группа условий для нового виджета
	 */
	public static interface NewWidget{}
	/**
	 * Группа условий для существующего виджета
	 */
	public static interface ExistedWidget{}
	
	/**
	 * Идентификатор
	 */
	@Null(message = "При создании нового виджета не может быть указан идентификатор",
			groups = {NewWidget.class})
	@NotNull(message = "Идентификатор виджета должен быть определен", groups = {ExistedWidget.class})
	private final Long id;
	/**
	 * zIndex
	 */
	@NotNull(message = "Положение виджета по zIndex должно быть определено",
			groups = {NewWidget.class, ExistedWidget.class})
	private final Long zIndex;
	/**
	 * Позиция по оси абсцисс
	 */
	@NotNull(message = "Положение виджета по оси абсцисс должно быть определено",
			groups = {NewWidget.class, ExistedWidget.class})
	private final Long x;
	/**
	 * Позиция по оси ординат
	 */
	@NotNull(message = "Положение виджета по оси ординат должно быть определено",
			groups = {NewWidget.class, ExistedWidget.class})
	private final Long y;
	/**
	 * Ширина
	 */
	@NotNull(message = "Ширина виджета должна быть определена",
			groups = {NewWidget.class, ExistedWidget.class})
	private final Long width;
	/**
	 * Высота
	 */
	@NotNull(message = "Высота виджета должна быть определена",
			groups = {NewWidget.class, ExistedWidget.class})
	private final Long heidth;
	
	/**
	 * Полный конструктор существующего виджета
	 * @param _id     Идентификатор
	 * @param _zIndex z-index
	 * @param _x      Положение по оси абсцисс
	 * @param _y      Положение по оси ординат
	 * @param _width  Ширина
	 * @param _heidth Высота
	 */
	public Widget(final Long _id,
			final Long _zIndex,
			final Long _x,
			final Long _y,
			final Long _width,
			final Long _heidth) {
		if (_id == null || _zIndex == null ||_x == null || _y == null || _width == null || _heidth == null) {
			throw new NullPointerException(
					"В существующем виджете должны быть определены все поля");
		}
		id     = _id;
		zIndex = _zIndex;
		x      = _x;
		y      = _y;
		width  = _width;
		heidth = _heidth;
	}
	
	/**
	 * Конструктор нового виджета
	 * @param _id     Идентификатор
	 * @param _zIndex z-index
	 * @param _x      Положение по оси абсцисс
	 * @param _y      Положение по оси ординат
	 * @param _width  Ширина
	 * @param _heidth Высота
	 */
	public Widget(final Long _zIndex,
			final Long _x,
			final Long _y,
			final Long _width,
			final Long _heidth) {
		if (_x == null || _y == null || _width == null || _heidth == null) {
			throw new NullPointerException(
					"Для создания нового виджета должы быть определены: положение по оси ординат, " +
					"положение по оси абсцисс, ширина и высота.");
		}
		
		id     = null;
		zIndex = _zIndex;
		x      = _x;
		y      = _y;
		width  = _width;
		heidth = _heidth;
	}
	
	/**
	 * Получение идентификатора
	 * @return Идентификатор
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Получение z-index
	 * @return z-index
	 */
	public Long getZIndex() {
		return zIndex;
	}
	
	/**
	 * Получение положения по оси абсцисс
	 * @return Положение по оси абсцисс
	 */
	public Long getX() {
		return x;
	}
	
	/**
	 * Получение позиции по оси ординат
	 * @return Положение по оси ординат
	 */
	public Long getY() {
		return y;
	}
	
	/**
	 * Получение ширины
	 * @return Ширина
	 */
	public Long getWidth() {
		return width;
	}
	
	/**
	 * Получение высоты
	 * @return Высота
	 */
	public Long getHeidth() {
		return heidth;
	}
	
	/**
	 * Проверка регистрации в хранилище путем проверки наличия идентификатора хранилища
	 * @return true для зарегистрированного виждета, false для незарегистрированного
	 */
	public boolean hasId() {
		return id != null;
	}
	
	/**
	 * Проверка определения z-index
	 * @return Флаг наличия определения
	 */
	public boolean hasZIndex() {
		return zIndex != null;
	}
	
	/**
	 * Регистрация в хранилище виджета
	 * @param _id Идентификатор виджета
	 * @return Новый экземпляр зарегистрированного виджета
	 */
	Widget register(final Long _id) {
		return new Widget(_id, zIndex, x, y, width, heidth);
	}
	
	/**
	 * Регистрация в хранилище нового виждета с доопределением zIndex
	 * @param _id     Идентификатор виджета
	 * @param _zIndex zIndex виджета
	 * @return Новый экземпляр зарегистрированного виджета
	 */
	Widget register(final Long _id, final Long _zIndex) {
		return new Widget(_id, zIndex, x, y, width, heidth);
	}
	
	/**
	 * Переупорядочивание виджета по zIndex
	 * Увеличивает значение zIndex на единицу
	 * @return Новый экземпляр зарегистрированного виджета 
	 */
	Widget reorderZIndex() {
		return new Widget(id, Long.valueOf(zIndex.longValue() + 1), x, y, width, heidth);
	}
}