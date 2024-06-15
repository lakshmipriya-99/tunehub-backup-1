package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.service.SongService;

@Controller
public class SongController {
	//connecting SongService using annotation or else we can use constructor
	@Autowired
	SongService songService;
	//ModelAttribute  is used to collect the info of Song.java as an entity
	@PostMapping("/addsong")
	public String addSong(@ModelAttribute Song song) {

		boolean songStatus = songService.songExists(song.getName());
		if(songStatus==false) {
			songService.addSong(song);
			System.out.println("Song added successfully");
		}
		else {
			System.out.println("Song already exists");
		}
		return "adminhome";
	}
	//	@GetMapping("/song")
	//	public String song() {
	//		return "newsong";
	//	}
	@GetMapping("/viewsongs")
	public String viewSongs(Model model) {
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "displaysongs";
	}
	@GetMapping("/playsongs")
	public String playSongs(Model model) {
		boolean premium = true;
		if(premium) {
			List<Song> songList = songService.fetchAllSongs();
			model.addAttribute("songs", songList);
			return "displaysongs";
		} else {
			return "subscription";
		}
	}

}
