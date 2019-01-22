package com.iawtr.webservice.service.bdch;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.iawtr.service.bdch.ApplicationContentRequestService;
import com.iawtr.service.bdch.ApplicationStatusRequestService;
import com.iawtr.service.bdch.OwnershipInfoRequestService;
import com.iawtr.service.bdch.impl.ApplicationContentRequestServiceImpl;
import com.iawtr.service.bdch.impl.ApplicationStatusRequestServiceImpl;
import com.iawtr.service.bdch.impl.OwnershipInfoRequestServiceImpl;
import com.iawtr.web.dto.bdch.ApplicationContentRequestData;
import com.iawtr.web.dto.bdch.ApplicationContentResponse;
import com.iawtr.web.dto.bdch.ApplicationStatusRequestData;
import com.iawtr.web.dto.bdch.ApplicationStatusResponse;
import com.iawtr.web.dto.bdch.OwnershipInfoRequestData;
import com.iawtr.web.dto.bdch.OwnershipInfoResponse;
import com.iawtr.webservice.service.WSMain;

public class BankPrepose extends WSMain{
	private ApplicationContentRequestService applicationContentRequestService;
	private ApplicationStatusRequestService applicationStatusRequestService;
	private OwnershipInfoRequestService ownershipInfoRequestService;


	public ApplicationContentRequestService getApplicationContentRequestService() {
		return applicationContentRequestService;
	}

	public void setApplicationContentRequestService(
			ApplicationContentRequestService applicationContentRequestService) {
		this.applicationContentRequestService = applicationContentRequestService;
	}

	public ApplicationStatusRequestService getApplicationStatusRequestService() {
		return applicationStatusRequestService;
	}

	public void setApplicationStatusRequestService(
			ApplicationStatusRequestService applicationStatusRequestService) {
		this.applicationStatusRequestService = applicationStatusRequestService;
	}

	public OwnershipInfoRequestService getOwnershipInfoRequestService() {
		return ownershipInfoRequestService;
	}

	public void setOwnershipInfoRequestService(
			OwnershipInfoRequestService ownershipInfoRequestService) {
		this.ownershipInfoRequestService = ownershipInfoRequestService;
	}

	public OwnershipInfoResponse OwnershipInfo(OwnershipInfoRequestData data) throws Exception{
		OwnershipInfoResponse rlt= ownershipInfoRequestService.GetResponse(data);
		return rlt;
	}
	
	public ApplicationStatusResponse ApplicationStatus(ApplicationStatusRequestData data) throws Exception{
		ApplicationStatusResponse rlt= applicationStatusRequestService.GetResponse(data);
		return rlt;
	}
	
	public ApplicationContentResponse ApplicationContent(ApplicationContentRequestData data) throws Exception{
		ApplicationContentResponse rlt= applicationContentRequestService.GetResponse(data);
		return rlt;
	}

	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws RemoteException 
	 * @throws Exception 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
