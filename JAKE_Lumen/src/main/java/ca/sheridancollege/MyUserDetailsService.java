package ca.sheridancollege;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.bean.UserRole;
import ca.sheridancollege.dao.DAO;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//classpath as to not conflict with user object of *.userdetails.User
		ca.sheridancollege.bean.User user = new DAO().findUserByName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
		
		
		return  buildUserForAunthentication(user, authorities);
	}
	
	private User buildUserForAunthentication(ca.sheridancollege.bean.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,true,authorities);
	}
	
	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles){
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		
		for(UserRole role: userRoles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		return new ArrayList<GrantedAuthority>(grantedAuthorities);
	}

}
