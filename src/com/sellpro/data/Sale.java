package com.sellpro.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.sellpro.utils.Database;

public class Sale {
    public int id;
    public String customer;
    public Date date;
    public Time time;
    public List<SaleProduct> products;

    public Sale() {
        id = 0;
        date = new Date(System.currentTimeMillis());
        time = new Time(System.currentTimeMillis());
        products = new ArrayList<SaleProduct>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
    public List<SaleProduct> getProducts() {
    	return products;
    }
    
    public double getAmount() {
    	double amount = 0.0;
    	
    	for (int i = 0; i < products.size(); ++i) {
    		SaleProduct product = products.get(i);
    		amount += product.product.price * product.quantity;
    	}
    	
    	return amount;
    }

    public boolean get() {
        return get(id);
    }

    public boolean get(int id) {
        Database db = Database.getInstance();

        PreparedStatement query = db.prepare("SELECT * FROM Sales WHERE id = ?");
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
            this.customer = result.getString(2);
            this.date = result.getDate(3);
            this.time = result.getTime(4);
            this.products = SaleProduct.getAll("sale_id = " + String.valueOf(id));
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update() {
        Database db = Database.getInstance();

        PreparedStatement query = db.prepare("UPDATE Sales SET customer=?, date=?, time=? WHERE id=?");
        try {
            query.setString(1, customer);
            query.setDate(2, date);
            query.setTime(3, time);
            query.setInt(4, id);
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

        PreparedStatement query = db.prepare("INSERT INTO Sales(customer, date, time) VALUES(?, ?, ?)");
        try {
            query.setString(1, customer);
            query.setDate(2, date);
            query.setTime(3, time);
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

        PreparedStatement query = db.prepare("DELETE FROM Sales WHERE id = ?");
        try {
            query.setInt(1, id);
            return query.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static List<Sale> getAll() {
        Database db = Database.getInstance();
        ResultSet results = db.execSelect("SELECT * FROM Sales");
        List<Sale> sales = new ArrayList<>();
        try {
            while (results.next()) {
                Sale sale = new Sale();
                sale.getFromResult(results);
                sales.add(sale);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sales;
    }
}
