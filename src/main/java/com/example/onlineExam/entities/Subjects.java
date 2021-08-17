package com.example.onlineExam.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Subjects")
public class Subjects implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "Sub_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sub_Id;
	@Column(name = "SubName")
	private String subName;
	@Column(name = "Enable")
	private boolean  enable;
	
	@OneToMany(mappedBy = "subject",fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference(value = "listExams")
	private Collection<Exams> listExams;
	
	public Subjects() {
		// TODO Auto-generated constructor stub
	}

	public Subjects(Integer sub_Id, String subName, boolean enable, Collection<Exams> listExams) {
		super();
		this.sub_Id = sub_Id;
		this.subName = subName;
		this.enable = enable;
		this.listExams = listExams;
	}

	public Subjects(String subName, boolean enable) {
		super();
		this.subName = subName;
		this.enable = enable;
	}

	public Integer getSub_Id() {
		return sub_Id;
	}

	public void setSub_Id(Integer sub_Id) {
		this.sub_Id = sub_Id;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Collection<Exams> getListExams() {
		return listExams;
	}

	public void setListExams(Collection<Exams> listExams) {
		this.listExams = listExams;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
