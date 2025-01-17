package poly.edu.Controller.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setIdSubcategories(rs.getInt("id_subcategories"));
                product.setName(rs.getString("name"));
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getFloat("price"));
                product.setQty(rs.getInt("qty"));
                product.setDescription(rs.getString("description"));
                product.setStatus(rs.getInt("status"));
                products.add(product);
            }
        }
        return products;
    }

    public int insertProduct(int idSubcategories, String name, String image, float price, int qty, String description, int status) throws SQLException {
        String sql = "INSERT INTO product (id_subcategories, name, image, price, qty, description, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idSubcategories);
            stmt.setString(2, name);
            stmt.setString(3, image);
            stmt.setFloat(4, price);
            stmt.setInt(5, qty);
            stmt.setString(6, description);
            stmt.setInt(7, status);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
}

