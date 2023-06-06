/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoryDAO extends DBContext{
    
        public ArrayList<Category> getAll() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CATEGORIES";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getBoolean("Status"),
                        rs.getString("Description"),
                        rs.getBoolean("isParent"),
                        getCategoryByID(rs.getInt("parentID"))));
            }
        } catch (Exception e) {
            System.out.println("get list categories");
        }
        return list;
    }

    public Category getCategoryByID(int CategoryID) {
        try {
            String sql = "SELECT * FROM CATEGORIES"
                    + " WHERE CategoryID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, CategoryID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getBoolean("Status"),
                        rs.getString("Description"),
                        rs.getBoolean("isParent"),
                        getCategoryByID(rs.getInt("parentID")));
            }
        } catch (Exception e) {
            System.out.println("get category by id");
        }
        return null;
    }
    
    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        //Test getAll()
        ArrayList<Category> list = dao.getAll();
        System.out.println(list.get(1));
        
        //TestgetCategoryByID
        Category ctg = dao.getCategoryByID(1);
        System.out.println(ctg);
    }
}
