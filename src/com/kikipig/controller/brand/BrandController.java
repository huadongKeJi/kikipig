package com.kikipig.controller.brand;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/brand")
public class BrandController{
	@RequestMapping(value = "/bondNotice.html")
	public String bondNotice(){
		return "front/help/bond_notice";
	}
	
}
