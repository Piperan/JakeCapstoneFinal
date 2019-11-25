package ca.sheridancollege.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {
	@Id
	@GeneratedValue
	private int projectId;
	@NotNull(message="Name cannot be null")
	@Size(min=5,max=25,message="Name must be of size between 5 and 20")
	private String projectName;
	@NotNull(message="Description cannot be null")
	@Size(min=5,max=150,message="Description must be of size between 5 and 150")
	private String projectDesc;
	@Pattern(regexp = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$",message = "Invalid start date. Please user dd/mm/yyyy format.")
	private String startDate;
	@Pattern(regexp = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$",message = "Invalid end date. Please user dd/mm/yyyy format.")
	private String endDate;
	@Transient
	String[] typeList= {"Afforrestation & Reforrestation","Carbon Capture and Storage","Small Scale","Small Scale with AR & RF"};
	@NotNull(message="Type cannot be empty.")
	private String type;
	@NotNull(message="Type cannot be empty.")
	private int projectStatus;
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="projects")
	private List<User> users= new ArrayList<User>();
	
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="projects")
	private List<Form> forms= new ArrayList<Form>();

	public Project(String projectName, String projectDesc, String startDate, String endDate, String type) {
		this.projectName = projectName;
		this.projectDesc = projectDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	public Project(String projectName, String projectDesc, String startDate, String endDate, String type, List<User> users) {
		this.projectName = projectName;
		this.projectDesc = projectDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.users = users;
	}

	public Project(int projectId, String projectName, String projectDesc, String startDate, String endDate, String type, List<Form> forms) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDesc = projectDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.forms = forms;
	}
}