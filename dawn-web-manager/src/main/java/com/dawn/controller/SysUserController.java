package com.dawn.controller;

import com.dawn.pojo.ActiveUser;
import com.dawn.pojo.SysUser;
import com.dawn.service.SysUserService;
import com.dawn.util.DawnResult;
import com.dawn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SysUserController {
	@Autowired
	private SysUserService userService;

	/**
	 * 登录
	 * <p>
	 * Title: login
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 

	 * @param password
	 * @return
	 */
	@RequestMapping("/account/login")
	@ResponseBody
	public DawnResult login(String username,String password, HttpSession session) {

			// 调用service方法
			DawnResult result = userService.authenticat(username, password);
			// 返回的数据 400 的话,返回到error页面 用户提示信息
			if (result.getStatus() == 400) {

				return result;
			}
			//获取activeUser
			ActiveUser activeUser = (ActiveUser) result.getData();
			// 保存到session中去.
			session.setAttribute("activeUser", activeUser);
			return result;
		}



	/**
	 * 查询用户
	 * <p>
	 * Title: findSysRoleAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping("/findUserAll")
	@ResponseBody
	public Result findSysRoleAll(int page, int rows) {
		return userService.findSysRolefindAll(page, rows);
	}

	/**
	 * 删除用户
	 * <p>
	 * Title: deleteSysRole
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 //r* @param id
	 * @return
	 */
	@RequestMapping("/deleteSysUser")
	@ResponseBody
	public Map deleteSysRole(String delIds) {
		String[] strid = delIds.split(",");
		Map map = new HashMap ();
		try {
			for (int i = 0; i < strid.length; i++) {
				userService.deleteSysRole(Integer.valueOf(strid[i]));
			}
			map.put("delNums", strid.length);// 删除条数
			map.put("success", "删除成功");
			return map;

		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorMsg", "删除失败!!");
			return map;
		}
	}

	/*
	 * 添加用户 <p>Title: addSysRole</p> <p>Description: </p>
	 * 
	 * @param user
	 * 
	 * @return
	 */
	@RequestMapping("/addSysUser")
	@ResponseBody
	public Map addSysRole(SysUser user, int roleId) {
		Map map = new HashMap ();
		try {
			user.setParentId(1);// 都归超级用户管理
			userService.addSysUser(user, roleId);
			map.put("success", "保存成功!!!");
			return map;
		} catch (Exception e) {
			map.put("errorMsg", "保存失败");
			e.printStackTrace();
			return map;
		}

	}

	/**
	 * Id查询
	 * <p>
	 * Title: findByIdSysRole
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("findByIdSysUser")
	@ResponseBody
	public SysUser findByIdSysRole(Integer id) {
		SysUser sys = userService.findByIdSysUser(id);
		return sys;
	}

	/**
	 * 修改用户
	 * <p>
	 * Title: updateSysRole
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateSysUser")
	@ResponseBody
	public String updateSysRole(SysUser user) {
		int i = userService.upadteSysUser(user);
		String msg = "";
		if (i > 0) {
			msg = "{\"msg\":\"修改成功\"}";
		} else {
			msg = "{\"msg\":\"修改失败\"}";
		}
		return msg;
	}

	// 根据parentid查询本班学生
	@RequestMapping("/sysUser/queryByParentId")
	@ResponseBody
	public Result queryByParentId(@RequestParam(value = "parentid") Integer parentid, int page, int rows) {
		Result result = userService.findSysByParentId(parentid, page, rows);
		return result;
	}

	// 老师添加学生
	@RequestMapping("/teacher/saveUser")
	@ResponseBody
	public Map saveUser(SysUser user, HttpServletRequest request) {
		Map map = new HashMap ();
		ActiveUser activeUser = (ActiveUser) request.getSession().getAttribute("activeUser");
		int userId = activeUser.getUserid();// 用户的id
		int userType = activeUser.getUser_type();// 用户类型
		user.setParentId(userId);
		user.setUserType(userType);
		user.setLocked("1");
		try {
			userService.addSysUser(user, 3);// 3是学生角色
			map.put("success", "保存成功!!!");
			return map;
		} catch (Exception e) {
			map.put("errorMsg", "保存失败");
			e.printStackTrace();
			return map;
		}
	}

}
