/*

package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/pages/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");

        String option = req.getParameter("option");
        try {
        switch (option) {
            case "add":
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/d99", "root", "1234");
                PreparedStatement pst = connection.prepareStatement("insert into customer values(?,?,?,?)");
                pst.setObject(1,id);
                pst.setObject(2,name);
                pst.setObject(3,address);
                pst.setObject(4,salary);

                boolean isSaved = pst.executeUpdate() > 0;
                break;


            case "update":
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/d99", "root", "1234");
                PreparedStatement pst1 = connection1.prepareStatement("update from customer  set name=?,address=?, salary=? where id=? ");
                pst1.setObject(4,id);
                pst1.setObject(1,name);
                pst1.setObject(2,address);
                pst1.setObject(3,salary);
                boolean isUpdated = pst1.executeUpdate() > 0;

                break;


            case "delete":
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/d99", "root", "1234");
                PreparedStatement pst2 = connection2.prepareStatement("delete from customer where id=?");
                        pst2.setObject(1,id);

                boolean isDeleted = pst2.executeUpdate() > 0;
                break;
        }
        resp.sendRedirect("customer_form.jsp");

        } catch (ClassNotFoundException| SQLException e) {
            e.printStackTrace();
        }


    }
}
*/
