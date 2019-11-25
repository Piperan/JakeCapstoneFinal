package ca.sheridancollege.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
	@Column(name="userid", unique=true, nullable=false)
	private int userid;

	@Column(name="username",unique=true,nullable=false, length=45)
	private String username;
	
	@Column(name="password", nullable=false, length=60)
	private String password;
	
	@Column(name="email", nullable=false, length=60)
	private String email;
	
	@Column(name="enabled",nullable=false)
	private boolean enabled;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	
	@Column(name="firstName", nullable=false, length=60)
	private String firstName;
	
	@Column(name="lastName", nullable=false, length=60)
	private String lastName;
	
	@Column(name="address", nullable=false, length=60)
	private String address;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@Column(name="projects", nullable=true, length=60)
	private List<Project> projects = new ArrayList<Project>();
	
	public User(String username, String password, String email, String firstName, String lastName, String address) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public User(String username, String password, String email, boolean enabled, Set<UserRole> userRoles, String firstName,
			String lastName, String address) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.userRoles = userRoles;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public User(String username, String password, String email, boolean enabled, String firstName, String lastName, String address) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}	
}