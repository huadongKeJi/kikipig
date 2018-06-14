package com.kikipig.listener;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.HttpSessionMutexListener;
/**
 * session监听类
 * @date 2017年2月24日16:30:11.
 */
public class SessionListener extends HttpSessionMutexListener{
	/**日志记录.*/
	private final Logger LOG = LogManager.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOG.info("[sessionCreated||SESSIONID=" + event.getSession().getId() + ",maxInactiveInterval="+ event.getSession().getMaxInactiveInterval() + "]");
		super.sessionCreated(event);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		LOG.info("[sessionDestroyed||SESSIONID=" + session.getId() + ",maxInactiveInterval="+ session.getMaxInactiveInterval() + ",lastAccessedTime:"+DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(new Date(session.getLastAccessedTime())) +"]");
		super.sessionDestroyed(event);
	}
}
