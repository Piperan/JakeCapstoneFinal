package ca.sheridancollege;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ca.sheridancollege.dao.FileUploadDAO;
import ca.sheridancollege.dao.FileUploadDAOImpl;

public class ApplicationContextConfig {

	@Autowired
	@Bean(name = "fileUploadDao")
	public FileUploadDAO getUserDao(SessionFactory sessionFactory) {
	    return new FileUploadDAOImpl(sessionFactory);
	}
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);   // 20MB
        multipartResolver.setMaxInMemorySize(1048576);  // 1MB
        return multipartResolver;
    }
}
