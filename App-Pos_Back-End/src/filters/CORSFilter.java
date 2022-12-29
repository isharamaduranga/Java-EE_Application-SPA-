/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/28/2022
 * Time        : 11:37 AM
 * Year        : 2022
 */

package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        /** Invoked Do-filter method for send the requests & response to related servlets*/
        filterChain.doFilter(servletRequest,servletResponse);

        /** Cross Policy Header */
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        resp.setContentType("application/json");//MIME Type

        //For Delete record
        resp.addHeader("Access-Control-Allow-Origin", "*");

        //For Update(PUT) record
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

        //For Delete & Update(PUT) records
        resp.addHeader("Access-Control-Allow-Methods", "DELETE,PUT");

    }

    @Override
    public void destroy() {

    }
}
