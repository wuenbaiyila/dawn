package com.dawn.service;

import java.util.List;

import com.dawn.pojo.TbCompany;
import com.dawn.util.Result;
import com.dawn.util.TreePojo;

public interface TbCompanyService {
	// 查询公司
	public List<TreePojo> queryCompany(long parentid);

	// 查询子节点
	public List<TbCompany> getChildList(long parentid);

	// 新增公司
	public void saveCompany(TbCompany company);

	// 修改公司
	public void updateCompany(TbCompany company);

	// 按照Id查询
	public TbCompany queryById(long companyId);

	// 删除
	public void deleteCompany(long companyId);

	// 模糊查询公司
	public Result queryByName(int page, int rows, String corporate, int userType);

	/*
	 * //按照用户id查看 public List<TbCompany> getCompanyByAuditstatusList(long
	 * userid);
	 */

	public List<TbCompany> getCompanyByAuditstatusList(Integer userid);

	/**
	 * 查看一条数据
	 * 
	 * @param companyId
	 * @return
	 */

	TbCompany selectCompanyById(long companyId);

}
