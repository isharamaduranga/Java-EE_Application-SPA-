/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/28/2022
 * Time        : 10:34 PM
 * Year        : 2022
 */

package listeners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/d99");
        bds.setUsername("root");
        bds.setPassword("1234");
        bds.setMaxTotal(2);// How many Total connections you need inside the pool
        bds.setInitialSize(2);// How many connections  should  initialize from  the total connections

        /** set the pool servlet Container use in servletContext setAttribute("name","value") */
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("dbcp",bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
