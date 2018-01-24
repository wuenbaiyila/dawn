package com.dawn.service;

import com.dawn.pojo.ActiveUser;
import com.dawn.pojo.TbTitle;
import com.dawn.util.DawnResult;

public interface TbtitleHandleService {

	DawnResult saveTitle(TbTitle title, String titleDesc, Long companyId, ActiveUser activeUser) throws Exception;
}
