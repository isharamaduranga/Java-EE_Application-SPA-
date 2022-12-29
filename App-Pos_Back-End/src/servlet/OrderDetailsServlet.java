/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/29/2022
 * Time        : 8:54 PM
 * Year        : 2022
 */

package servlet;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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

@WebServlet(urlPatterns = "/OrderDetails")
public class OrderDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /** Get Connection using dbcp(BasicDataSource) pool getAttribute Method */
        try(Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()){


            //How to Manipulate JSON using Json Processing
            JsonArrayBuilder orderArray = Json.createArrayBuilder();
            JsonArrayBuilder orderDetailsArray = Json.createArrayBuilder();

            PreparedStatement pstm = connection.prepareStatement("select * from Orders");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {

                JsonObjectBuilder object = Json.createObjectBuilder();
                object.add("oid", rst.getString("oid"));
                object.add("date", rst.getString("date"));
                object.add("customerID", rst.getString("customerID"));

                orderArray.add(object.build());
            }

            PreparedStatement pstm2 = connection.prepareStatement("select * from orderDetails");
            ResultSet rst2 = pstm2.executeQuery();

            while (rst2.next()) {
                JsonObjectBuilder object1 = Json.createObjectBuilder();
                object1.add("orderID", rst2.getString("orderID"));
                object1.add("itemCode", rst2.getString("itemCode"));
                object1.add("orderQty", rst2.getInt("orderQty"));
                object1.add("price", rst2.getDouble("price"));

                orderDetailsArray.add(object1.build());
            }

            JsonObjectBuilder jsonRespObj = Json.createObjectBuilder();
            jsonRespObj.add("state", "done");
            jsonRespObj.add("message", "Successfully load..");
            jsonRespObj.add("data", orderArray.build());
            jsonRespObj.add("dataTwo", orderDetailsArray.build());
            resp.getWriter().print(jsonRespObj.build());

        } catch (SQLException e) {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("state", "error");
            jsonObj.add("message", e.getMessage());
            resp.getWriter().print(jsonObj.build());
            resp.setStatus(400);
        }
    }
}
