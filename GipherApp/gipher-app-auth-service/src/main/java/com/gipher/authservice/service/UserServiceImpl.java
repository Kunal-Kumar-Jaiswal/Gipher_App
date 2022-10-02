package com.gipher.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import com.gipher.authservice.exception.UserAlreadyExistsException;
import com.gipher.authservice.model.User;
import com.gipher.authservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public User registerUser(User user) throws UserAlreadyExistsException {
		User userExists = getByEmail(user.getEmailId());

		if (userExists != null) {
			throw new UserAlreadyExistsException("User with this email already Exists!");
		} else {
			return userRepo.save(user);
		}
	}

	@Override
	public User authenticateUser(String emailId, String password) {

		User user = getByEmail(emailId);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<User> getUserByEmail(String emailId) {
		List<User> listUser = (List<User>) userRepo.findAll();
		User requiredUser = null;
		for (User user : listUser) {
			if (user.getEmailId().equals(emailId)) {
				requiredUser = user;
			}
		}
		return new ResponseEntity<User>(requiredUser, HttpStatus.OK);
	}
	
	@Override
	public User getByEmail(String email) {
		List<User> listUser = (List<User>) userRepo.findAll();
		for (User user : listUser) {
			if (user.getEmailId().equals(email)) {
				return user;
			}
		}
		return null;
	}
	
	@Override
	public User updateUser(User updatedData) {
		User user = getByEmail(updatedData.getEmailId());
		if (user != null) {
			user.setMobileNumber(updatedData.getMobileNumber());
			user.setPassword(updatedData.getPassword());
			user.setUserName(updatedData.getUserName());
			user.setImage(updatedData.getImage());
			return userRepo.save(user);
		}
		return null;
	}
	
	@Override
	public boolean removeUser(String emailId) {
		userRepo.deleteByEmailId(emailId);
		User deletedUser = getByEmail(emailId);
		if (deletedUser == null) {
			return true;
		}
		return false;
	}
}
