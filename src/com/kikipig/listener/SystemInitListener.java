package com.kikipig.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

import com.kikipig.configure.SystemConfigure;

public class SystemInitListener implements ServletContextListener, WebApplicationInitializer {
	
	public static final String UPLOAD_CONTEXTPATH = "upload_contextpath";
	public static final String PC_URL = "pc_url";
	public static final String H5_URL = "h5_url";
	@Override
	public void onStartup(ServletContext arg0) throws ServletException {
	}
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	public void contextInitialized(ServletContextEvent event) {
		ServletContext application = event.getServletContext();
		application.setAttribute(UPLOAD_CONTEXTPATH, SystemConfigure.getUpload_path());
		application.setAttribute(PC_URL, SystemConfigure.getPc_url());
		application.setAttribute(H5_URL, SystemConfigure.getH5_url());
	}
}