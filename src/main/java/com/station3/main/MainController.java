package com.station3.main;

import com.station3.login.dto.CustomUserDetailsDto;
import com.station3.login.util.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

	/**
	 * @content : intro
	 * @name :
	 * @date : 2021-10-11
	 * @author : 신동아
	 * @return : ModelAndView
	 **/
	@RequestMapping(value = "")
	public ModelAndView index(@User CustomUserDetailsDto user) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");

		return mav;
	}
}
