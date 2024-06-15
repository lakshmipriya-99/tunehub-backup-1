package com.kodnest.tunehub.service;

//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;

public interface UserService {
	
	public String addUser(User user);
	
	public boolean emailExists(String email);
	
	public boolean validateUser(String email, String password);
	
	public String getRole(String email);
	//day9
	public User getUser(String email);
	public void updateUser(User user);
//	day9
	

}
