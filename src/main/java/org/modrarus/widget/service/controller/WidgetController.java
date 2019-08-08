package org.modrarus.widget.service.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.modrarus.widget.service.model.Area;
import org.modrarus.widget.service.model.Widget;
import org.modrarus.widget.service.model.WidgetNotExistException;
import org.modrarus.widget.service.model.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WidgetController {
	/**
	 * Хранилище виджетов
	 */
	private WidgetRepository repository;
	
	@Autowired
	public WidgetController(final WidgetRepository _repository) {
		repository = _repository;
	}
	
	/**
	 * Запрос создания виджета
	 * @param _widget Описание нового виджета без идентификатора
	 * @return Новый виджет
	 */
	@PostMapping(value = "/api/v0.1/widgets/add",
			produces="application/json", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Widget add(final @Validated(Widget.NewWidget.class) @RequestBody Widget _widget) {
		return repository.save(_widget);
	}
	
	/**
	 * Обновление существующего виджета
	 * @param _widget Виджет с обновленными данными
	 * @return Обновленный виджет
	 */
	@PostMapping(value = "/api/v0.1/widgets/update",
			produces="application/json", consumes="application/json")
	public Widget update(final @Validated(Widget.ExistedWidget.class) @RequestBody Widget _widget) {
		return repository.save(_widget);
	}
	
	/**
	 * Удаление виджета
	 * @param _id Идентификатор удаляемого виджета
	 */
	@DeleteMapping("/api/v0.1/widgets/delete/{widgetId}")
	public void delete(@PathVariable("widgetId") final Long _id) {
		if (!repository.deleteById(_id)) {
			throw new WidgetNotExistException(_id);
		}
	}
	
	/**
	 * Получение списка всех виджетов
	 * @return Список всех виджетов
	 */
	@GetMapping(value = "/api/v0.1/widgets/list", produces="application/json")
	public List<Widget> getAll() {
		return repository.getAll();
	}
	
	/**
	 * Получение списка всех виджетов в заданной прямоугольной зоне
	 * @return Список всех виджетов
	 */
	@GetMapping(value = "/api/v0.1/widgets/list/area", produces="application/json")
	public List<Widget> getAllInArea(@RequestParam(name = "xmin") @NotNull final Long _xMin,
			@RequestParam(name = "xmax") @NotNull final Long _xMax,
			@RequestParam(name = "ymin") @NotNull final Long _yMin,
			@RequestParam(name = "ymax") @NotNull final Long _yMax) {
		return repository.getAllInArea(new Area(_xMin, _xMax, _yMin, _yMax));
	}
	
	/**
	 * Получение виджета по идентификатору
	 * @param _id Идентификатор
	 * @return Виджет/null при его отсутствии
	 */
	@GetMapping(value = "/api/v0.1/widgets/get/{widgetId}", produces="application/json")
	public Widget getWidget(@PathVariable("widgetId") final Long _id) {
		return repository.getById(_id).orElseThrow(() -> new WidgetNotExistException(_id));
	}
}