package org.modrarus.widget.service.model;

/**
 * Контейнер объекта-виджета
 */
public final class Widget {
	/**
	 * Идентификатор
	 */
	private Long id;
	/**
	 * zIndex
	 */
	private Long zIndex;
	/**
	 * Позиция по оси абсцисс
	 */
	private long x;
	/**
	 * Позиция по оси ординат
	 */
	private long y;
	/**
	 * Ширина
	 */
	private long width;
	/**
	 * Высота
	 */
	private long heidth;
	
	/**
	 * Полный конструктор существующего виджета
	 * @param _id     Идентификатор
	 * @param _zIndex z-index
	 * @param _x      Положение по оси абсцисс
	 * @param _y      Положение по оси ординат
	 * @param _width  Ширина
	 * @param _heidth Высота
	 */
	Widget(final Long _id, final Long _zIndex,
			final long _x, final long _y, final long _width, final long _heidth) {
		id     = _id;
		zIndex = _zIndex;
		x      = _x;
		y      = _y;
		width  = _width;
		heidth = _heidth;
	}
	
	/**
	 * Конструтор для построения нового виджета
	 * @param _zIndex z-index
	 * @param _x      Положение по оси абсцисс
	 * @param _y      Положение по оси ординат
	 * @param _width  Ширина
	 * @param _heidth Высота
	 */
	public Widget(final Long _zIndex, final long _x, final long _y,
			final long _width, final long _heidth) {
		this(null, _zIndex, _x, _y, _width, _heidth);   
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
	public long getX() {
		return x;
	}
	
	/**
	 * Получение позиции по оси ординат
	 * @return Положение по оси ординат
	 */
	public long getY() {
		return y;
	}
	
	/**
	 * Получение ширины
	 * @return Ширина
	 */
	public long getWidth() {
		return width;
	}
	
	/**
	 * Получение высоты
	 * @return Высота
	 */
	public long getHeidth() {
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
}