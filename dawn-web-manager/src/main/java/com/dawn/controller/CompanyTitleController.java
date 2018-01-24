package com.dawn.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dawn.dto.CompanyTatilDto;
import com.dawn.pojo.ActiveUser;
import com.dawn.pojo.TbCompany;
import com.dawn.service.TbCompanyTitleService;
import com.dawn.util.DawnResult;
import com.dawn.util.Result;

@Controller
@RequestMapping("/companyTitle")
public class CompanyTitleController {
	@Autowired
	private TbCompanyTitleService companyTitleService;

	// 根据题目id查询公司提问明细
	@RequestMapping("/selectByTitleId/{titleId}")
	@ResponseBody
	public List getCompanyTitlebyTitleId(@PathVariable Long titleId, HttpSession session) {
		Result result = companyTitleService.getCompanyTitle((long) titleId);
		// 把题目id保存到session中，
		session.setAttribute("title_campany_add", titleId);
		return result.getRows();
	}

	// 查询所有公司
	@RequestMapping("/selectAllCompanyId")
	@ResponseBody
	public List<TbCompany> selectAllCompanyId() {
		return companyTitleService.getAllCompany();
	}

	// 对已有的公司提问新增公司提问次数
	@RequestMapping("/updateAskNum")
	@ResponseBody
	public DawnResult updateAskNum(CompanyTatilDto companyTatilDto, HttpSession session) {
		// 在查询题目是保存的session
		Long titleId = (Long) session.getAttribute("title_campany_add");
		companyTatilDto.setTitleId(titleId);
		return companyTitleService.updateAskNum(companyTatilDto);
	}

	// 新增公司提问（原来没有该公司）
	@RequestMapping("/insertAsk")
	@ResponseBody
	public DawnResult insertAsk(CompanyTatilDto companyTatilDto, HttpSession session) {
		// 在查询题目是保存的session
		Long titleId = (Long) session.getAttribute("title_campany_add");
		companyTatilDto.setTitleId(titleId);
		return companyTitleService.addAskNum(companyTatilDto);
	}

	// 根据公司名称模糊查询公司列表
	@RequestMapping("/selectByCompanyName/{param}")
	@ResponseBody
	public List<TbCompany> getByName(@PathVariable String param, HttpServletRequest request) {
		String params;
		ActiveUser activeUser = (ActiveUser) request.getSession().getAttribute("activeUser");
		Integer userType = activeUser.getUser_type();
		try {
			params = new String(param.getBytes("ISO-8859-1"), "utf-8");
			List<TbCompany> list = companyTitleService.getByName(params, userType);
			return list;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
