package com.dawn.service.impl;

import com.dawn.mapper.TbDescMapper;
import com.dawn.mapper.TbTitleCustomMapper;
import com.dawn.mapper.TbTitleMapper;
import com.dawn.pojo.*;
import com.dawn.pojo.TbTitleExample.Criteria;
import com.dawn.service.TbTitleService;
import com.dawn.util.DawnResult;
import com.dawn.util.IDUtils;
import com.dawn.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TbTitleServiceImpl implements TbTitleService {
	@Autowired
	private TbTitleMapper titleMapper;
	@Autowired
	private TbDescMapper descMapper;
	@Autowired
	private TbTitleCustomMapper customMapper;

	/**
	 * 
	 * <p>
	 * Title: getTitleList
	 * </p>
	 * <p>
	 * Description: 查询题目列表
	 * </p>
	 * 
	 * @param page
	 * @param rows
	 * @return
	 //* @see TbTitleService#getTitleList(int, int)
	 */
	// 根据题目类型获取题目列表
	public Result getTitleLists(Long categoryId, int page, int rows) {
		TbTitleExample example = new TbTitleExample();
		Criteria criteria = example.createCriteria();
		example.setOrderByClause("clicknum DESC");
		criteria.andCategoryIdEqualTo(categoryId);
		criteria.andAuditStatusEqualTo("2");
		PageHelper.startPage(page, rows);
		List<TbTitle> list = titleMapper.selectByExample(example);
		PageInfo<TbTitle> pageInfo = new PageInfo(list);
		Result result = new Result();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

// 根据题目类型获取题目列表
	public List<TbTitle> getTitleList(Long categoryId) {
		TbTitleExample example = new TbTitleExample();
		Criteria criteria = example.createCriteria();
		example.setOrderByClause("clicknum DESC");
		criteria.andCategoryIdEqualTo(categoryId);
		criteria.andAuditStatusEqualTo("2");
		List<TbTitle> list = titleMapper.selectByExample(example);
//		Result result = new Result();
//		result.setRows(list);
//		result.setTotal(pageInfo.getTotal());
		System.out.println(list.get(0).getCreated()+""+list.get(0).getUpdated() );
		return list;
	}


	// 删除题目
	public DawnResult delTitle(long tbtitleId) {
		titleMapper.deleteByPrimaryKey(tbtitleId);
		return DawnResult.ok();
	}

	// 删除答案

	public DawnResult delDesc(long titleId) {
		TbDescExample example = new TbDescExample();
		TbDescExample.Criteria criteria = example.createCriteria();
		criteria.andTitleIdEqualTo(titleId);
		descMapper.deleteByExample(example);
		return DawnResult.ok();
	}

	/**
	 * 
	 * <p>
	 * Title: saveTitle
	 * </p>
	 * <p>
	 * Description: 添加题目
	 * </p>
	 * 
	 * @param title
	 * @return
	 * @throws Exception
	// * @see TbTitleService#saveTitle(TbTitle)
	 */

	public DawnResult saveTitle(TbTitle title, String titleDesc, Integer userType, Integer userId) throws Exception {
		long titleId = IDUtils.genTitleId();
		Date date = new Date();
		title.setTbtitleId(titleId);// 题目id
		title.setCreated(date);
		title.setUpdated(date);
		title.setClicknum((long) 0);
		// 新增列 审核状态为 0 未审核
		title.setAuditStatus("0");
		// 新增列 用户所属部门
		title.setUserType(userType);
		// 新增列，用户ID
		title.setUserid(userId);
		titleMapper.insertSelective(title);
		DawnResult result = saveDesc(titleId, titleDesc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return DawnResult.ok();
	}

	// 修改题目

	public DawnResult updateTitle(TbTitle title, String titleDesc) throws Exception {
		Date date = new Date();
		title.setUpdated(date);
		title.setAuditStatus("0");
		titleMapper.updateByPrimaryKeySelective(title);// 修改指定的字段
		DawnResult result = updateDesc(title.getTbtitleId(), titleDesc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return DawnResult.ok();
	}

	// 新增答案
	private DawnResult saveDesc(Long titleId, String titleDesc) {
		TbDesc tbdesc = new TbDesc();
		Date date = new Date();
		tbdesc.setCreated(date);
		tbdesc.setTitleDesc(titleDesc);
		tbdesc.setUpdated(date);
		tbdesc.setTitleId(titleId);
		descMapper.insert(tbdesc);
		return DawnResult.ok();
	}

	// 修改答案
	private DawnResult updateDesc(Long titleId, String titleDesc) {
		TbDescExample example = new TbDescExample();
		Date date = new Date();
		TbDescExample.Criteria criteria = example.createCriteria();
		criteria.andTitleIdEqualTo(titleId);
		List<TbDesc> list = descMapper.selectByExampleWithBLOBs(example);
		TbDesc desc = list.get(0);
		desc.setTitleDesc(titleDesc);
		desc.setUpdated(date);
		descMapper.updateByPrimaryKeyWithBLOBs(desc);
		return DawnResult.ok();
	}

	// 按照题目查询

	public Result getByName(String title, int page, int rows, int userType) {
		List<TbTitle> list = titleMapper.getByTitleNameList(title, userType);
		PageInfo<TbTitle> info = new PageInfo(list);
		Result result = new Result();
		result.setRows(list);
		result.setTotal(info.getTotal());
		return result;
	}

	/**
	 * 审核机制 根据用户id 查询题目的审核状态
	 */

	public List<TbTitle> getTitleAuditList(int userid) {
		TbTitleExample example = new TbTitleExample();
		Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andAuditStatusNotEqualTo("2");
		List<TbTitle> list = titleMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据题目id获得题目名称
	 */

	public String selectTitleById(Long tbtitleId) {
		return titleMapper.selectByPrimaryKey(tbtitleId).getTitle();
	}

	// 查询待审核知识点

	public List<TbTitleCustom> getByAuditStatu(int id) {
		List<TbTitleCustom> list = customMapper.getByAuditStatuAndUserId(id);
		return list;
	}

	// 通过，根据题目id修改审核状态码，并刷新当前页面。
	public void updateStatu(long id) {
		TbTitle title = titleMapper.selectByPrimaryKey(id);
		title.setAuditStatus("2");
		titleMapper.updateByPrimaryKey(title);
	}

	// 保存未通过原因字段

	public void updateTbtitle(long tbtitleId, String cause) {
		TbTitle title = titleMapper.selectByPrimaryKey(tbtitleId);
		if (cause.equals("2")) {
			title.setCause("知识点描述不清楚！");
		}
		if (cause.equals("3")) {
			title.setCause("书写不符合规范！");
		}
		title.setAuditStatus("1");
		titleMapper.updateByPrimaryKey(title);
	}

	// 根据题目id查询题目信息
	public TbTitle queryByTitleId(Long id) {
		return titleMapper.selectByPrimaryKey(id);
	}

}
