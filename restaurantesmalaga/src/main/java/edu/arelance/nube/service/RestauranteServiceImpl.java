package edu.arelance.nube.service;

import java.util.Optional;

import javax.swing.Spring;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

//import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.arelance.nube.repository.RestauranteRepository;
import edu.arelance.nube.repository.entity.Restaurante;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	@Autowired
	RestauranteRepository restauranteRepository;

	@Override
	@Transactional(readOnly = true) // permitimos acceso concurrente a la tabla Restaurantes
	public Iterable<Restaurante> consultarTodos() {
		// TODO Auto-generated method stub
		return this.restauranteRepository.findAll();
		// return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Restaurante> consultarRestaurante(Long id) {
		// TODO Auto-generated method stub
		return this.restauranteRepository.findById(id);
		// return null;
	}

	@Override
	@Transactional

	public Restaurante altaRestaurante(Restaurante restaurante) {
		// TODO Auto-generated method stub
		return this.restauranteRepository.save(restaurante);
		// return null;
	}

	@Override
	@Transactional
	public void borrarRestaurante(Long id) {
		// TODO Auto-generated method stub
		this.restauranteRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<Restaurante> modificarRestaurante(Long id, Restaurante restaurante) {
		Optional<Restaurante> opRest = Optional.empty();

		// 1 LEER
		opRest = this.restauranteRepository.findById(id);
		if (opRest.isPresent()) {
			// Al estar dentro de una transacción, restauranteLeido está asociado
			// a un registro de la tabla. Si modifico un campo, estoy modificando
			// la columna asociada (Estado "Persistent" - JPA)
			Restaurante restauranteLeido = opRest.get();
			// restauranteLeido.setNombre(restaurante.getNombre());
			BeanUtils.copyProperties(restaurante, restauranteLeido, "id", "creadoEn");
			opRest = Optional.of(restauranteLeido);// "relleno el Optional"
		}
		// 2 ACTUALIZAR

		return opRest;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> buscarRestauranteRangoPrecio(int pmin, int pmax) {
		// TODO Auto-generated method stub
		Iterable<Restaurante> lRest = null;

		lRest = this.restauranteRepository.findByprecioBetween(pmin, pmax);

		return lRest;
	}

//	@Override
//	public Iterable<Restaurante> buscarPorEspecialidad(String esp1, String esp2, String esp3) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//    
	@Override
	public Iterable<Restaurante> buscarPorBarrioNombreEspecialidad(String clave) {
		// TODO Auto-generated method stub
		Iterable<Restaurante> lRest = null;

		lRest = this.restauranteRepository.buscarPorBarrioNombreOEspecialidad(clave);
		

		return lRest;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> buscarPorEspecialidad(String esp1, String esp2, String esp3) {
		// TODO Auto-generated method stub
		Iterable<Restaurante> lRest = null;
		
		//lRest = this.restauranteRepository.findByEspecialidad1orEspecialidad2orEspecialidad3(esp1, esp2, esp3);
		
		
		return lRest;
	}

}
