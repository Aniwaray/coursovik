package com.example.coursework;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private final String host = "127.0.0.1";
    private final String port = "3306";
    private final String dbName = "coursework";
    private final String login = "root";
    private final String password = "vanessa2020k";

    private static int max, id;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connStr, login, password);
    }
    public ArrayList<String> getPrice(String name, String count) throws SQLException, ClassNotFoundException {

        String sql = "SELECT productPrice * '"+count+"' as productPrice FROM product where productName='" + name + "' ";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> productPrice = new ArrayList<>();
        while (resultSet.next())
            productPrice.add(resultSet.getString("productPrice"));
        return productPrice;
    }
    public int getPriceInt(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT productPrice FROM product where productName='" + name + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }
    public int getStaff(String log, String pas) throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) as n FROM staff where login=? and password=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pas);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }
    public int getClients(String log, String pas) throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) as n FROM clients where login=? and password=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pas);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }
    public String getRole(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM role join staff on role.id_role=staff.role_id_role where login='" + log + "' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String role = "";
        while (resultSet.next()) {
            role = resultSet.getString("name");
        }
        return role;
    }
    public String getFioClient(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT fio FROM clients where login='" + log + "' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("fio");
        }
        return name;
    }
    public String getFioStaff(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT fio FROM staff where login='" + log + "' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("fio");
        }
        return name;
    }
    public void clientAdd(String fio, String log, String pas, String tel) throws SQLException, ClassNotFoundException {
        String sql = "insert into clients values (?,?,?,?,?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
        preparedStatement.setInt(1, maxClient());
        preparedStatement.setString(2, fio);
        preparedStatement.setString(3, log);
        preparedStatement.setString(4, pas);
        preparedStatement.setString(5, tel);

        preparedStatement.executeUpdate();
    }
    public Integer maxClient() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id_clients) as max FROM clients";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        max = 0;
        while (res.next()) {
            max = res.getInt(1);
        }
        max += 1;
        return max;
    }
    public ArrayList<ProductData> getProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product ORDER BY `id_product`";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> product = new ArrayList<>();
        while (res.next())
            product.add(new ProductData(res.getString("productName"), res.getString("productDescription"), res.getInt("productPrice"), res.getInt("productQuantityInStock"), res.getString("productStatus"), res.getString("productPhoto"), res.getInt("category_id_category"), res.getInt("manufacture_id_manufacture"), res.getInt("model_cars_id_model")));
        return product;
    }
    public ArrayList getProductSearch(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product where productName like ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, "%" + name + "%");
        ResultSet res = statement.executeQuery();

        ArrayList<ProductData> product = new ArrayList<>();

        while (res.next())
            product.add(new ProductData(res.getString("productName"), res.getString("productDescription"), res.getInt("productPrice"), res.getInt("productQuantityInStock"), res.getString("productStatus"), res.getString("productPhoto"), res.getInt("category_id_category"), res.getInt("manufacture_id_manufacture"), res.getInt("model_cars_id_model")));
        return product;
    }
    public ArrayList<ProductData> getProductSortMan(Integer man) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product where manufacture_id_manufacture ='" + man + "'";;

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> product = new ArrayList<>();
        while (res.next())
            product.add(new ProductData(res.getString("productName"), res.getString("productDescription"), res.getInt("productPrice"), res.getInt("productQuantityInStock"), res.getString("productStatus"), res.getString("productPhoto"), res.getInt("category_id_category"), res.getInt("manufacture_id_manufacture"), res.getInt("model_cars_id_model")));
        return product;
    }
    public ArrayList<ProductData> getProductModel(Integer model) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product where model_cars_id_model ='" + model + "'";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> product = new ArrayList<>();
        while (res.next())
            product.add(new ProductData(res.getString("productName"), res.getString("productDescription"), res.getInt("productPrice"), res.getInt("productQuantityInStock"), res.getString("productStatus"), res.getString("productPhoto"), res.getInt("category_id_category"), res.getInt("manufacture_id_manufacture"), res.getInt("model_cars_id_model")));
        return product;
    }
    public ArrayList<String> getCategoryMain() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM category ORDER BY `id_category`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("name"));

        return address;
    }
    public ArrayList<String> getModelMain() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM model_cars ORDER BY `id_model`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("name"));

        return address;
    }
    public ArrayList<String> getManufactureMain() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM manufacture ORDER BY `id_manufacture`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("name"));

        return address;
    }
    public ArrayList<String> getStatusMain() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product ORDER BY `id_product`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("productStatus"));

        return address;
    }
    public ArrayList<String> getPoint() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM point ORDER BY `id_point`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("address"));

        return address;
    }
    public ArrayList<String> getClient() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM clients ORDER BY `id_clients`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("fio"));

        return address;
    }
    public ArrayList<String> getProductMain() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product ORDER BY `id_product`";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> address = new ArrayList<>();
        while(resultSet.next())
            address.add(resultSet.getString("productName"));

        return address;
    }
    public ArrayList<Integer> getCategory(String category) throws SQLException, ClassNotFoundException {
        String sql = "select id_product FROM product join category on product.category_id_category=category.id_category where category.name = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1,  category);

        ResultSet res = statement.executeQuery();
        ArrayList<Integer> stud = new ArrayList<>();
        while(res.next())
            stud.add(res.getInt("id_product"));
        return stud;
    }
    public ArrayList<Integer> getProductStatNo() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_product FROM product where productStatus = 'Отсутствует' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> stud = new ArrayList<>();
        while(res.next())
            stud.add(res.getInt("id_product"));
        return stud;
    }
    public ArrayList<Integer> getProductStat() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_product FROM product where productStatus = 'Присутствует' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> stud = new ArrayList<>();
        while(res.next())
            stud.add(res.getInt("id_product"));
        return stud;
    }
    public ArrayList<ProductData> getProduct2(int n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product where id_product = ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  n);

        ResultSet res = statement.executeQuery();
        ArrayList<ProductData> product = new ArrayList<>();

        while (res.next())
            product.add(new ProductData(res.getString("productName"), res.getString("productDescription"), res.getInt("productPrice"), res.getInt("productQuantityInStock"), res.getString("productStatus"), res.getString("productPhoto"), res.getInt("category_id_category"), res.getInt("manufacture_id_manufacture"), res.getInt("model_cars_id_model")));
        return product;
    }
    public String getManufactureString(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM manufacture where id_manufacture='" + id + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("name");
        }
        return name;
    }
    public String getCategoryString(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM category where id_category='" + id + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("name");
        }
        return name;
    }
    public Integer getIdProduct(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_product FROM product where productName =? ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();
        id = 0;
        while (res.next()) {
            id = res.getInt(1);
        }
        return id;
    }
    public int getOneProductPrice(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT productPrice FROM product where id_product=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, id);
        ResultSet res = statement.executeQuery();

        int tasks = 0;
        while (res.next()) {
            tasks=res.getInt("productPrice");
        }
        return tasks;
    }
    public String getOneProductPhoto(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT productPhoto FROM product where id_product=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks=res.getString("productPhoto");
        }
        return tasks;
    }
    public String getOneProductDescription(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT productDescription FROM product where id_product=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks=res.getString("productDescription");
        }
        return tasks;
    }
    public String getOneProductName(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT productName FROM product where id_product=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, id);
        ResultSet res = statement.executeQuery();
        String tasks = "";
        while (res.next()) {
            tasks = res.getString("productName");
        }
        return tasks;
    }
    public int getOneProductQuantityInStock(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT productQuantityInStock FROM product where id_product=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, id);
        ResultSet res = statement.executeQuery();

        int tasks = 0;
        while (res.next()) {
            tasks=res.getInt("productQuantityInStock");
        }
        return tasks;
    }
    public void insertProduct(String name, String desc, Integer price, Integer stock, String stat, String photo, Integer cat, Integer man, Integer mod) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO product VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, maxProduct());
        prSt.setString(2, name);
        prSt.setString(3, desc);
        prSt.setInt(4, price);
        prSt.setInt(5, stock);
        prSt.setString(6, stat);
        prSt.setString(7, photo);
        prSt.setInt(8, cat);
        prSt.setInt(9, man);
        prSt.setInt(10, mod);

        prSt.executeUpdate();
    }
    public Integer maxProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id_product) as max FROM product";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        max = 0;
        while (res.next()) {
            max = res.getInt(1);
        }
        max += 1;
        return max;
    }
    public Integer maxOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id_orders) as max FROM orders";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        max = 0;
        while (res.next()) {
            max = res.getInt(1);
        }
        max += 1;
        return max;
    }
    public Integer getCategoryForInsert(String name) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_category FROM category where name = '" + name + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_cat = 0;
        while (res.next()) {
            id_cat = res.getInt(1);
        }
        return id_cat;
    }
    public Integer getManufactureForInsert(String name) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_manufacture FROM manufacture where name = '" + name + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_cat = 0;
        while (res.next()) {
            id_cat = res.getInt(1);
        }
        return id_cat;
    }
    public Integer getModelForInsert(String name) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_model FROM model_cars where name = '" + name + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_cat = 0;
        while (res.next()) {
            id_cat = res.getInt(1);
        }
        return id_cat;
    }
    public void insertOrder(Integer count, Integer finalPrice, Integer point, Integer product, Integer client) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES (?, current_date, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, maxOrder());
        prSt.setInt(2, count);
        prSt.setInt(3, finalPrice);
        prSt.setInt(4, point);
        prSt.setInt(5, product);
        prSt.setInt(6, client);

        prSt.executeUpdate();
    }
    public Integer getPointForInsert(String address) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_point FROM point where address = '" + address + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_cat = 0;
        while (res.next()) {
            id_cat = res.getInt(1);
        }
        return id_cat;
    }
    public Integer getProductForInsert(String name) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_product FROM product where productName = '" + name + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_cat = 0;
        while (res.next()) {
            id_cat = res.getInt(1);
        }
        return id_cat;
    }
    public Integer getClientForInsert(String fio) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_clients FROM clients where fio = '" + fio + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_cat = 0;
        while (res.next()) {
            id_cat = res.getInt(1);
        }
        return id_cat;
    }
    public void deleteProduct(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "delete from product where id_product = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);

        prSt.executeUpdate();
    }
    public void updateProduct(String name, String desc, Integer price, Integer stock, String photo, Integer cat, Integer man, Integer mod, Integer idProd) throws SQLException, ClassNotFoundException {
        String sql = "update product set productName =?, productDescription=?, productPrice =?, productQuantityInStock =?, productPhoto=?, category_id_category =?, manufacture_id_manufacture=?, model_cars_id_model=? where id_product=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setString(2, desc);
        prSt.setInt(3, price);
        prSt.setInt(4, stock);
        prSt.setString(5, photo);
        prSt.setInt(6, cat);
        prSt.setInt(7, man);
        prSt.setInt(8, mod);
        prSt.setInt(9, idProd);

        prSt.executeUpdate();
    }
    public Integer maxReviews() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id_reviews) as max FROM reviews";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        max = 0;
        while (res.next()) {
            max = res.getInt(1);
        }
        max += 1;
        return max;
    }
    public void insertReviews(String rating, String comment, Integer client, Integer product) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO reviews VALUES (?, ?, ?, ?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, maxReviews());
        prSt.setString(2, rating);
        prSt.setString(3, comment);
        prSt.setInt(4, client);
        prSt.setInt(5, product);

        prSt.executeUpdate();
    }
    public Integer getClientForReview(String log) throws SQLException, ClassNotFoundException{
        String sql = "SELECT id_clients FROM clients where login = '" + log + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        int id_client = 0;
        while (res.next()) {
            id_client = res.getInt(1);
        }
        return id_client;
    }
}