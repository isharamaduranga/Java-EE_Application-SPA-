/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/29/2022
 * Time        : 2:13 PM
 * Year        : 2022
 */

package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/purchase")
public class PurchaseOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
