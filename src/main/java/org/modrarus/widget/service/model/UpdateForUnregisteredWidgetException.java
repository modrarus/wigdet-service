package org.modrarus.widget.service.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение при попытке обновления виджета с id не зарегистрированным в репозитории
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpdateForUnregisteredWidgetException extends RuntimeException {
	private static final long serialVersionUID = -4231420264053639352L;
	
	private Widget unregitered;
	
	public UpdateForUnregisteredWidgetException(final Widget _unregistered) {
		super("Невозможно обновить виджет не сохраненный в хранилище виджетов.");
		unregitered = _unregistered;
	}
	
	/**
	 * Получение виджета, попытка обновления которого привела к ошибке
	 * @return Виджет
	 */
	public Widget getUnregisteredWidget() {
		return unregitered;
	}
}