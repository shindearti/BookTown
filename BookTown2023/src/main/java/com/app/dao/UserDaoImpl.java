package com.app.dao;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.app.pojos.Cart;
import com.app.pojos.CartItem;
import com.app.pojos.Supplier;
import com.app.pojos.OrderDetails;
import com.app.pojos.Orders;
import com.app.pojos.Book;
import com.app.pojos.User;


@Repository
public class UserDaoImpl implements IUserDao {

	@PersistenceContext
	private EntityManager mgr;

	@Override
	public boolean RegisterUser(User user) {
		boolean success = false;
		mgr.persist(user);
		success = true;
		return success;
	}

	@Override
	public User AuthenticateUser(String email, String password) {
		String jpql = "SELECT u FROM User u WHERE email=:em and password=:pass";
		User user = mgr.createQuery(jpql, User.class).setParameter("em", email)
				.setParameter("pass", password).getSingleResult();
		if (user != null)
			return user;
		return null;
	}

	@Override
	public CartItem AddToCart(int productid, int qty) {
		String jpql = "SELECT NEW com.app.pojos.Book(b.id, b.title,b.author,b.stock, b.unitPrice,b.category,b.imagePath) FROM Book b  WHERE b.id=:Id";
		String jpql2 = "SELECT b.supplier FROM Book b JOIN b.supplier s WHERE b.id=:pid";
		Book product = mgr.createQuery(jpql, Book.class)
				.setParameter("Id", productid).getSingleResult();
		Supplier sid = mgr.createQuery(jpql2, Supplier.class).setParameter("pid", productid)
				.getSingleResult();

		CartItem item = new CartItem();
		item.setId(product.getId());
		item.setItem(product.getTitle());
		item.setPrice(product.getUnitPrice());
		item.setQty(qty);
		item.setAmount(qty * product.getUnitPrice());
		item.setSupplier_id(sid.getSupplierId());

		return item;
	}

	@Override
	public boolean PlaceOrder(Cart cart, User user) {
		Orders order = new Orders();
		List<CartItem> items = cart.getItems();

		for (CartItem item : items) {
			OrderDetails details = new OrderDetails();
			details.setAmount(item.getAmount());
			details.setOrderItem(item.getItem());
			details.setQuantity(item.getQty());
			Supplier f = mgr.find(Supplier.class, item.getSupplier_id());
			details.setSupplier(f);
			order.getOrderDetails().add(details);
		}
		
		order.setDeliveryStatus(false);
		order.setPaymentStatus(true);
		order.setUser(user);
		
		long millis=System.currentTimeMillis();  
		order.setPlaceOrderDate(new java.sql.Date(millis));
		
		java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
		int futureDay =3;
		java.sql.Date futureDate = this.addDays(todaysDate, futureDay);
		order.setDeliveryDate(futureDate);
		
		mgr.persist(order);
		for(OrderDetails det : order.getOrderDetails()) {
			det.setOrders(order);
			mgr.persist(det);
		}
		return true;
	}
	
	 public Date addDays(Date date, int days) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.DATE, days);
	        return new Date(c.getTimeInMillis());
	    }
	 
	 @Override
	 public User getUserDetails(int userId) {
		 String jpql = "SELECT NEW com.app.pojos.User(u.userId, u.firstname, u.lastname, u.email, u.phoneNo, u.address, u.password) FROM User u WHERE u.userId=:userId";
			return mgr.createQuery(jpql, User.class).setParameter("userId", userId).getSingleResult();
		}
	 
	 @Override
		public List<OrderDetails> getOrder(int userId) {
			 String jpql = "SELECT NEW com.app.pojos.OrderDetails(o.id, o.orderItem, o.quantity, o.amount, o.orders) FROM OrderDetails o "
			 		+ "JOIN o.orders ord JOIN ord.user u WHERE u.userId =:userid";
			 return mgr.createQuery(jpql, OrderDetails.class).setParameter("userid", userId).getResultList();
		}

}
