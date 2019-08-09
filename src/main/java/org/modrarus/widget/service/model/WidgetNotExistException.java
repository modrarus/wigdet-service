package org.modrarus.widget.service.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение при попытке работать с несуществующим виджетом
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WidgetNotExistException extends RuntimeException {
	private static final long serialVersionUID = -8077703245124671650L;
	
	private Long id;
	
	public WidgetNotExistException(final Long _id) {
		super("Виджет с идентификатором " + _id + " не существует.");
		id = _id;
	}
	
	/**
	 * Получение идентификатора несуществующего виджета
	 * @return Идентификатор
	 */
	public Long getNotExistWidgetId() {
		return id;
	}
}