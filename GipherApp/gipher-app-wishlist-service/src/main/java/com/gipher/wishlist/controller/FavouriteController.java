package com.gipher.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gipher.wishlist.exception.GifNotFoundException;
import com.gipher.wishlist.model.Favourite;
import com.gipher.wishlist.service.FavouriteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/favourites")
public class FavouriteController {
	
	@Autowired
	private FavouriteService favouriteService;

	@PostMapping("/addToFavourites")
	public ResponseEntity<?> saveGifToFavourites(@RequestBody Favourite f) {
		try {
			Favourite fv = favouriteService.saveFavourite(f);
			if (fv != null) {
				return new ResponseEntity<Favourite>(fv, HttpStatus.CREATED);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping("/getAllFavourites")
	public ResponseEntity<?> getAllFavourites() {
		List<Favourite> list = favouriteService.getAllFavourites();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/getAllFavouritesByEmailId/{emailId}")
	public ResponseEntity<List<Favourite>> getAllFavouritesByEmailId(@PathVariable("emailId") String emailId) {
		List<Favourite> list = favouriteService.getFavouritesByEmailId(emailId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteFromFavourites/{id}")
	public ResponseEntity<?> deleteFromFavourites(@PathVariable("id") String id) throws GifNotFoundException {
		favouriteService.deleteFromFavourites(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
