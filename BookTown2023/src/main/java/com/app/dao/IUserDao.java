package com.app.dao;

import java.util.List;

import com.app.pojos.Cart;
import com.app.pojos.CartItem;
import com.app.pojos.OrderDetails;
import com.app.pojos.User;

public interface IUserDao {
	
	public boolean RegisterUser(User user);
	public User AuthenticateUser(String email, String password);
	public CartItem AddToCart(int productid, int qty);
	public boolean PlaceOrder(Cart cart, User user);
	public User getUserDetails(int userId);
	public List<OrderDetails> getOrder(int userId);
	
}
