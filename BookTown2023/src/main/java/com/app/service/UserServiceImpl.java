package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.IUserDao;
import com.app.pojos.Cart;
import com.app.pojos.CartItem;
import com.app.pojos.OrderDetails;
import com.app.pojos.User;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao u_dao;

	@Override
	public boolean Register(User user) {
		u_dao.RegisterUser(user);
		return false;
	}

	@Override
	public User Authenticate(String email, String password) {
		User user = u_dao.AuthenticateUser(email, password);
		return user;
	}

	@Override
	public CartItem AddToCart(int productid, int qty) {
		System.out.println("in AddtoCart of UserService");
		return u_dao.AddToCart(productid, qty);
	}

	@Override
	public boolean PlaceOrder(Cart cart, User user) {
		return u_dao.PlaceOrder(cart, user);
	}

	@Override
	public User getUserDetails(int userId) {
		// TODO Auto-generated method stub
		return u_dao.getUserDetails(userId);
	}

	@Override
	public List<OrderDetails> getOrder(int userId) {
		return u_dao.getOrder(userId);
	}
}
