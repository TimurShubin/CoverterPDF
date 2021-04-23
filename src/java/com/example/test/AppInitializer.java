package com.example.test;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.test.configs.WebConfig;
import com.example.test.utils.FolderCleaner;

public class AppInitializer implements WebApplicationInitializer {
	
	private String TMP_FOLDER = "/tmp"; 
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
    
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);
		
		container.addListener(new ContextLoaderListener(context));
		context.setServletContext(container);
		
		ServletRegistration.Dynamic dispatcher = container.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
		dispatcher.setMultipartConfig(multipartConfigElement);
		
		// service for looking for unusable folders
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				FolderCleaner folderCleaner = new FolderCleaner();
				folderCleaner.startCounter();				
			}
		});
		t.start();
	}

}
