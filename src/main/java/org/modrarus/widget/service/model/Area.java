package org.modrarus.widget.service.model;

import javax.validation.constraints.NotNull;

/**
 * Зона расположения виджета
 */
public class Area {
	@NotNull(message = "Начальная позиция зоны по оси абсцисс не определена")
	private Long xMin;
	@NotNull(message = "Конечная позиция зоны по оси абсцисс не определена")
	private Long xMax;
	@NotNull(message = "Начальная позиция зоны по оси ординат не определена")
	private Long yMin;
	@NotNull(message = "Конечная позиция зоны по оси ординат не определена")
	private Long yMax;
	
	/**
	 * Основной конструктор зоны
	 * @param _xMin Начальная позиция зоны по оси абсцисс
	 * @param _xMax Конечная позиция зоны по оси абсцисс
	 * @param _yMin Начальная позиция зоны по оси абсцисс
	 * @param _yMax Конечная позиция зоны по оси абсцисс
	 */
	public Area(final Long _xMin, final Long _xMax, final Long _yMin, final Long _yMax) {
		if (_xMin == null || _xMax == null || _yMin == null || _yMax == null) {
			throw new NullPointerException("Размеры целевой зоны должны быть определены полностью");
		}
		xMin = _xMin;
		xMax = _xMax;
		yMin = _yMin;
		yMax = _yMax;
	}
	
	/**
	 * Проверка попадания виджета в зону целиком (включая границы)
	 * @param _widget Приверяемый виджет
	 * @return Успешность попадания
	 */
	public boolean conteinWidget(final Widget _widget) {
		if (_widget == null) {
			return false;
		}
		
		final double halfWidth  = _widget.getWidth().doubleValue() / 2;
		final double halfHeidth = _widget.getHeidth().doubleValue() / 2;
		
		return xMin <= _widget.getX().doubleValue() - halfWidth &&
				xMax >= _widget.getX().doubleValue() + halfHeidth &&
				yMin <= _widget.getY().doubleValue() - halfHeidth &&
				yMax >= _widget.getY().doubleValue() + halfHeidth;
	}
}
