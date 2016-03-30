/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import sg.edu.ntu.hrms.config.AppConfig;

/**
 *
 * @author michael-PC
 */
public class WebInit implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext
            = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);
         
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
        servletContext.addListener(contextLoaderListener);
        /*
        FilterRegistration.Dynamic filter = servletContext.addFilter(
                "StrutsDispatcher", new StrutsPrepareAndExecuteFilter());
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        */
    }
}
