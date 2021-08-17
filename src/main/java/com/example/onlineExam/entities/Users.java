package com.example.onlineExam.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Users")
public class Users implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="U_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer u_Id;
	@Column(name="FullName")
	private String fullName;
	@Column(name="Username",unique = true)
	private String username;
	@Column(name="Password")
	private String password;
	@Column(name="Email",unique = true)
	@Email
	private String email;
	@Column(name="Role")
	private String role;
	@Column(name="Enable")
	private boolean enable;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference(value = "user")
	private Collection<Papers> listPapers;
	
	public Users() {
		// TODO Auto-generated constructor stub
	}

	public Users(Integer u_Id, String fullName, String username, String password, @Email String email,
			String role, boolean enable, Collection<Papers> listPapers) {
		super();
		this.u_Id = u_Id;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.enable = enable;
		this.listPapers = listPapers;
	}

	public Users(String fullName, String username, String password, @Email String email, String role, boolean enable) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.enable = enable;
	}

	public Integer getU_Id() {
		return u_Id;
	}

	public void setU_Id(Integer u_Id) {
		this.u_Id = u_Id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Collection<Papers> getListPapers() {
		return listPapers;
	}

	public void setListPapers(Collection<Papers> listPapers) {
		this.listPapers = listPapers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
