package com.gipher.wishlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gipher.wishlist.model.Favourite;
import com.gipher.wishlist.repository.FavouriteRepository;

@Service
public class FavouriteServiceImpl implements FavouriteService{

	@Autowired
	private FavouriteRepository favouriteRepository;

	@Override
	public Favourite saveFavourite(Favourite f) {
		return favouriteRepository.save(f);
	}

	@Override
	public List<Favourite> getAllFavourites() {
		return favouriteRepository.findAll();
	}

	public Favourite deleteFromFavourites(String id) {
		try {
			favouriteRepository.deleteById(id);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public List<Favourite> getFavouritesByEmailId(String emailId) {
		return favouriteRepository.findByEmailId(emailId);
	}
	
}
