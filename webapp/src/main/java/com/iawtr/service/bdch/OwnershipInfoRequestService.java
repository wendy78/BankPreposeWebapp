package com.iawtr.service.bdch;

import com.iawtr.service.main.MainService;
import com.iawtr.web.dto.bdch.OwnershipInfoRequestData;
import com.iawtr.web.dto.bdch.OwnershipInfoResponse;

public interface OwnershipInfoRequestService extends MainService{

	OwnershipInfoResponse GetResponse(OwnershipInfoRequestData data) throws Exception;
}
