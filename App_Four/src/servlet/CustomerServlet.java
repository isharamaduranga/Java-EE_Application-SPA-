/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/26/2022
 * Time        : 1:28 PM
 * Year        : 2022
 */

package servlet;

import db.DBConnection;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            //How to Manipulate JSON using Json Processing
            JsonArrayBuilder array = Json.createArrayBuilder();

            PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {

                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("id", rst.getString("id"));
                object.add("name", rst.getString("name"));
                object.add("address", rst.getString("address"));
                object.add("salary", rst.getDouble("salary"));

                array.add(object.build());
            }

            resp.setContentType("application/json"); //MIME TYPE*/
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

        try {
            PreparedStatement pst =
                    DBConnection.getDbConnection().getConnection().prepareStatement("insert into customer values(?,?,?,?)");

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
            PreparedStatement pst2 =
                    DBConnection.getDbConnection().getConnection().prepareStatement("delete from customer where id=?");

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



        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customer = reader.readObject();

        String id = customer.getString("id");
        String name = customer.getString("name");
        String address = customer.getString("address");
        String salary = customer.getString("salary");

        try{
            PreparedStatement pst1 =
                    DBConnection.getDbConnection().getConnection().prepareStatement("update customer  set name=?,address=?, salary=? where id=?");

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
