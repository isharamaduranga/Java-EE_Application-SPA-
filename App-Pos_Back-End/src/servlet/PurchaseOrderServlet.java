/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/29/2022
 * Time        : 2:13 PM
 * Year        : 2022
 */

package servlet;

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
import java.sql.SQLException;

@WebServlet(urlPatterns = "/purchase")
public class PurchaseOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        try {
             connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection();
             connection.setAutoCommit(false);

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject readObject = reader.readObject();

            String oid = readObject.getString("oid");
            String date = readObject.getString("date");
            String cusID = readObject.getString("cusID");

            PreparedStatement pstm = connection.prepareStatement("Insert into Orders values (?,?,?)");
            pstm.setObject(1,oid);
            pstm.setObject(1,date);
            pstm.setObject(1,cusID);
            if (!(pstm.executeUpdate() > 0)) {
                connection.rollback();
                throw new RuntimeException("Can't Save The Order cuz Something went wrong!!!");
            }else {
                JsonArray orderDetails = readObject.getJsonArray("orderDetails");
                for (JsonValue oDetails : orderDetails) {
                    String code = oDetails.asJsonObject().getString("code");
                    String qty = oDetails.asJsonObject().getString("qty");
                    String price = oDetails.asJsonObject().getString("price");

                    PreparedStatement pstm2 = connection.prepareStatement("Insert into OrderDetails values (?,?,?,?)");
                    pstm2.setObject(1,oid);
                    pstm2.setObject(1,code);
                    pstm2.setObject(1,qty);
                    pstm2.setObject(1,price);

                    if (!(pstm2.executeUpdate()>0)) {
                        connection.rollback();
                        throw new RuntimeException("There is a Problem With Order Details.");
                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
                connection.close();

                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state","done");
                responseObject.add("message","Successfully Purchased");
                responseObject.add("data","");
                resp.getWriter().print(responseObject.build());

            }


        }catch (SQLException |RuntimeException e){
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
                connection.close();
            }catch (SQLException ex){
                jsonObject.add("state","error");
                jsonObject.add("message",e.getMessage());
            }
            jsonObject.add("state","error");
            jsonObject.add("message",e.getMessage());
            resp.getWriter().print(jsonObject.build());
            resp.setStatus(500);
        }
    }
}
