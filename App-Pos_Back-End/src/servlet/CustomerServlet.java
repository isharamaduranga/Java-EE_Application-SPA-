/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/26/2022
 * Time        : 1:28 PM
 * Year        : 2022
 */

package servlet;

import db.DBConnection;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /** Get Connection using dbcp(BasicDataSource) pool getAttribute Method */
        try(Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()){

            //How to Manipulate JSON using Json Processing
            JsonArrayBuilder array = Json.createArrayBuilder();

            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {

                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("id", rst.getString("id"));
                object.add("name", rst.getString("name"));
                object.add("address", rst.getString("address"));
                object.add("salary", rst.getDouble("salary"));

                array.add(object.build());
            }

            JsonObjectBuilder jsonRespObj = Json.createObjectBuilder();
            jsonRespObj.add("state", "done");
            jsonRespObj.add("message", "Successfully load..");
            jsonRespObj.add("data", array.build());
            resp.getWriter().print(jsonRespObj.build());

        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(400);
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

            if (isSaved) {

                JsonObjectBuilder jsonObj = Json.createObjectBuilder();
                jsonObj.add("state", "done");
                jsonObj.add("message", "Successfully Added Customer Record...");
                resp.getWriter().print(jsonObj.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(500);

        } catch (SQLException e) {

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        try {
            PreparedStatement pst2 =
                    DBConnection.getDbConnection().getConnection().prepareStatement("delete from customer where id=?");

            pst2.setObject(1, id);
            boolean isDeleted = pst2.executeUpdate() > 0;

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            if (isDeleted) {
                jsonObj.add("state", "done");
                jsonObj.add("message", "Successfully Deleted..");
            } else {
                jsonObj.add("state", "error");
                jsonObj.add("message", "No Such a Customer To Delete !!!.");
                resp.setStatus(400);
            }
            resp.getWriter().print(jsonObj.build());

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(500);

        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(400);
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

        try {
            PreparedStatement pst1 =
                    DBConnection.getDbConnection().getConnection().prepareStatement("update customer  set name=?,address=?, salary=? where id=?");

            pst1.setObject(4, id);
            pst1.setObject(1, name);
            pst1.setObject(2, address);
            pst1.setObject(3, salary);
            boolean isUpdated = pst1.executeUpdate() > 0;

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            if (isUpdated) {

                jsonObj.add("state", "done");
                jsonObj.add("message", "Successfully Updated...");

            } else {
                jsonObj.add("state", "error");
                jsonObj.add("message", "No Such a Customer To Updated !!!.");
                resp.setStatus(400);
            }
            resp.getWriter().print(jsonObj.build());
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(500);

        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(404);
        }
    }
}
