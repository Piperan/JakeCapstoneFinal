package ca.sheridancollege.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.sheridancollege.bean.*;

@Repository
public class FileUploadDAOImpl implements FileUploadDAO {
    
	SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
     
    public FileUploadDAOImpl() {
    }
 
    public FileUploadDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Override
    @Transactional
    public void save(Form uploadFile) {
        sessionFactory.getCurrentSession().save(uploadFile);
    } 
}