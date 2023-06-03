package com.met.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.met.model.Employee;

@Repository
public class EmployeeDao {
	
	private Map<Integer, Employee> mapEmployee = new HashMap<>();
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveUsingDataSource(Employee employee) {
		
		try(Connection con = datasource.getConnection();
				PreparedStatement pstmt = con.prepareStatement("insert into EmployeeTbl values(?,?,?,?)")){
			
			pstmt.setInt(1, employee.getId());
			pstmt.setString(2, employee.getName());
			pstmt.setString(3, employee.getDesignation());
			pstmt.setString(4, employee.getEmailid());
			
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveUsingJDBCTemplate(Employee employee) {
		
		System.out.println("saving employee using jdbc template");
		
		jdbcTemplate.update("insert into EmployeeTbl values(?,?,?,?)",
				new Object[] {employee.getId(),employee.getName(),
						employee.getDesignation(),employee.getEmailid()});
	}
	
	
	public void saveEmployee(Employee employee) {
		
		System.out.println("EmployeeDao :: saveEmployee" + employee);
		//mapEmployee.put(employee.getId(), employee);
		
		//saveUsingDataSource(employee);
		
		saveUsingJDBCTemplate(employee);
		
		//save it into db
	}
	
//	class BeanPropertyRowMapper implements RowMapper<Employee>{
//		
//		public Employee mapRow(ResultSet rs,int arg1) throws SQLException {
//			
//			Employee emp = new Employee();
//			emp.setId(rs.getInt(1));
//			emp.setName(rs.getString(2));
//			emp.setDesignation(rs.getString(3));
//			emp.setEmailid(rs.getString(4));
//			
//			return emp;
//		}
//	}

	
	public Collection<Employee> getAllEmployees(){
		
		
		return jdbcTemplate.query("select * from employeeTbl", 
				new BeanPropertyRowMapper<Employee>(Employee.class));
		
		//return mapEmployee.values();
	}

}
