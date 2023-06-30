package edu.arelance.nube.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.arelance.nube.repository.entity.Restaurante;
import java.util.List;


@Repository
public interface RestauranteRepository extends CrudRepository<Restaurante, Long>{
	//1 KEY WORD QUERIES - Consultas por palabras clave
   Iterable<Restaurante> findByprecioBetween(int preciomin, int preciomax); //consultar restauarntes por rango de precio
   
   //Iterable<Restaurante> findByclaveIgnoreCase (String clave);
	   
   
	//2 JPQL - HQL - Pseudo SQL  pero de JAVA - "Agnostico"
	//3 NATIVAS - SQL
   
   @Query(value = "SELECT * FROM bdrestaurantes.restaurantes WHERE barrio LIKE %?1% OR nombre LIKE %?1% OR especialidad1 LIKE %?1% OR especialidad2 LIKE %?1% OR especialidad3 LIKE %?1% ", nativeQuery = true)
	Iterable<Restaurante> buscarPorBarrioNombreOEspecialidad (String clave);
	//4 STORED PROCEDURES - Procedimientos Almacenados
	//CRITERIA API - SQL  pero con étodos de JAVA https://www.arquitecturajava.criteriaapi
}
