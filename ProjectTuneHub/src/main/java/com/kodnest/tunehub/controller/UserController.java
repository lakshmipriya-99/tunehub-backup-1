package com.kodnest.tunehub.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.service.SongService;
import com.kodnest.tunehub.service.UserService;
import com.kodnest.tunehub.serviceimpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserServiceImpl serviceImpl;
	//day 10//
	@Autowired
	SongService songService;
	//day 10//
	@PostMapping("/register")
	public String addUser(@ModelAttribute User user) 
	{	
		//		System.out.println(user.getUsername()+" "+user.getEmail()+" "+
		//				user.getPassword()+" "+user.getGender()+" "+user.getRole()+
		//				" "+user.getAddress());
		//		serviceImpl.addUser(user);

		//email taken from registration form
		String email = user.getEmail();

		//checking if email has entered in registration form 
		//is present in DB or not.
		boolean status = serviceImpl.emailExists(email);

		if(status == false) {
			serviceImpl.addUser(user);
			System.out.println("User added");	
		}
		else {
			System.out.println("User already exists");
		}
		return "login";
		//		//Redirecting to home page with respect to the role
		//		String role = user.getRole();
		//		if(role=="admin") {
		//			return "adminhome";
		//		} else {
		//			return "customerhome";
		//		}
	}


	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session, Model model) {
		if(serviceImpl.validateUser(email, password) == true ) {

			String role = serviceImpl.getRole(email);
			//day9
			session.setAttribute("email", email);
			//day9

			if(role.equals("admin")) {
				return "adminhome";
			} else {
				//day 10 mentioned model attribute as one of the parameter
//				serviceImpl.getUser(email); after typin this to get below line use  ctrl 2 L
				User user = serviceImpl.getUser(email);//fetching user from repo from db
				boolean userstatus = user.isIspremium();//fetching whether the user is premium or not
				//logic to fetch and display songs on premium customer page
				List<Song> fetchAllSongs = songService.fetchAllSongs();
				model.addAttribute("songs", fetchAllSongs);
				model.addAttribute("ispremium",userstatus); //the ispremium value i from the customer home page{ispremium}
				//day 10
				return "customerhome";
			}
		}
		else {

			return "login";
		}

	}
	//day 9
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();// session object is destroyed
		return "login";
	}
}
