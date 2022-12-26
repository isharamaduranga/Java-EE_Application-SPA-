/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/26/2022
 * Time        : 1:28 PM
 * Year        : 2022
 */


package servlet;

import db.DBConnection;
import model.CustomerDTO;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ArrayList<CustomerDTO> allCustomers = new ArrayList();

        try {
            PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                double salary = rst.getDouble("salary");

                allCustomers.add(new CustomerDTO(id, name, address, salary));
            }

            //How to Manipulate JSON using Json Processing
            JsonArrayBuilder array = Json.createArrayBuilder();

            for (CustomerDTO customer : allCustomers) {
                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("id", customer.getId());
                object.add("name", customer.getName());
                object.add("address", customer.getAddress());
                object.add("salary", customer.getSalary());

                array.add(object.build());
            }

            /*  resp.setContentType("application/json"); //MIME TYPE*/
            resp.getWriter().print(array.build());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");

        String option = req.getParameter("option");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/d99", "root", "1234");
            PreparedStatement pst = connection.prepareStatement("insert into customer values(?,?,?,?)");
            pst.setObject(1, id);
            pst.setObject(2, name);
            pst.setObject(3, address);
            pst.setObject(4, salary);

            boolean isSaved = pst.executeUpdate() > 0;


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/d99", "root", "1234");
            PreparedStatement pst2 = connection2.prepareStatement("delete from customer where id=?");
            pst2.setObject(1, id);
            boolean isDeleted = pst2.executeUpdate() > 0;

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/d99", "root", "1234");
            PreparedStatement pst1 = connection1.prepareStatement("update customer  set name=?,address=?, salary=? where id=?");
            pst1.setObject(4, id);
            pst1.setObject(1, name);
            pst1.setObject(2, address);
            pst1.setObject(3, salary);
            boolean isUpdated = pst1.executeUpdate() > 0;

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
