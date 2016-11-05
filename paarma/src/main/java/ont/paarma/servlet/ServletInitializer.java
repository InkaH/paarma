package ont.paarma.servlet;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ont.paarma.config.AppConfig;
import ont.paarma.config.SpringRootConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
 
public class ServletInitializer extends
AbstractAnnotationConfigDispatcherServletInitializer {

@Override
protected Class<?>[] getRootConfigClasses() {
return new Class[] { SpringRootConfig.class };
}

@Override
protected Class<?>[] getServletConfigClasses() {
return new Class[] { AppConfig.class };
}

@Override
protected String[] getServletMappings() {
return new String[] { "/" };
}

}