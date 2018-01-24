package com.dawn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dawn.pojo.TbCategory;
import com.dawn.service.TbCategoryService;
import com.dawn.util.MsgUtil;
import com.dawn.util.TreePojo;

@Controller
public class TbCategoryController {
	@Autowired
	private TbCategoryService tbCategoryService;

	// 查询知识点
	@RequestMapping("/title/tree")
	@ResponseBody
	public List<TreePojo> queryCategory(@RequestParam(value = "id", defaultValue = "0") long parentid) {
		return tbCategoryService.queryCategory(parentid);
	}

	// 新增知识点
	@RequestMapping("/saveTree")
	@ResponseBody
	public MsgUtil saveCategory(long targetId, String name, TbCategory tbCategory) {
		tbCategory.setParentid(targetId);
		tbCategory.setClassname(name);
		try {
			tbCategoryService.saveCategory(tbCategory);
			MsgUtil m = new MsgUtil();
			m.setTitle("提示信息");
			m.setMsg("保存成功");
			return m;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgUtil m = new MsgUtil();
			m.setTitle("提示信息");
			m.setMsg("保存成功");
			return m;
		}
	}

	// 修改知识点
	@RequestMapping("/updateTree")
	@ResponseBody
	public MsgUtil updateCategory(long targetId, String classname, HttpServletRequest request) {
		TbCategory tbCategory = tbCategoryService.queryById(targetId);
		tbCategory.setCategordId(targetId);
		tbCategory.setClassname(classname);
		try {
			tbCategoryService.updateCategory(tbCategory);
			MsgUtil m = new MsgUtil();
			m.setTitle("提示信息");
			m.setMsg("修改成功");
			return m;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgUtil m = new MsgUtil();
			m.setTitle("提示信息");
			m.setMsg("修改失败");
			return m;
		}
	}

	// 删除知识点
	@RequestMapping("/deleteTree")
	@ResponseBody
	public MsgUtil deleteCategorty(long targetId) {
		List<TbCategory> list = tbCategoryService.getChildList(targetId);
		for (int i = 0; i < list.size(); i++) {
			Long id = list.get(i).getCategordId();
			tbCategoryService.deleteCategory(id);
		}
		tbCategoryService.deleteCategory(targetId);
		MsgUtil m = new MsgUtil();
		m.setTitle("提示信息");
		m.setMsg("删除成功");
		return m;
	}
}