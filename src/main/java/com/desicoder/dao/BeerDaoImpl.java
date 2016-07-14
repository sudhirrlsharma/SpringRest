package com.desicoder.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.desicoder.model.Beer;
import com.desicoder.model.DomesticBeer;

@Repository
@Transactional(readOnly = true)
public class BeerDaoImpl extends BaseDao implements BeerDao{
	
	public Beer findOne(Long id) {
		return (Beer)this.hibernateTamplate.get(DomesticBeer.class, id);
	}

	public List<Beer> findAll() {
		return (List<Beer>) this.hibernateTamplate.find("From DomesticBeer");
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class})
	public void saveBeer(Beer object) {	
		this.hibernateTamplate.saveOrUpdate(object);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteBeer(Integer beerId) {
		this.hibernateTamplate.delete(this.hibernateTamplate.load(DomesticBeer.class, beerId));
	}

	@Override
	public Beer findByBrand(String brand) {
		List<Beer> beers = (List<Beer>) getSession().createQuery("From DomesticBeer b where b.brand = :brand").setParameter("brand", brand).list();
		if(beers.isEmpty()){
			throw new RuntimeException("List is Empty");
		}
		return beers.get(0);
	}

}
