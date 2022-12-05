package com.garciajuanjo.hibernatebbddh2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan José García Navarrete
 */
@Entity
@Table(name = "user_app")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	public User(String name) {
		this.name = name;
	}

	public User(int id) {
		this.id = id;
	}
}
