package com.kodnest.tunehub.service;

import java.util.List;

import com.kodnest.tunehub.entity.Playlist;

public interface PlaylistService {
	//day 7
	public void addplaylist(Playlist playlist);
	//day8
	public List<Playlist> fetchAllPlaylists();

}
