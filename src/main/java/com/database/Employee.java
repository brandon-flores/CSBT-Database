package com.database;

public class Employee{
	private String username, password;
	private String firstName, lastName;
	private String workType;
	private boolean activeEmployee = false;
	private String estatus = "ACTIVE";

	public Employee(String username, String password, String firstName, String lastName, String workType, boolean activeEmployee) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.workType = workType;
		this.activeEmployee = activeEmployee;
		estatus = "INACTIVE";
		if(activeEmployee) estatus = "ACTIVE";

	}

	public Employee(String username, String password, String firstName, String lastName, String workType) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.workType = workType;
		activeEmployee = true;	// set default to true because I think you wont add an inactive employee
		// and this constructor is only called when you create a new employee
		//call setter to set it to inactive
		estatus = "ACTIVE";
	}

	public Employee(){

	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public boolean isActiveEmployee() {
		return activeEmployee;
	}

	public void setActiveEmployee(boolean activeEmployee) {
		this.activeEmployee = activeEmployee;
	}


	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}