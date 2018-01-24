package com.dawn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dawn.pojo.TbTitle;
import com.dawn.pojo.TbTitleExample;

public interface TbTitleMapper {
	int countByExample(TbTitleExample example);

	int deleteByExample(TbTitleExample example);

	int deleteByPrimaryKey(Long tbtitleId);

	int insert(TbTitle record);

	int insertSelective(TbTitle record);

	List<TbTitle> selectByExampleWithBLOBs(TbTitleExample example);

	List<TbTitle> selectByExample(TbTitleExample example);

	List<TbTitle> getByTitleNameList(@Param("title") String titlename, @Param("userType") Integer userType);

	TbTitle selectByPrimaryKey(Long tbtitleId);

	int updateByExampleSelective(@Param("record") TbTitle record, @Param("example") TbTitleExample example);

	int updateByExampleWithBLOBs(@Param("record") TbTitle record, @Param("example") TbTitleExample example);

	int updateByExample(@Param("record") TbTitle record, @Param("example") TbTitleExample example);

	int updateByPrimaryKeySelective(TbTitle record);

	int updateByPrimaryKeyWithBLOBs(TbTitle record);

	int updateByPrimaryKey(TbTitle record);
}