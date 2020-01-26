package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	
	
	public Department() {
		
	}

	/**
	 * @param id
	 * @param name
	 */
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Department))
			return false;
		
		Department other = (Department) obj;
		
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "id= " + id + " - name= " + name;
	}
	
	
	
}
