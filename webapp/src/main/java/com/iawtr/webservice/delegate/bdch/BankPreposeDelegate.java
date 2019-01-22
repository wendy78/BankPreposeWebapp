package com.iawtr.webservice.delegate.bdch;

import com.iawtr.web.dto.bdch.ApplicationContentRequestData;
import com.iawtr.web.dto.bdch.ApplicationContentResponse;
import com.iawtr.web.dto.bdch.ApplicationStatusRequestData;
import com.iawtr.web.dto.bdch.ApplicationStatusResponse;
import com.iawtr.web.dto.bdch.OwnershipInfoRequestData;
import com.iawtr.web.dto.bdch.OwnershipInfoResponse;
import com.iawtr.web.init.SpringFactoryHelp;
import com.iawtr.webservice.service.bdch.BankPrepose;

@javax.jws.WebService(targetNamespace = "http://bdch.delegate.webservice.iawtr.com/", serviceName = "BankPreposeService", portName = "BankPreposePort")
public class BankPreposeDelegate {
	/**
	 * 查证
	 * @param pOwnershipInfoRequest
	 * @return
	 * @throws Exception 
	 */
	public OwnershipInfoResponse OwnershipInfo(OwnershipInfoRequestData data) throws Exception{
		BankPrepose ss=(BankPrepose) SpringFactoryHelp.getApplicationContext().getBean("bankPrepose");
		return ss.OwnershipInfo(data);
	}

	/**
	 * 返回业务办理状态
	 * @param pApplicationStatusRequest
	 * @return
	 * @throws Exception 
	 */
	public ApplicationStatusResponse ApplicationStatus(ApplicationStatusRequestData data) throws Exception{
		BankPrepose ss=(BankPrepose) SpringFactoryHelp.getApplicationContext().getBean("bankPrepose");
		return ss.ApplicationStatus(data);
	}
	
	/**
	 * 传输抵押业务内容到不动产
	 * @param pApplicationContentRequest
	 * @return
	 * @throws Exception 
	 */
	public ApplicationContentResponse ApplicationContent(ApplicationContentRequestData data) throws Exception{
		BankPrepose ss=(BankPrepose) SpringFactoryHelp.getApplicationContext().getBean("bankPrepose");
		return ss.ApplicationContent(data);
	}
}
