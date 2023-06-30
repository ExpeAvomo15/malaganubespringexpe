package edu.arelance.nube.service;

import java.util.Optional;

import edu.arelance.nube.repository.entity.Restaurante;

public interface RestauranteService {
	
Iterable<Restaurante> consultarTodos ();

Optional<Restaurante> consultarRestaurante (Long id);

Restaurante altaRestaurante (Restaurante restaurante);

void borrarRestaurante (Long id);

Optional<Restaurante> modificarRestaurante (Long id, Restaurante restaurante);

// consultar restaurantes por rang
Iterable<Restaurante> buscarRestauranteRangoPrecio (int pmin, int pmax);

//Buscar por especialidad
Iterable<Restaurante> buscarPorEspecialidad (String esp1, String esp2, String esp3);

//Buscar por palabra clave
Iterable<Restaurante> buscarPorBarrioNombreEspecialidad(String clave);

}
