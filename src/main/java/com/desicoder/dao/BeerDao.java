package com.desicoder.dao;

import java.util.List;

import com.desicoder.model.Beer;


public interface BeerDao {
	
	Beer findOne(Long id);
	List<Beer> findAll(); 
	Beer findByBrand(String brand);
	void saveBeer(Beer object );
	void deleteBeer(Integer beerId);
	
}
