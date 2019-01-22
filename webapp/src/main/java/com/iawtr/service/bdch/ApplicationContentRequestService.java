package com.iawtr.service.bdch;

import com.iawtr.service.main.MainService;
import com.iawtr.web.dto.bdch.ApplicationContentRequestData;
import com.iawtr.web.dto.bdch.ApplicationContentResponse;

public interface ApplicationContentRequestService extends MainService{

	ApplicationContentResponse GetResponse(ApplicationContentRequestData data) throws Exception;
}
