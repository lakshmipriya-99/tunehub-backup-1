package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.tunehub.entity.Playlist;
import com.kodnest.tunehub.entity.Song;

import com.kodnest.tunehub.service.PlaylistService;
import com.kodnest.tunehub.service.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	//model is used to render the data from back end to the front end
	@GetMapping("/createplaylist")
	public String createPlaylist(Model model) {
		//list of songs 
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createplaylist";
	}
	
	@PostMapping("/addplaylist")
	//mode attribute is used to render the data from front end to back end
	public String addplaylist(@ModelAttribute Playlist playlist ) {
		//adding songs to the playlists
		playlistService.addplaylist(playlist);
		//getting the playlist table songs individually from song table 
		List<Song> songList = playlist.getSongs();
		//updating the song table so that to know which song belongs to the created playlist
		for(Song s : songList) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);//day8
			
		}
		return "adminhome";
	}
	///day 8 below
	
	@GetMapping("/viewplaylists")
	public String viewplaylists(Model model) {
		List<Playlist> allPlaylist = playlistService.fetchAllPlaylists();
		model.addAttribute("allplaylist" , allPlaylist);
		return "displayplaylist";
	}

}
