package ca.sheridancollege.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user_role", uniqueConstraints=@UniqueConstraint(columnNames= {"role","userid"}))
public class UserRole {

	@Id
	@GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
	@Column(name="user_role_id", unique=true, nullable=false)
	private int role_id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid", nullable=true)
	private User user;
	
	@Column(name="role", nullable=false, length=45)
	private String role;

	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
	
	
}
