package com.dawn.service;

import java.util.List;

import com.dawn.dto.CompanyTatilDto;
import com.dawn.pojo.TbCompany;
import com.dawn.util.DawnResult;
import com.dawn.util.Result;

public interface TbCompanyTitleService {
	// public Result getCompanyTitle(int rows, int page, Long titleId);
	public Result getCompanyTitle(Long titleId);

	public List<TbCompany> getAllCompany();

	// 公司联想查询
	public List<TbCompany> getByName(String corporate, int usertype);

	public DawnResult updateAskNum(CompanyTatilDto companyTatilDto);

	public DawnResult addAskNum(CompanyTatilDto companyTatilDto);

}
