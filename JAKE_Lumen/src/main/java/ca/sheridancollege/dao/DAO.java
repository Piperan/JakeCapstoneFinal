package ca.sheridancollege.dao;

import java.util.*;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.validation.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ca.sheridancollege.bean.*;

public class DAO {
	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	// Method To Generate a User and a Admin.
	public void generateDummyUser() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// Possible Options
		String[] namesF = { "John", "Mary", "JC", "Lou", "Andre", "Mitch", "Mark", "Jen", "Emma", "Ross" };
		String[] namesL = { "Smith", "Davidson", "Doe", "Yan", "Brown", "Miller", "Wilson", "Jones", "Taylor",
				"Lopez" };
		String[] streetName = { "Lombard Street", "Abbey Road", "Fifth Avenue", "Santa Monica Street", "Beale Street",
				"Bourbon Street", "Harley Street", "Grafton Street", "Carnaby Street", "Baker Street" };
		String password = "potato";
		// Password Hasher
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		// Get Random names from list
		String fname1 = namesF[randomNum(9, 0)];
		String fname2 = namesF[randomNum(9, 0)];
		// Make new users
		User user1 = new User("Andrew", hashedPassword,"myEmail@email.com", true, "Andrew", namesL[randomNum(9, 0)],
				randomNum(1, 100) + " " + streetName[randomNum(9, 0)]);
		User user2 = new User("Evangeline", hashedPassword,"myEmail@email.com", true, "Evangeline", namesL[randomNum(9, 0)],
				randomNum(1, 100) + " " + streetName[randomNum(9, 0)]);
		User user3 = new User("Kyle", hashedPassword,"myEmail@email.com", true, "Kyle", namesL[randomNum(9, 0)],
				randomNum(1, 100) + " " + streetName[randomNum(9, 0)]);
		// Making and Adding Role to new user
		UserRole ur1 = new UserRole(user1, "ROLE_ADMIN");
		UserRole ur2 = new UserRole(user2, "ROLE_USER");
		UserRole ur3 = new UserRole(user3, "ROLE_CDM");
		user1.getUserRoles().add(ur1);
		user2.getUserRoles().add(ur2);
		user3.getUserRoles().add(ur3);
		// Saving User and Roles
		session.save(user1);
		session.save(user2);
		session.save(user3);
		session.save(ur3);
		session.save(ur2);
		session.save(ur1);

		session.getTransaction().commit();
		session.close();
	}
	
	// Method to add a CDM user
		public void addCDMUser(User user) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			// Hashing the users Password
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(hashedPassword);
			// Setting all new users default to USER role
			UserRole ur = new UserRole(user, "ROLE_CDM");
			user.getUserRoles().add(ur);
			// saving the user and role
			session.save(user);
			session.save(ur);

			session.getTransaction().commit();
			session.close();
		}

	// Method to add a regular user
	public void addUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// Hashing the users Password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		// Setting all new users default to USER role
		UserRole ur = new UserRole(user, "ROLE_USER");
		user.getUserRoles().add(ur);
		// saving the user and role
		session.save(user);
		session.save(ur);

		session.getTransaction().commit();
		session.close();
	}
	// Method to add a regular user
		public void addUserWithRole(User user, String role) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			// Hashing the users Password
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(hashedPassword);
			// Setting all new users default to USER role
			UserRole ur = new UserRole(user, role);
			user.getUserRoles().add(ur);
			// saving the user and role
			session.save(user);
			session.save(ur);

			session.getTransaction().commit();
			session.close();
		}

	// method to generate random numbers in a range
	public static int randomNum(int max, int min) {
		int random;
		random = (int) (Math.random() * ((max - min) + 1)) + min;
		return random;
	}

	//Method to Retrieve all the projects
	public List<Project> getAllProjects() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Make a List of projects that include all of the projects
		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) session.createQuery("from Project").getResultList();

		session.getTransaction().commit();
		session.close();

		return projectList;
	}
	//Method to Retrieve all the projects by their user
	public List<Project> getAllProjectsByUser(User loggedUser) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Make a List of projects that will hold the users project objects;
		List<Project> projectList = new ArrayList<Project>();
		
		//Get the project Id's Of all the users projects
		List<Integer> usersProjects;
		Query query = session.createQuery("Select p.projectId from Project p JOIN p.users where users_userId=:userId");
		query.setParameter("userId", loggedUser.getUserid());
		usersProjects = (List<Integer>) query.getResultList();
		
		//Getting the users projects based of of the project ID and adding to projectList
		usersProjects.forEach((Integer temp) ->{
			projectList.add(getProjectById(temp));
		});
		 
		session.getTransaction().commit();
		session.close();

		return projectList;
	}
	public User getUserByProjectAndUsername(Project project, String username) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("Select f.userid from User f JOIN f.projects where projectId=:projId AND username=:username");
		query.setParameter("projId", project.getProjectId());
		query.setParameter("username", username);
		List<Integer> userProjects;
		userProjects = (List<Integer>) query.getResultList();
		User userx= getUserById(userProjects.get(0));
		return userx;
	}
	public User getProjectManager(Project project) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("Select MIN(f.userid) from User f JOIN f.projects where projectId=:projId");
		query.setParameter("projId", project.getProjectId());
