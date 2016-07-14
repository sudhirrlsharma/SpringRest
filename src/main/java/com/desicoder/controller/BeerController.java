package com.desicoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desicoder.dao.BeerDao;
import com.desicoder.model.Beer;
import com.desicoder.model.DomesticBeer;

@RestController
@RequestMapping("/beers")
public class BeerController {
	
	@Autowired
	private BeerDao beerDao;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getBeers() {
    	return beerDao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Beer getBeerById(@RequestParam(value="id", defaultValue="1") long id) {
    	return beerDao.findOne(id);
    }

    @RequestMapping(value = "/brand/{brand}", method = RequestMethod.GET)
    public Beer  getBeerByBrand( @PathVariable("brand") String brand) {
    	return beerDao.findByBrand(brand);
    }
    
    
    @RequestMapping(method = RequestMethod.PUT)
    public void  saveBeer( @RequestBody DomesticBeer beer) {
    	beerDao.saveBeer(beer);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void  updateBeer( @RequestBody DomesticBeer beer) {
    	 beerDao.saveBeer(beer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void   deleteBeer( @PathVariable("id") int id) {
    	beerDao.deleteBeer(id);
    }

}