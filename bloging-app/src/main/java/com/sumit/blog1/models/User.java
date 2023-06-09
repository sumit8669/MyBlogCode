package com.sumit.blog1.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Setter
@Getter
public class User {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int id;
	
	@Column(name ="user_name", nullable = false , length =70)
	private String name;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	
	private String about;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>(); 	

}
