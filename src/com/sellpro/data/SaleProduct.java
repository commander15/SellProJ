package com.sellpro.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sellpro.utils.Database;

public class SaleProduct {
    public int id;
    public int quantity;
    public Product product;
    public int saleId = 0;

    public SaleProduct() {
        id = 0;
        quantity = 0;
        product = new Product();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean get() {
        return get(id);
    }

    public boolean get(int id) {
        Database db = Database.getInstance();

        PreparedStatement query = db.prepare("SELECT * FROM SaleProducts WHERE id = ?");
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
            this.id = result.getInt("id");
            this.quantity = result.getInt("quantity");
            this.saleId = result.getInt("sale_id");

            // Fetch product based on product_id
            int productId = result.getInt("product_id");
            Product product = new Product();
            if (product.get(productId)) {
                this.product = product;
                return true;
            } else {
                System.out.println("Product with id " + productId + " not found.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update() {
        Database db = Database.getInstance();

        PreparedStatement query = db.prepare("UPDATE SaleProducts SET quantity=?, product_id=? WHERE id=?");
        try {
            query.setInt(1, quantity);
            query.setInt(2, product.getId());
            query.setInt(3, id);
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

        PreparedStatement query = db.prepare("INSERT INTO SaleProducts(quantity, product_id, sale_id) VALUES(?, ?, ?)");
        try {
            query.setInt(1, quantity);
            query.setInt(2, product.getId());
            query.setInt(3, saleId);
            return query.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean delete() {
        Database db = Database.getInstance();

        PreparedStatement query = db.prepare("DELETE FROM SaleProducts WHERE id = ?");
        try {
            query.setInt(1, id);
            return query.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static List<SaleProduct> getAll(String filter) {
        Database db = Database.getInstance();
        ResultSet results = db.execSelect("SELECT * FROM SaleProducts WHERE " + filter);
        List<SaleProduct> saleProducts = new ArrayList<>();
        try {
            while (results.next()) {
                SaleProduct saleProduct = new SaleProduct();
                saleProduct.getFromResult(results);
                saleProducts.add(saleProduct);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return saleProducts;
    }
}
