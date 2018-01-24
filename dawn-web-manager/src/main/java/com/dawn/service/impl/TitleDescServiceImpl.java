package com.dawn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.dto.TitleDescDto;
import com.dawn.dtomapper.TitleDescDtoMapper;
import com.dawn.service.TitleDescService;

@Service
public class TitleDescServiceImpl implements TitleDescService {
	@Autowired
	private TitleDescDtoMapper mapper;

	// 根据知识点查询答案

	public TitleDescDto selectByTbtitleId(long tbtitleId) {
		return mapper.selectByTbtitleId(tbtitleId);
	}

}
