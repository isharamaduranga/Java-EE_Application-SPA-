/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/27/2022
 * Time        : 10:12 PM
 * Year        : 2022
 */

package servlet;

import db.DBConnection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonArrayBuilder array = Json.createArrayBuilder();
        try {
            PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement("select * from item");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("code",rst.getString("code"));
                object.add("description",rst.getString("description"));
                object.add("qtyOnHand",rst.getString("qtyOnHand"));
                object.add("unitPrice",rst.getString("unitPrice"));
                array.add(object.build());
            }

            resp.setContentType("application/json");

            JsonObjectBuilder jsonRespObj = Json.createObjectBuilder();
            jsonRespObj.add("state","done");
            jsonRespObj.add("message","Successfully load..");
            jsonRespObj.add("data",array.build());
            resp.getWriter().print(jsonRespObj.build());


        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state","error");
            jsonObj.add("message",e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(400);
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state","error");
            jsonObj.add("message",e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(500);
        }

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        String description = req.getParameter("description");
        String qtyOnHand = req.getParameter("qtyOnHand");
        String unitPrice = req.getParameter("unitPrice");

        try {
            PreparedStatement pst =
                    DBConnection.getDbConnection().getConnection().prepareStatement("insert into item values(?,?,?,?)");
            pst.setObject(1,code);
            pst.setObject(2,description);
            pst.setObject(3,qtyOnHand);
            pst.setObject(4,unitPrice);

            boolean isSaved = pst.executeUpdate() > 0;

            if (isSaved) {
                JsonObjectBuilder jsonObj = Json.createObjectBuilder();
                jsonObj.add("state","done");
                jsonObj.add("message","Successfully Added Item Record...");
                resp.getWriter().print(jsonObj.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state","error");
            jsonObj.add("message",e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(400);

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state","error");
            jsonObj.add("message",e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        try {
            PreparedStatement pst =
                    DBConnection.getDbConnection().getConnection().prepareStatement("delete from item where code=?");
            pst.setObject(1,code);
            boolean isDeleted = pst.executeUpdate() > 0;

            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            if (isDeleted) {
                jsonObj.add("state","done");
                jsonObj.add("message","Successfully Deleted..");
            }else {
                jsonObj.add("state","error");
                jsonObj.add("message","No Such a Item To Delete !!!.");
                resp.setStatus(400);
            }
            resp.getWriter().print(jsonObj.build());

        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state","error");
            jsonObj.add("message",e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(400);

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state","error");
            jsonObj.add("message",e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
