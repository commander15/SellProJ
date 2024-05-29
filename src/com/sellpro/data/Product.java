package com.sellpro.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sellpro.utils.Database;

public class Product {
	public int id;
	public String name;
	public String description;
	public double price;
	public int in_stock;
	
	public Product() {
		id = 0;
		price = 0.0;
		in_stock = 0;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getIn_stock() {
		return in_stock;
	}

	public void setIn_stock(int in_stock) {
		this.in_stock = in_stock;
	}
	
	public boolean get() {
		return get(id);
	}
	
	public boolean get(int id) {
		Database db = Database.getInstance();
		
		PreparedStatement query = db.prepare("SELECT * FROM Products WHERE id = ?");
		try {
			query.setInt(1, id);
			query.execute();
			
			ResultSet result = query.getResultSet();
			if (result.next()) {
				getFromResult(result);
				return true;
			} else
				return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public boolean getFromResult(ResultSet result) {
		try {
			this.id = result.getInt(1);
			this.name = result.getString(2);
			this.description = result.getString(3);
			this.price = result.getDouble(4);
			this.in_stock = result.getInt(5);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public boolean update() {
		Database db = Database.getInstance();
		
		PreparedStatement query = db.prepare("UPDATE Products SET name=?, description=?, price=?, in_stock=?");
		try {
			query.setString(1, name);
			query.setString(2, description);
			query.setDouble(3, price);
			query.setInt(4, in_stock);
			return query.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public boolean save() {
		if (id <= 0)
			return insert();
		else
			return update();
	}
	
	public boolean insert() {
		Database db = Database.getInstance();
		
		PreparedStatement query = db.prepare("INSERT INTO Products(name, description, price, in_stock) VALUES(?, ?, ?, ?)");
		try {
			query.setString(1, name);
			query.setString(2, description);
			query.setDouble(3, price);
			query.setInt(4, in_stock);
			query.execute();

			ResultSet result = query.getGeneratedKeys();
			if (result.next())
				id = result.getInt(1);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public boolean delete() {
		Database db = Database.getInstance();
		
		PreparedStatement query = db.prepare("DELETE FROM Products WHERE id = ?");
		try {
			query.setInt(1, id);
			return query.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public static List<Product> getAll() {
		Database db = Database.getInstance();
		ResultSet results = db.execSelect("SELECT * FROM Products");
		List<Product> products = new ArrayList<Product>();
		try {
			while (results.next()) {
				Product product = new Product();
				product.getFromResult(results);
				products.add(product);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return products;
	}
}