//		query.setParameter("3rdParty", "3rdParty"+project.getProjectId());
//		query.setParameter("Local", "Local"+project.getProjectId());
//		query.setParameter("National", "National"+project.getProjectId());
//		query.setParameter("CDM", "CDM"+project.getProjectId());
		List<Integer> userProjects;
		userProjects = (List<Integer>) query.getResultList();
		User userx= getUserById(userProjects.get(0));
		return userx;
	}
	//Method to Retrieve all forms based on the project 
		public List<Form> getAllFormsByProject(Project proj) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			// Make a List of projects that will hold the users project objects;
			List<Form> formList = new ArrayList<Form>();
			
			//Get the project Id's Of all the users projects
			List<Integer> formProjects;
			Query query = session.createQuery("Select f.formid from Form f JOIN f.projects where projectId=:projId");
			query.setParameter("projId", proj.getProjectId());
			formProjects = (List<Integer>) query.getResultList();
			
			//Getting the users projects based of of the project ID and adding to projectList
			formProjects.forEach((Integer temp) ->{
				formList.add(getFormById(temp));
			});
			 
			session.getTransaction().commit();
			session.close();

			return formList;
		}
	
	//Method to Retrieve all the Users
	public List<User> getAllUsers() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Make a List of Users that include all of the users
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) session.createQuery("from User").getResultList();

		session.getTransaction().commit();
		session.close();

		return userList;
	}

	//Method to Add project on succsess returns true on failure returns false
	@SuppressWarnings("unchecked")
	public boolean addProject(Project project, User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		//defaults to false
		boolean projectAdded = false;
		
		// Adding forms to the project
		List<Project> projectHolder= new ArrayList<Project>();
		projectHolder.add(project);
		List<Form> forms=project.getForms();
		for (Form var : forms) 
		{ 
		    var.setProjects(projectHolder);
		    session.save(var);
		}
		
		List<Project> projects= getAllProjectsByUser(user);
		projects.add(projectHolder.get(0));
		
		//Adding the project to the user
		User m = (User) session.get(User.class, user.getUserid());
		m.setFirstName(user.getFirstName());
		m.setLastName(user.getLastName());
		m.setUsername(user.getUsername());
		m.setPassword(user.getPassword());
		m.setEmail(user.getEmail());
		m.setAddress(user.getAddress());
		m.setEnabled(true);
		m.setProjects(projects);
		List<User> users = new ArrayList<User>();
		users.add(m);
		project.setUsers(users);
		
		//Saves the project
		session.save(project);

		//checking to see if the project was added
		List<Project> locList;
		Query query = session.createQuery("from Project where projectId=:projectId");
		query.setParameter("projectId", project.getProjectId());
		locList = query.getResultList();

		session.getTransaction().commit();
		session.close();

		//if the project was added make return false if true make projectAdded True and return
		if (locList == null || locList.size() == 0) {
			return projectAdded;
		} else {
			projectAdded = true;
		}
		return projectAdded;
	}
	
	//Updating the project Status
	public void updatedStatus(int id, Project project) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
			
		Project m = (Project) session.get(Project.class, id);
		m.setEndDate(project.getEndDate());
		m.setForms(project.getForms());
		m.setProjectDesc(project.getProjectDesc());
		m.setProjectName(project.getProjectName());
		m.setProjectStatus(project.getProjectStatus());
		m.setStartDate(project.getStartDate());
		m.setType(project.getType());
		m.setUsers(project.getUsers());
				
		session.getTransaction().commit();
		session.close();
	}
	
	//Method to get project status(int) by project id
	public int getProjectStatus(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Project currentProject = getProjectById(id);
		int x = currentProject.getProjectStatus();

		session.getTransaction().commit();
		session.close();

		return x;
	}
	
	// Method to get project by id returns null if it does not exist
	@SuppressWarnings("unchecked")
	public Project getProjectById(int projectId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		//Retrieving projects by id
		List<Project> projectList;
		Query query = session.createQuery("from Project where projectId=:projectId");
		query.setParameter("projectId", projectId);
		projectList = query.getResultList();

		
		session.getTransaction().commit();
		session.close();

		//checking if anything was returned if not returns null if there was returns the project
		if (projectList == null || projectList.size() == 0) {
			return null;
		} else {
			return projectList.get(0);
		}

	}
	
	// Method to get project by id returns null if it does not exist
		@SuppressWarnings("unchecked")
		public Form getFormById(int formid) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			//Retrieving projects by id
			List<Form> formList;
			Query query = session.createQuery("from Form where formid=:id");
			query.setParameter("id", formid);
			formList = query.getResultList();

			
			session.getTransaction().commit();
			session.close();

			//checking if anything was returned if not returns null if there was returns the project
			if (formList == null || formList.size() == 0) {
				return null;
			} else {
				return formList.get(0);
			}

		}
	
	//Method to Find a User by their username if username doesn't exit return false
	public User findUserByName(String uName) {
		@SuppressWarnings("unchecked")
		List<User> users = sessionFactory.openSession().createQuery("from User where username=:username").setParameter("username", uName).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	//Method to Get a list of all the enabled users
	public List<User> getEnabledUsers() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.where(cb.equal(root.get("enabled"), true));
		List<User> userList = session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();

		return userList;
	}
	
	//Method to get a list of all users that are not enabled (Mainly for newly registered users)
	public List<User> getDisabledUsers() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.where(cb.equal(root.get("enabled"), false));
		List<User> userList = session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();

		return userList;
	}
	
	//Method to get a specific user by their id
	public User getUserById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.where(cb.equal(root.get("userid"), id));
		List<User> voterList = session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();

		return voterList.get(0);
	}
	
	//Method to get a specifc user by their username
		public User getUserByUsername(String username) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> root = criteria.from(User.class);
			criteria.where(cb.equal(root.get("username"), username));
			List<User> userList = session.createQuery(criteria).getResultList();

			session.getTransaction().commit();
			session.close();

			return userList.get(0);
		}
		//Method to get a role user by its id
				public UserRole getRoleById(int id) {
					Session session = sessionFactory.openSession();
					session.beginTransaction();

					CriteriaBuilder cb = session.getCriteriaBuilder();
					CriteriaQuery<UserRole> criteria = cb.createQuery(UserRole.class);
					Root<UserRole> root = criteria.from(UserRole.class);
					criteria.where(cb.equal(root.get("role_id"), id));
					List<UserRole> roleList = session.createQuery(criteria).getResultList();

					session.getTransaction().commit();
					session.close();

					return roleList.get(0);
				}
		//Method to delete a user by their id
		public void deleteUserById(int id) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			List<Integer> roleList;
			Query query = session.createQuery("Select p.role_id from UserRole p JOIN p.user u where u.userid=:userId");
			query.setParameter("userId", id);
			roleList = (List<Integer>) query.getResultList();
			
			session.delete(getRoleById(roleList.get(0)));
			
			User user = getUserById(id);
			session.delete(user);

			session.getTransaction().commit();
			session.close();
		}
		
	
	//Method to edit a user by their id and a new user object
	public void editUser(int id, User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		User m = (User) session.get(User.class, id);
		m.setFirstName(user.getFirstName());
		m.setLastName(user.getLastName());
		m.setUsername(user.getUsername());
		m.setPassword(user.getPassword());
		m.setEmail(user.getEmail());
		m.setAddress(user.getAddress());

		session.getTransaction().commit();
		session.close();

	}
	
	//Method to enable a user by their id and a new user object
	public void enableUser(int id, User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		User m = (User) session.get(User.class, id);
		m.setFirstName(user.getFirstName());
		m.setLastName(user.getLastName());
		m.setUsername(user.getUsername());
		m.setPassword(user.getPassword());
		m.setEmail(user.getEmail());
		m.setAddress(user.getAddress());
		m.setEnabled(true);

		session.getTransaction().commit();
		session.close();

	}
	
	public void nextProjectPhase(int id, Project project) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Project m = (Project) session.get(Project.class, id);
		m.setProjectName(project.getProjectName());
		m.setProjectDesc(project.getProjectDesc());
		m.setStartDate(project.getStartDate());
		m.setEndDate(project.getEndDate());
		m.setType(project.getType());
		m.setProjectStatus(project.getProjectStatus()+1);
		m.setUsers(project.getUsers());
		m.setForms(project.getForms());
		
		session.getTransaction().commit();
		session.close();

	}
	
	//Method to enable a user by their id and a new user object
		public void editUploadForm(int id, Form newForm) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Form oldForm = getFormById(id);
					
			Form m = (Form) session.get(Form.class, id);
			m.setFormName(oldForm.getFormName());
			m.setUrlPath(oldForm.getUrlPath());
			m.setContent(newForm.getContent());
			m.setProjects(oldForm.getProjects());
			
			session.getTransaction().commit();
			session.close();

		}

	//Method to assign a project to a user
	public void assignProject(Project project, List<User> users) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int id = project.getProjectId();
		Project m = (Project) session.get(Project.class, id);
		m.setUsers(users);

		session.getTransaction().commit();
		session.close();
	}
	
	//Method to assign a user to a project
	public void projectUser(User user, List<Project> projects) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int id = user.getUserid();
		User m = (User) session.get(User.class, id);
		m.setProjects(projects);

		session.getTransaction().commit();
		session.close();
	}
	
	//Method to validate a Project and return a error message depending on what is wrong with the project
	public List<String> validateProject(Project s) {
		// set up hibernate validation
		// check if student is valid according to our annotations

		List<String> errorList = new ArrayList<String>();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

		Validator validator = validatorFactory.getValidator();

		// will generate errors and we'll put it in a set
		// looking strictly on our student
		Set<ConstraintViolation<Project>> violationErrors = validator.validate(s);

		if (!violationErrors.isEmpty()) {
			for (ConstraintViolation<Project> errors : violationErrors) {
				errorList.add(errors.getPropertyPath() + " :: " + errors.getMessage());
			}
		}
		return errorList;
	}
	
	public List<Integer> getReportResults(User loggedUser) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Make Lists for the diffrent types of projects;
		List<Project> projectListAR = new ArrayList<Project>();
		List<Project> projectListCCS = new ArrayList<Project>();
		List<Project> projectListSSAR = new ArrayList<Project>();
		List<Project> projectListSS = new ArrayList<Project>();
		
		//Get the project Id's Of all the users projects
		List<Integer> usersProjects;
		Query query = session.createQuery("Select p.projectId from Project p JOIN p.users where users_userId=:userId");
		query.setParameter("userId", loggedUser.getUserid());
		usersProjects = (List<Integer>) query.getResultList();
		
		//Sorting the projects into their lists
		usersProjects.forEach((Integer temp) ->{
			if (getProjectById(temp).getType().contentEquals("Afforrestation & Reforrestation")) {
				projectListAR.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else if (getProjectById(temp).getType().contentEquals("Carbon Capture and Storage")) {
				projectListCCS.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else if (getProjectById(temp).getType().contentEquals("Small Scale with AR & RF")) {
				projectListSSAR.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else if (getProjectById(temp).getType().contentEquals("Small Scale")) {
				projectListSS.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else {
				System.out.println("Project Type Not Found");
			}
		});
		
		//Getting the amount of each project and returning 
		List<Integer> ResultsList = new ArrayList<Integer>();
		ResultsList.add(projectListAR.size());
		ResultsList.add(projectListCCS.size());
		ResultsList.add(projectListSSAR.size());
		ResultsList.add(projectListSS.size());

		session.getTransaction().commit();
		session.close();

		return ResultsList;
	}
	
	public List<ArrayList<Project>> getReportLists(User loggedUser) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Make Lists for the diffrent types of projects;
		ArrayList<Project> projectListAR = new ArrayList<Project>();
		ArrayList<Project> projectListCCS = new ArrayList<Project>();
		ArrayList<Project> projectListSSAR = new ArrayList<Project>();
		ArrayList<Project> projectListSS = new ArrayList<Project>();
		
		//Get the project Id's Of all the users projects
		List<Integer> usersProjects;
		Query query = session.createQuery("Select p.projectId from Project p JOIN p.users where users_userId=:userId");
		query.setParameter("userId", loggedUser.getUserid());
		usersProjects = (List<Integer>) query.getResultList();
		
		//Sorting the projects into their lists
		usersProjects.forEach((Integer temp) ->{
			if (getProjectById(temp).getType().contentEquals("Afforrestation & Reforrestation")) {
				projectListAR.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else if (getProjectById(temp).getType().contentEquals("Carbon Capture and Storage")) {
				projectListCCS.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else if (getProjectById(temp).getType().contentEquals("Small Scale with AR & RF")) {
				projectListSSAR.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else if (getProjectById(temp).getType().contentEquals("Small Scale")) {
				projectListSS.add(getProjectById(temp));
				System.out.println((getProjectById(temp).getType()));
			}else {
				System.out.println("Project Type Not Found");
			}
		});
		
		//Getting the amount of each project and returning 
		List<ArrayList<Project>> ReportList = new ArrayList<ArrayList<Project>>();
		ReportList.add(0, projectListAR);
		ReportList.add(0, projectListCCS);
		ReportList.add(0, projectListSSAR);
		ReportList.add(0, projectListSS);

		session.getTransaction().commit();
		session.close();

		return ReportList;
	}
	//Method to disable a user by their id
		public void disableUser (int id, User user) {
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			User m = (User) session.get(User.class, id);
			m.setEnabled(false);
					
			session.getTransaction().commit();
			session.close();
		}
		
		//My Profile
		public void editMyProfile(int id, User user) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String password = user.getPassword();
			
			// Password Hasher
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			
			User m = (User) session.get(User.class, id);
			m.setFirstName(user.getFirstName());
			m.setLastName(user.getLastName());
			m.setUsername(user.getUsername());
			m.setPassword(user.getPassword());
			m.setEmail(user.getEmail());
			m.setAddress(user.getAddress());
			m.setPassword(hashedPassword);
			session.getTransaction().commit();
			session.close();
		}
		public String getAlphaNumericString(int n) 
	    { 
	  
	        // chose a Character random from this String 
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	                                    + "0123456789"
	                                    + "abcdefghijklmnopqrstuvxyz"; 
	  
	        // create StringBuffer size of AlphaNumericString 
	        StringBuilder sb = new StringBuilder(n); 
	  
	        for (int i = 0; i < n; i++) { 
	  
	            // generate a random number between 
	            // 0 to AlphaNumericString variable length 
	            int index 
	                = (int)(AlphaNumericString.length() 
	                        * Math.random()); 
	  
	            // add Character one by one in end of sb 
	            sb.append(AlphaNumericString 
	                          .charAt(index)); 
	        } 
	  
	        return sb.toString(); 
	    } 
}