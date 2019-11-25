package ca.sheridancollege;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ca.sheridancollege.bean.*;
import ca.sheridancollege.dao.*;


@Controller
public class HomeController {
	
	@Autowired
    private JavaMailSender mailSender;   	
	
	//DAO Call
	DAO dao=new DAO();
	String username;
	
	private FileUploadDAO fileUploadDao;
	
	//Initial Mapping
	@GetMapping("/") 
	public String goindex(Model model)
	{
		
		return "th_landing";
	}
	
	//Generate Users
	@GetMapping("/gen") 
	public String goindex2(Model model)
	{
		//DAO Method to generate user
		dao.generateDummyUser();
		return "th_landing";
	}
	
	//Login Error Page
	@GetMapping("/error") 
	public String goError()
	{
		return "error/th_error";
	}
	
	String getUsername(){
		String getMe;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			getMe= ((UserDetails)principal).getUsername();
		} else {
			getMe = principal.toString();
		}
		return getMe;
	}
	
	@RequestMapping("/user") 
	public String goSecuredHome(Model model)
	{
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.print("The Current user is " + username);
		model.addAttribute("username",username);
		List<Project> projects=dao.getAllProjectsByUser(dao.getUserByUsername(username));
		model.addAttribute("projects",projects);

		return "user/homeUser";
	}
	
	@RequestMapping("/3rdparty") 
	public String go3RDPARTY(Model model)
	{
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.print("The Current user is " + username);
		model.addAttribute("username",username);
		List<Project> projects=dao.getAllProjectsByUser(dao.getUserByUsername(username));
		Project proj = projects.get(0);
		model.addAttribute("project",proj);
		model.addAttribute("projectID",proj.getProjectId());
		model.addAttribute("projForms",dao.getAllFormsByProject(proj));		
		model.addAttribute("username",username);
		model.addAttribute("userProj", dao.getProjectManager(proj));
		
		return "3rdparty/home";
	}
	
	@RequestMapping("/cdm") 
	public String goCDM(Model model)
	{
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.print("The Current user is " + username);
		model.addAttribute("username",username);
		List<Project> projects=dao.getAllProjectsByUser(dao.getUserByUsername(username));
		Project proj = projects.get(0);
		model.addAttribute("project",proj);
		model.addAttribute("projectID",proj.getProjectId());
		model.addAttribute("projForms",dao.getAllFormsByProject(proj));		
		model.addAttribute("username",username);
		model.addAttribute("userProj", dao.getProjectManager(proj));
		
		return "cdm/home";
	}
	
	@RequestMapping("/local") 
	public String goLOCAL(Model model)
	{
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.print("The Current user is " + username);
		model.addAttribute("username",username);
		List<Project> projects=dao.getAllProjectsByUser(dao.getUserByUsername(username));
		Project proj = projects.get(0);
		model.addAttribute("project",proj);
		model.addAttribute("projectID",proj.getProjectId());
		model.addAttribute("projForms",dao.getAllFormsByProject(proj));		
		model.addAttribute("username",username);
		model.addAttribute("userProj", dao.getProjectManager(proj));
		
		return "local/home";
	}
	
	@RequestMapping("/national") 
	public String goNATIONAL(Model model)
	{
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.print("The Current user is " + username);
		model.addAttribute("username",username);
		List<Project> projects=dao.getAllProjectsByUser(dao.getUserByUsername(username));
		Project proj = projects.get(0);
		model.addAttribute("project",proj);
		model.addAttribute("projectID",proj.getProjectId());
		model.addAttribute("projForms",dao.getAllFormsByProject(proj));		
		model.addAttribute("username",username);
		model.addAttribute("userProj", dao.getProjectManager(proj));
		
		return "national/home";
	}
	
	@RequestMapping(value = "/user/project/search",  method=RequestMethod.GET)
	public String searchProject(@RequestParam("keyword") String keyword, Model model) {
		
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("username",getUsername());
		  
	       List<ArrayList<Project>> ReportList = dao.getReportLists(dao.getUserByUsername(username));
	       ArrayList<Project> SearchResults = new ArrayList<Project>();
	       
			ReportList.forEach((List<Project> temp) -> {
				temp.forEach((Project temp2) ->{
		
					if (temp2.getProjectName().toLowerCase().contains(keyword.toLowerCase())) {
						
						SearchResults.add(temp2);
					}
				
					if (temp2.getType().toLowerCase().contains(keyword.toLowerCase())){
						SearchResults.add(temp2);
					}
			
				});
			});
			
			model.addAttribute("projects", SearchResults);
			
		return "user/homeUser";
	}
	
	@RequestMapping("/user/email") 
	public String sendEmailLA(@RequestParam("name") String name, @RequestParam("id") Integer id, Model model) { 
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		Project x=dao.getProjectById(id);
		
		List<String> projectStatusesEmail = new ArrayList<String>();
		projectStatusesEmail.add("Placeholder");
		projectStatusesEmail.add("LocalAuthority");
		projectStatusesEmail.add("Placeholder");
		projectStatusesEmail.add("NationalAuthority");
		projectStatusesEmail.add("Placeholder");
		projectStatusesEmail.add("3rdParty");
		projectStatusesEmail.add("Placeholder");
		projectStatusesEmail.add("CDM");
		projectStatusesEmail.add("Placeholder");
		
		String password = dao.getAlphaNumericString(6);
		List<Project> projects = new ArrayList<Project>();
		projects.add(x);
		
		if (x.getProjectStatus() == 1) {
			User user = new User(projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), password, "NA", projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), "NA");
			user.setProjects(projects);
			dao.addUserWithRole(user, "ROLE_LOCAL");
			dao.enableUser(user.getUserid(), user);
		}else if(x.getProjectStatus() == 3) {
			User user = new User(projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), password, "NA", projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), "NA");
			user.setProjects(projects);
			dao.addUserWithRole(user, "ROLE_NATIONAL");
			dao.enableUser(user.getUserid(), user);
		}else if(x.getProjectStatus() == 5) {
			User user = new User(projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), password, "NA", projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), "NA");
			user.setProjects(projects);
			dao.addUserWithRole(user, "ROLE_3RDPARTY");
			dao.enableUser(user.getUserid(), user);
		}else if(x.getProjectStatus() == 7) {
			User user = new User(projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), password, "NA", projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId(), "NA");
			user.setProjects(projects);
			dao.addUserWithRole(user, "ROLE_CDM");	
			dao.enableUser(user.getUserid(), user);
		}

		String message= "Dear " + projectStatusesEmail.get(x.getProjectStatus()) + ",\n\nPlease approve project"+x.getProjectName()+".\n"
				+ "Login to Project Lumen website with the following account: \n"
				+ "USERNAME: "+ projectStatusesEmail.get(x.getProjectStatus())+x.getProjectId() + " PASSWORD: " + password + "\n"+
				"Link: https://projectlumen.azurewebsites.net";
		
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(name);
        email.setSubject("Approve Project "+x.getProjectName());
        email.setText(message);
        mailSender.send(email);
        
        x.setProjectStatus(x.getProjectStatus()+1);
		dao.updatedStatus(id, x);
		
		
		List<String> projectStatuses = new ArrayList<String>();
		projectStatuses.add("Placeholder");
		projectStatuses.add("Send Email for Local Authority Approval.");
		projectStatuses.add("Project has been submitted for Local Authority Approval.");
		projectStatuses.add("Send Email for National Authority Approval.");
		projectStatuses.add("Project has been submitted for National Authority Approval.");
		projectStatuses.add("Send Email for 3rd Party Approval.");
		projectStatuses.add("Project has been submitted for 3rd Party Approval.");
		projectStatuses.add("Send Email for CDM Approval.");
		projectStatuses.add("Project has been submitted for CDM Approval.");
		projectStatuses.add("CDM Approved Awaiting Carbon Credits.");
		
		Project proj = dao.getProjectById(id);
		List<Form> projectForms = dao.getAllFormsByProject(proj);
		var numberOfUploaded = 0;
		boolean completed=false;
		model.addAttribute("project",proj);
		model.addAttribute("projectID",id);
		model.addAttribute("projForms",dao.getAllFormsByProject(proj));
		model.addAttribute("username",getUsername());
		model.addAttribute("projectStatus",projectStatuses.get(proj.getProjectStatus()));
		model.addAttribute("projectStatusNumber", proj.getProjectStatus());
		model.addAttribute("projectStatusPosition", proj.getProjectStatus()%2);
		model.addAttribute("progressBarPosition", proj.getProjectStatus()*10);
		
		for (Form var : projectForms) 
		{ 
		    if(var.getContent()!=null){
		    	numberOfUploaded+=1;
		    }
		}
		
		if(numberOfUploaded==projectForms.size()) {
			completed = true;
		}
		
		model.addAttribute("done",completed);
		model.addAttribute("myUrl","http://localhost:8080/cdm/regProject/"+id);
		
		
        model.addAttribute("project",x);
        model.addAttribute("username",username);
		return "/user/viewProject";
	}
	
	@RequestMapping("/user/createProject") 
	public String goCreateProject(Model model)
	{
		
		model.addAttribute("username",getUsername());
		model.addAttribute("projects",dao.getAllProjects());
		return "user/homeUser";
	}
	
	@GetMapping("/admin") 
	public String goAdminHome()
	{
		
		return "admin/th_home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "th_login";
    }
	
    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();

        if(role.contains("ROLE_ADMIN")){
         response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin"));                            
         }
         else if(role.contains("ROLE_USER")) {
             response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user"));
         }
         else if(role.contains("ROLE_CDM")) {
             response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/cdm"));
         }
         else if(role.contains("ROLE_3RDPARTY")) {
             response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/3rdparty"));
         }
         else if(role.contains("ROLE_LOCAL")) {
             response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/local"));
         }
         else if(role.contains("ROLE_NATIONAL")) {
             response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/national"));
         }
    }
    
	@RequestMapping("/Edit/{username}") 
	public String editMovie(Model model, @PathVariable String username) {
		return "user/th_home"; 
	}
	
	@RequestMapping("/register") 
	public String goregister(Model model, @ModelAttribute User user)
	{
		User p2=new User();
	    model.addAttribute("user",p2);
	    model.addAttribute("username",getUsername());
		return "register";
	}
	
	@RequestMapping("/registerSend") 
	public String goregisterSend(Model model, @ModelAttribute User user)
	{
		synchronized(User.class) { //prevents thread interfererance
			user.setEnabled(false);
			dao.addUser(user);
		}
		User p2=new User();
		model.addAttribute("username",getUsername());
	    model.addAttribute("user",p2);
		return "register";
	}
	
	@GetMapping("/admin/registrationRequests") 
	public String goRegistrationRequestPage(Model model)
	{
		model.addAttribute("username",getUsername());
		model.addAttribute("users",dao.getDisabledUsers());
		return "admin/registrationRequests";
	}
	
	@GetMapping("/admin/viewUsers") 
	public String goViewUsersPage(Model model)
	{
		model.addAttribute("username",getUsername());
		model.addAttribute("users",dao.getEnabledUsers());
		return "admin/viewUsers";
	}
	
	@RequestMapping("/admin/deleteEnabledUser/{userid}") 
	public String goDeleteEnabledVoter(Model model, @PathVariable int userid) { 
		User user = dao.getUserById(userid);
		if(user != null) {
			System.out.println(userid);
			dao.deleteUserById(userid);
		}else {
			user = new User();
		}
		model.addAttribute("users",dao.getEnabledUsers());
		model.addAttribute("username",getUsername());
		return "admin/viewUsers";
	}
	
	@RequestMapping("/admin/deleteDisabledUser/{userid}") 
	public String goDeleteDisabledVoter(Model model, @PathVariable int userid) { 
		User user = dao.getUserById(userid);
		if(user != null) {
			System.out.println(userid);
			dao.deleteUserById(userid);
		}else {
			user = new User();
		}
		model.addAttribute("users",dao.getDisabledUsers());
		model.addAttribute("username",getUsername());
		return "admin/registrationRequests";
	}
	
	@RequestMapping("/EditUser")
	public String editUser2(@ModelAttribute User user, Model model, @RequestParam int userid) {
		
		dao.editUser(userid, user);
		
		List<User> users = dao.getAllUsers();
		model.addAttribute("users",users);
		model.addAttribute("username",getUsername());
		return "admin/viewUsers";

	}
	
	@RequestMapping("/admin/editUser/{userid}") 
	public String goEditUser(Model model, @PathVariable int userid) { 

		User user = dao.getUserById(userid);
		model.addAttribute("username",getUsername());
		model.addAttribute("user",user);
		return "admin/EditMovie";
	}
	
	@RequestMapping("/admin/enableUser/{userid}") 
	public String goEnableUsers(Model model, @PathVariable int userid) { 

		User user = dao.getUserById(userid);
		dao.enableUser(userid, user);
		model.addAttribute("username",getUsername());
		model.addAttribute("users",dao.getDisabledUsers());
		String message="Hi "+user.getFirstName()+" "+user.getLastName()+",\n\n\n"+
				"Your registration for project lumen has been approved. \n"
				+ "Login to you account using the username: "+user.getUsername()+"\n\n\nThank you,\nProject Lumen";
		
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Welcome to Project Lumen!");
        email.setText(message);
        mailSender.send(email);
		return "admin/registrationRequests";
	}
	
	@RequestMapping("/user/projectForms") 
	public String formsProject(@ModelAttribute Project project, Model model)
	{	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		User x= dao.findUserByName("username");
		List<Project> prjectList =new ArrayList<Project>();
		prjectList.add(dao.getProjectById(1));
		prjectList.add(dao.getProjectById(2));
		prjectList.add(dao.getProjectById(3));
		dao.projectUser(x, prjectList);
		return "user/homeUser";
	}
	//Page that adds a project
	@RequestMapping("/user/addProject") 
	public String addProject(@ModelAttribute Project project, Model model) { 
		String username;
		String warning = "";
		List<Form> formsProj = new ArrayList<Form>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		User x= dao.findUserByName("username");
		
		List<Project> prjectList =new ArrayList<Project>();
		
		
		 Form form1 = new Form("F-CDM-A","https://cdm.unfccc.int/Reference/PDDs_Forms/Accreditation/accr_form01.pdf");
		 Form form2 = new Form("CDM-DOO-FORM","https://cdm.unfccc.int/sunsetcms/storage/contents/stored-file-20181001095253054/AccrForm31_DOO.pdf");
		 Form form3 = new Form("F-CDM-SCC","https://cdm.unfccc.int/sunsetcms/storage/contents/stored-file-20181001095213533/AccrForm22_SCC.pdf");
		 Form form4 = new Form("F-CDM-W","https://cdm.unfccc.int/Reference/PDDs_Forms/Accreditation/accr_form25.pdf");
		 Form form5 = new Form("F-CDM-FPM","https://cdm.unfccc.int/Reference/PDDs_Forms/Accreditation/accr_form13.pdf");
		 formsProj.add(form1);
		 formsProj.add(form2);
		 formsProj.add(form3);
		 formsProj.add(form4);
		 formsProj.add(form5);
		 if(project.getType()== "Afforrestation & Reforrestation")
		 {
			 Form form6 = new Form("F-CDM-AR-AM-Rev","https://cdm.unfccc.int/Reference/PDDs_Forms/Methodologies/methAR_form08.pdf");
			 Form form7 = new Form("F-CDM-AR-AM-Subm","https://cdm.unfccc.int/Reference/PDDs_Forms/Methodologies/methAR_form09.pdf");
			 formsProj.add(form6);
			 formsProj.add(form7);
		 }
		 project.setForms(formsProj);
		 project.setProjectStatus(1);
		synchronized(Project.class) {
			if(dao.validateProject(project).isEmpty()) {
				if(dao.addProject(project,dao.getUserByUsername(username))) {
						warning="Project Added";
						model.addAttribute("project",project);
						prjectList.add(project);
				}
			
				else {
					warning="Project not Added";
				}
			}
			else {
				model.addAttribute("project",project);
				model.addAttribute("errorList",dao.validateProject(project));
			}
			
		}
		
		model.addAttribute("formsList", project.getForms());
		model.addAttribute("username", username);
		model.addAttribute("error",warning);	
	    
		return "user/createProject";
	}
	
	@RequestMapping("/user/saveProject") 
	public String saveProject(Model model) { 
		model.addAttribute("username",getUsername());
		model.addAttribute("project",new Project());
		return "user/createProject";
	}
	
	@RequestMapping(value="/getpdf1/{formid}")
	public ResponseEntity<byte[]> getPDF1(@PathVariable int formid) {

		Form x=dao.getFormById(formid);
	    HttpHeaders headers = new HttpHeaders();

	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = x.getFormName()+"_COMPLETED.pdf";
	    headers.setContentDispositionFormData("inline", filename);
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(x.getContent(), headers, HttpStatus.OK);
	
	    return response;
	}
		
	@RequestMapping("/user/myReports") 
	public String myReport(Model model) { 
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("username",getUsername());
		
		List<Integer> ResultList = dao.getReportResults(dao.getUserByUsername(username));
		List<Project> overdue = new ArrayList<Project>();
		ArrayList<Project> projectListAR = new ArrayList<Project>();
		ArrayList<Project> projectListCCS = new ArrayList<Project>();
		ArrayList<Project> projectListSSAR = new ArrayList<Project>();
		ArrayList<Project> projectListSS = new ArrayList<Project>();
	
			Date date = new Date();
		   DateFormat df = new SimpleDateFormat("dd/MM/yy");
	       System.out.println(df.format(date));
	       
	       List<ArrayList<Project>> ReportList = dao.getReportLists(dao.getUserByUsername(username));
			
			ReportList.forEach((List<Project> temp) -> {
				temp.forEach((Project temp2) ->{
					Date test;
					try {
						test = new SimpleDateFormat("dd/MM/yyyy").parse(temp2.getEndDate());
					
					System.out.println("");
					System.out.println(temp2.getProjectName());
					//projectListAR.add(temp2);
					System.out.println("");
					
					if (temp2.getType().contentEquals("Afforrestation & Reforrestation")) {
						projectListAR.add(temp2);
					
					}else if (temp2.getType().contentEquals("Carbon Capture and Storage")) {
						projectListCCS.add(temp2);
					
					}else if (temp2.getType().contentEquals("Small Scale with AR & RF")) {
						projectListSSAR.add(temp2);
						
					}else if (temp2.getType().contentEquals("Small Scale")) {
						projectListSS.add(temp2);
				
					}else {
						System.out.println("Project Type Not Found");
					}
					
					
					if (test.before(date)) {
						System.out.println(date);
						System.out.println(temp2.getProjectName());
						overdue.add(temp2);
					}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				});
			});
			
		ResultList.forEach((Integer temp) -> {
			System.out.print(temp);
		});
		
		model.addAttribute("ResultList", ResultList);
		model.addAttribute("projectListSS", projectListSS);
		model.addAttribute("projectListAR", projectListAR);
		model.addAttribute("projectListSSAR", projectListSSAR);
		model.addAttribute("projectListCCS", projectListCCS);
		model.addAttribute("overDue", overdue);
		
		return "user/viewReports";
	}
		
	@RequestMapping("/user/myOverdue") 
	public String tryMe(Model model) { 
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		User x= dao.findUserByName(username);
		List<Project> projList=x.getProjects();
		List<Project> overList=new ArrayList<Project>();
		for (Project ccc :projList) 
		{ 
			String endDate = ccc.getEndDate();     //input string
			String lastFourDigits = "";     //substring containing last 4 characters
			 
			if (endDate.length() > 4)
			{
			    lastFourDigits = endDate.substring(endDate.length() - 4);
			    try
			    {
			      int i = Integer.parseInt(lastFourDigits.trim());
			      if(i<2019)
			      {
			      overList.add(ccc);
			      }
			    }
			    catch (NumberFormatException nfe)
			    {
			      System.out.println("NumberFormatException: " + nfe.getMessage());
			    }
			}
			else
			{
			    lastFourDigits = endDate;
			}			 
		}
		model.addAttribute("username",username+"   ---   Projects that are: OVERDUE");
		model.addAttribute("projects",overList);
		return "user/viewReports";
	}
	
	//view a specific project
	@RequestMapping("/user/viewProject/{projectId}") 
	public String viewProject(Model model, @PathVariable int projectId) { 
		
		List<String> projectStatuses = new ArrayList<String>();
		projectStatuses.add("Placeholder");
		projectStatuses.add("Send Email for Local Authority Approval.");
		projectStatuses.add("Project has been submitted for Local Authority Approval.");
		projectStatuses.add("Send Email for National Authority Approval.");
		projectStatuses.add("Project has been submitted for National Authority Approval.");
		projectStatuses.add("Send Email for 3rd Party Approval.");
		projectStatuses.add("Project has been submitted for 3rd Party Approval.");
		projectStatuses.add("Send Email for CDM Approval.");
		projectStatuses.add("Project has been submitted for CDM Approval.");
		projectStatuses.add("CDM Approved Awaiting Carbon Credits.");
		
		Project proj = dao.getProjectById(projectId);
		List<Form> projectForms = dao.getAllFormsByProject(proj);
		var numberOfUploaded = 0;
		boolean completed=false;
		model.addAttribute("project",proj);
		model.addAttribute("projectID",projectId);
		model.addAttribute("projForms",dao.getAllFormsByProject(proj));
		model.addAttribute("username",getUsername());
		model.addAttribute("projectStatus",projectStatuses.get(proj.getProjectStatus()));
		model.addAttribute("projectStatusNumber", proj.getProjectStatus());
		model.addAttribute("projectStatusPosition", proj.getProjectStatus()%2);
		model.addAttribute("progressBarPosition", proj.getProjectStatus()*10);
		
		for (Form var : projectForms) 
		{ 
		    if(var.getContent()!=null){
		    	numberOfUploaded+=1;
		    }
		}
		
		if(numberOfUploaded==projectForms.size()) {
			completed = true;
		}
		
		model.addAttribute("done",completed);
		model.addAttribute("myUrl","http://localhost:8080/cdm/regProject/"+projectId);
		return "user/viewProject";
	}
	
	@RequestMapping("/user/doUpload/{projectId}/{formid}")
    public String handleFileUpload(Model model, @RequestParam MultipartFile[] fileUpload, @PathVariable int projectId, @PathVariable int formid) throws Exception {
          Project x=dao.getProjectById(projectId);
          int numberOfUploaded = 0;
          boolean completed=false;
          		if (fileUpload != null && fileUpload.length > 0) {
            for (MultipartFile aFile : fileUpload){
            	
                Form newForm = new Form();
                newForm.setFormName(aFile.getOriginalFilename());
                newForm.setContent(aFile.getBytes());
                newForm.setUrlPath("Placeholder");
                
                dao.editUploadForm(formid,newForm);           
            }
            
            List<String> projectStatuses = new ArrayList<String>();
    		projectStatuses.add("Placeholder");
    		projectStatuses.add("Send Email for Local Authority Approval.");
    		projectStatuses.add("Project has been submitted for Local Authority Approval.");
    		projectStatuses.add("Send Email for National Authority Approval.");
    		projectStatuses.add("Project has been submitted for National Authority Approval.");
    		projectStatuses.add("Send Email for 3rd Party Approval.");
    		projectStatuses.add("Project has been submitted for 3rd Party Approval.");
    		projectStatuses.add("Send Email for CDM Approval.");
    		projectStatuses.add("Project has been submitted for CDM Approval.");
    		projectStatuses.add("CDM Approved Awaiting Carbon Credits.");
    		
    		Project proj = dao.getProjectById(projectId);
    		model.addAttribute("project",proj);
    		model.addAttribute("projectID",projectId);
    		model.addAttribute("projForms",dao.getAllFormsByProject(proj));
    		model.addAttribute("username",getUsername());
    		model.addAttribute("projectStatus",projectStatuses.get(proj.getProjectStatus()));
    		model.addAttribute("projectStatusNumber", proj.getProjectStatus());
    		model.addAttribute("projectStatusPosition", proj.getProjectStatus()%2);
    		model.addAttribute("progressBarPosition", proj.getProjectStatus()*10);
    		model.addAttribute("done",completed);
        }
          		
          	List<Form> xForms=dao.getAllFormsByProject(x);
          	for (Form var : xForms) { 
          		if(var.getContent()!=null){
        		    numberOfUploaded+=1;
        		}
          	}
          	
        	if(numberOfUploaded==xForms.size()) {
        		completed=true;
        	}
          	model.addAttribute("username",getUsername()); 
          	model.addAttribute("done",completed);
          	model.addAttribute("myUrl","http://localhost:8080/user/regProject/"+projectId);
         
        return "/user/viewProject";
    }
	
	@RequestMapping("/admin/disableUser/{userid}") 
	public String goDisableUsers(Model model, @PathVariable int userid) { 

		User user = dao.getUserById(userid);
		dao.disableUser(userid, user);
		model.addAttribute("username",getUsername());
		model.addAttribute("users",dao.getDisabledUsers());
		return "admin/registrationRequests";
	}
	
	@RequestMapping("/user/editUser") 
	public String goEditMyProfile(Model model) { 
		String username;
		User x = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			x=dao.getUserByUsername(username);
		} else {
			username = principal.toString();
		}
		User user = dao.getUserByUsername(username);
		model.addAttribute("username",getUsername());
		user.setPassword("New Password");
		model.addAttribute("user",user);
		return "user/EditProfile";
	}
	
	@RequestMapping("/EditUser/MyProfile")
	public String editMyProfile(@ModelAttribute User user, Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		dao.editMyProfile(dao.getUserByUsername(username).getUserid(), user);
		
		model.addAttribute("username",getUsername());
		
		model.addAttribute("user",user);
		
		return "user/EditProfile";
	}
	@RequestMapping("/certify/{projectID}")
	public String certifyProject(Model model, @PathVariable int projectID) {
		
		Project project = dao.getProjectById(projectID);
		System.out.println("=========================");
		System.out.println(project.getProjectStatus());
		System.out.println("=========================");
        project.setProjectStatus(project.getProjectStatus()+1);
		dao.updatedStatus(projectID, project);
		
		if(project.getProjectStatus()%2 == 0) {
            project.setProjectStatus(project.getProjectStatus()-1);
            dao.updatedStatus(projectID, project);
        }
		
		return "th_login";
	}
	
			
}