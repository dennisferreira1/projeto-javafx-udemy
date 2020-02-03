package model.service;

import java.util.List;

import db.DbIntegrityException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

	public List<Department> findAll(){
		return departmentDao.findAll();	
	}

	public void saveOrUpdate(Department department) {
		if(department.getId() == null) {
			departmentDao.insert(department);
		}else {
			departmentDao.update(department);
		}
		
	}
	
	public void remove(Department department) throws DbIntegrityException {
		departmentDao.deleteById(department.getId());
	}
	
}
