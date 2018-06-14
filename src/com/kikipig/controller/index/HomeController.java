package com.kikipig.controller.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kikipig.controller.BaseController;

/***
 * 棣栭〉Controller
 * 
 * @date 2017骞�6鏈�17鏃�10:52:54.
 */
@Controller
public class HomeController extends BaseController {



	
	/**
	 * 浼佷笟鏂囧寲
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index.html")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
	
		return "front/index";
	}
}