package ca.sheridancollege.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="form")
public class Form {
	
	@Id
	@GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
	@Column(name="formid", unique=true, nullable=false)
	private int formid;

	@Column(name="formName", nullable=false, length=60)
	private String formName;
	
	@Column(name="urlPath", nullable=false, length=250)
	private String urlPath;
	
	@Column(name="content", nullable=true, columnDefinition="LONGBLOB")
	private byte[] content;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@Column(name="projects", nullable=true, length=60)
	private List<Project> projects = new ArrayList<Project>();
	public Form(String formName, byte[] content) {
		this.formName = formName;
		this.content = content;
	}
	public Form(String formName, String urlPath) {
		this.formName = formName;
		this.urlPath = urlPath;
	}
	public Form(String formName, String urlPath, byte[] content) {
		this.formName = formName;
		this.urlPath = urlPath;
		this.content = content;
	}
}