package com.iawtr.service.bdch;

import com.iawtr.service.main.MainService;
import com.iawtr.web.dto.bdch.ApplicationStatusRequestData;
import com.iawtr.web.dto.bdch.ApplicationStatusResponse;

public interface ApplicationStatusRequestService extends MainService{
	
	public ApplicationStatusResponse GetResponse(ApplicationStatusRequestData data) throws Exception;
}
