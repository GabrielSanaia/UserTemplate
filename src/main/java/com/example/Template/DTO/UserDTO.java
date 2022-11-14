package com.example.Template.DTO;

public class UserDTO {

	private String name;
	private String surname;
	
	public UserDTO(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public UserDTO() {
		
	}
	
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", surname=" + surname + "]";
	}
	
	
	
}
