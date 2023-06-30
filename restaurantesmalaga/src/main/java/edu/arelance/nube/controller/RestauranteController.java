package edu.arelance.nube.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.arelance.nube.repository.entity.Restaurante;
import edu.arelance.nube.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * API WEB HTTP -> Deriva en la ejecucion de un mètodo
 * 
 * GET -> Consultar TODOS GET -> Consultar UNO (por ID) POST -> Insertar un
 * restaurante nuevo PUT -> Modificar un restaurante que ya existe DELETE ->
 * Borrar un restaurante (por ID) BUSQUEDA -> Por barrio, por especialidad, por
 * nombre, etc...
 */

@Controller // Devolvemos una lista vista htmljsp
@RestController // Devolvemos de JSON http://localhost:8081/restaurante/test

@RequestMapping("/restaurante")
public class RestauranteController {

	@Autowired
	RestauranteService restuaranteService;
	
    Logger logger = LoggerFactory.getLogger(RestauranteController.class);

	@GetMapping("/test")
	public Restaurante obtenerRestauranteTest() {
		Restaurante restaurante = null;

//		String saludo = "Hola";
//		saludo.charAt(10);

		System.out.println("llamando a obtenerRestauranteTest");
		logger.debug("Estoy en obtenerRestauranteTest");
		restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque", "www.martinete.org",
				"https://google.xe", 33.65f, -2.3f, 10, "Gazpacheuelo", "paella", "sopa de marisco",
				LocalDateTime.now());

		return restaurante;

	}
	
	private ResponseEntity<?> genererRespuestaErroresValidacion (BindingResult bindingResult){
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores = null;
		
		
		listaErrores = bindingResult.getAllErrors();
		//imprimir todos los errores que nos de el prograa por consola
		listaErrores.forEach(e->logger.error(e.toString()));
		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		
		return responseEntity;
	}

	// GET -> Consultar TODOS GET http://localhost:8081/restaurante/test
	@GetMapping
	public ResponseEntity<?> obtenerListaRestaurantes() {
		ResponseEntity<?> responseEntity = null;
		// ResponseEntity<?> representa un mensaje http

//		String saludo = "Hola";
//		saludo.charAt(10);

		Iterable<Restaurante> lista_Restaurantes = null;

		lista_Restaurantes = this.restuaranteService.consultarTodos();
		responseEntity = ResponseEntity.ok(lista_Restaurantes);// construyendo el mensaje de vuelta

		return responseEntity;
	}

	// GET -> Consultar uno (por ID)
	@Operation(description = "Este metodoservicio permite la consulat de restuarantes por un ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> ListarID(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> or = null;
		or = this.restuaranteService.consultarRestaurante(id);
		if (or.isPresent()) {
			// La consulta ha recuperado un registro
			Restaurante restauranteLeido = or.get();
			responseEntity = responseEntity.ok(restauranteLeido);
			logger.debug("Recuperado el registro" +restauranteLeido);
		}

		else {
			// La consulta no ha recurado un registro
			responseEntity = ResponseEntity.noContent().build();
			logger.debug("El restaurante con id "+id+" no existe");
		}

		return responseEntity;
	}

	// POST -> Insertar un nuevvo restaurante
	@PostMapping
	public ResponseEntity<?> insertarRestaurante(@Valid @RequestBody Restaurante restaurante, BindingResult bindingresult) {
		ResponseEntity<?> responseEntity = null;
		Restaurante restauranteNuevo = null;
 //TODO Validar
		if(bindingresult.hasErrors()) {
			logger.debug("Errores en la entrada POST");
			responseEntity = genererRespuestaErroresValidacion(bindingresult);
		}
		
		else {
			logger.debug("Sin errores en la entrada POST");
			restauranteNuevo = this.restuaranteService.altaRestaurante(restaurante);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
		}
		
		return responseEntity;
	}

	// PUT-> modificar un restuarante en nla bbdd

	@PutMapping("/{id}")
	public ResponseEntity<?> modificarRestaurante(@Valid @RequestBody Restaurante restaurante, @Valid @PathVariable Long id, BindingResult bindingresult) {
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> opRest = null;
        if(bindingresult.hasErrors()) {
        	logger.debug("Errores en la entrada PUT");
        	responseEntity = genererRespuestaErroresValidacion(bindingresult);
        }
        
        else {
        	logger.debug("No hay Errores en la entrada PUT");
        }
		opRest = this.restuaranteService.modificarRestaurante(id, restaurante);

		if (opRest.isPresent()) {
			Restaurante rm = opRest.get();
			responseEntity = ResponseEntity.ok(rm);
		}

		else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return responseEntity;
	}

	// DELETE -> Consultar uno (por ID) http://localhost:8081/restaurante/test
	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarPorID(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;

		this.restuaranteService.borrarRestaurante(id);
		responseEntity = ResponseEntity.ok().build();

		return responseEntity;
	}

	// Buscar restaurante por rango de  precio
	@GetMapping("/buscarPorPrecio")
	public ResponseEntity<?> listarPorRangoPrecio (
			@RequestParam(name = "preciomin") int preciomin,
			@RequestParam(name = "preciomax") int preciomax)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> lista_Restaurantes = null;
			
			lista_Restaurantes = this.restuaranteService.buscarRestauranteRangoPrecio(preciomin, preciomax);
			responseEntity = ResponseEntity.ok(lista_Restaurantes);
		
		return responseEntity;
	}
	
//	//Buscar restaurante por especialidad
//	@GetMapping("/buscarPorEspecialidad")
//	public ResponseEntity<?> listaPorEspecialidad (
//		@RequestParam(name = "first one") String esp1,
//		@RequestParam(name = "second one") String esp2,
//		@RequestParam(name = "third one") String esp3
//			) 
//	{ 
//		ResponseEntity<?> responseEntity = null;
//		Iterable<Re>
//		
//		
//		
//		return responseEntity;
//	}
	
	//http://localhost:8081/restaurante/buscarPorClave/pimientos
	@GetMapping("/buscarPorClave")
	public ResponseEntity<?> filtrarPorBarrioNombreEspecialidad(@RequestParam (name="clave") String clave){
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> listRest = null;
		
		listRest = restuaranteService.buscarPorBarrioNombreEspecialidad(clave);
		responseEntity = ResponseEntity.ok(listRest);
		
		
		
		return responseEntity;
	}

}
