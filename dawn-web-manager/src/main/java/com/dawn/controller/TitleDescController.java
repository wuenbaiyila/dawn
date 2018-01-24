package com.dawn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dawn.dto.TitleDescDto;
import com.dawn.service.TitleDescService;

@Controller
public class TitleDescController {

	@Autowired
	private TitleDescService service;

	/**
	 * 
	 * <p>
	 * Title: selectByTbtitleId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 根据题目id查询答案
	 * 
	 * @param tbtitleId
	 * @return
	 */
	@RequestMapping("/titledesc/{tbtitleId}")
	@ResponseBody
	public TitleDescDto selectByTbtitleId(@PathVariable long tbtitleId) {
		return service.selectByTbtitleId(tbtitleId);
	}
}
