package com.dawn.controller;

import com.dawn.pojo.ActiveUser;
import com.dawn.pojo.TbTitle;
import com.dawn.service.TbtitleHandleService;
import com.dawn.util.DawnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TbtitleHandleController {
	/**
	 * 给公司添加题目和答案
	 */
	@Autowired
	private TbtitleHandleService service;

	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	@ResponseBody
	public DawnResult saveTitle(TbTitle title, String titleDesc, Long companyId,HttpServletRequest request) throws Exception {
		//从session中获取登陆信息
		ActiveUser activeUser =  (ActiveUser) request.getSession().getAttribute("activeUser");
		service.saveTitle(title, titleDesc, companyId,activeUser);
		return DawnResult.ok();
	}
}
