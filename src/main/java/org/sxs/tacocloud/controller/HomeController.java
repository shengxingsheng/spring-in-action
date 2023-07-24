package org.sxs.tacocloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @since 2023/7/22
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		Integer i = 1;
		String a = " "+i;
		System.out.println("Home.。");
		return "home";
	}
}
