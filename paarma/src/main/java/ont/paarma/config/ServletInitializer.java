package ont.paarma.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Servlet configuration class. (Java class equivalent to web.xml.)
 * Detected automatically by SpringServletContainerInitializer,
 * which is automatically called by any servlet.
 * 
 * @author Inka
 */

public class ServletInitializer extends
AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}