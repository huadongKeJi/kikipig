package com.kikipig.controller.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 跳转到错误页面Controller
 * 
 * @date 2017年3月6日13.
 */
@Controller
@RequestMapping(value = "/error",method = {RequestMethod.GET,RequestMethod.POST})
public class ErrorController {

	@RequestMapping("/404")
	public String toError_404(HttpServletRequest request, HttpServletResponse response) {
		return "404";
	}

	@RequestMapping(value = "/500")
	public String toError_500(HttpServletRequest request, HttpServletResponse response) {
		return "500";
	}

	@RequestMapping(value = "/403")
	public String toError_403(HttpServletRequest request, HttpServletResponse response) {
		return "403";
	}
}
