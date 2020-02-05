package model.service;

import java.util.List;

import db.DbIntegrityException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao sellerDao = DaoFactory.createSellerDao();

	public List<Seller> findAll(){
		return sellerDao.findAll();	
	}

	public void saveOrUpdate(Seller seller) {
		if(seller.getId() == null) {
			sellerDao.insert(seller);
		}else {
			sellerDao.update(seller);
		}
		
	}
	
	public void remove(Seller seller) throws DbIntegrityException {
		sellerDao.deleteById(seller.getId());
	}
	
}
