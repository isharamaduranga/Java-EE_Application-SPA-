/**
 * @author : Ishara Maduarnga
 * Project Name: Java-EE_Application
 * Date        : 12/28/2022
 * Time        : 11:37 AM
 * Year        : 2022
 */

package filters;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*")
public class CORSFilter {
}
