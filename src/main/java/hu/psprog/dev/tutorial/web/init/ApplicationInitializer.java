package hu.psprog.dev.tutorial.web.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import hu.psprog.dev.tutorial.web.config.AppContextConfig;

/**
 * This initializer will be our "web.xml". Tomcat will find this class on classpath, and will start loading 
 * application context based on its content. The result will be the same (a fully functioning web application),
 * but without a single line of XML.
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        
        // now we have to set main application context configuration class first
        // if our configuration is separated to multiple files, only the one needed
        // which tells the context loader where to find the other configuration files (@ComponentScan)
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppContextConfig.class);
        
        // create servlet and add previously created context
        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("mvc-dispatcher", new DispatcherServlet(appContext));
        
        // same as with XML configuration, set mapping to / in order this servlet to handle all requests
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setLoadOnStartup(1);
        
        // finally create UTF-8 character encoding filter
        FilterRegistration characterEncodingFilter = servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
