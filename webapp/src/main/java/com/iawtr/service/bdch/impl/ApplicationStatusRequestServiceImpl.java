package com.iawtr.service.bdch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iawtr.database.dto.DjSjd;
import com.iawtr.database.dto.WfmActinst;
import com.iawtr.service.bdch.ApplicationStatusRequestService;
import com.iawtr.service.main.impl.MainServiceImpl;
import com.iawtr.web.dto.bdch.ApplicationStatusRequestData;
import com.iawtr.web.dto.bdch.ApplicationStatusResponse;
import com.iawtr.web.dto.bdch.ApplicationStatusResponseData;
import com.iawtr.web.dto.bdch.ApplicationStatusResponseInfo;



public class ApplicationStatusRequestServiceImpl extends MainServiceImpl implements ApplicationStatusRequestService{

	public ApplicationStatusResponse GetResponse(ApplicationStatusRequestData data) throws Exception{
		ApplicationStatusResponse pApplicationStatusResponse = new ApplicationStatusResponse();	//����ֵ
		if(data == null || data.getArrayList() == null || data.getArrayList().size() <=0){
			throw new Exception("传入数据为空");
		}
		
		ArrayList<ApplicationStatusResponseInfo> pApplicationStatusResponseInfoList = new ArrayList<ApplicationStatusResponseInfo>();
		try {
			for(int cnt =0;data.getArrayList() != null && cnt < data.getArrayList().size(); cnt++){
				pApplicationStatusResponseInfoList.addAll(GetRequestInfoSingle(data.getArrayList().get(cnt)));
			}
		
			ApplicationStatusResponseData rps = new ApplicationStatusResponseData();
			rps.setArrayList(pApplicationStatusResponseInfoList);
			rps.setLimit(pApplicationStatusResponseInfoList.size());
			rps.setTotalCount(pApplicationStatusResponseInfoList.size());
					
			pApplicationStatusResponse.setData(rps);
			pApplicationStatusResponse.setCode("0");
			pApplicationStatusResponse.setMsg("返回查询");
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			pApplicationStatusResponse.setCode("1");
			pApplicationStatusResponse.setMsg(e.getMessage());
		}
		
		return pApplicationStatusResponse;
	}
	
	private ArrayList<ApplicationStatusResponseInfo> GetRequestInfoSingle(String pApplicationStatusRequestInfo) throws Exception{
		if(pApplicationStatusRequestInfo == null || pApplicationStatusRequestInfo.isEmpty() || pApplicationStatusRequestInfo.trim().length() <=0){
			throw new Exception("没有查询条件");
		}
		
		ArrayList<ApplicationStatusResponseInfo> pApplicationStatusResponseInfoList = new ArrayList<ApplicationStatusResponseInfo>();
//		PageSearchEntity pe=new PageSearchEntity();
//		pe.constructSqlByArray("and", "Wwsqbh", "=", pApplicationStatusRequestInfo);
//		List<DjSjd> sjdlst=hibernateDao.findPageListByEntity(DjSjd.class, pe, 1, 0);
		
		String hql=" from DjSjd p where p.wwsqbh=:wwsqbh ";
		HashMap<String, Object> mapValues = new HashMap<String, Object>();
		mapValues.put("wwsqbh", pApplicationStatusRequestInfo);
		List<DjSjd> sjdlst =hibernateDao.findListByHql(hql,1,0, mapValues);
		
		ApplicationStatusResponseInfo pApplicationStatusResponseInfo = null;
		List<WfmActinst> wfmlst =null;
		for(int sjdcnt=0; sjdlst != null && sjdcnt<sjdlst.size(); sjdcnt++){
			pApplicationStatusResponseInfo = new ApplicationStatusResponseInfo();
			pApplicationStatusResponseInfo.setUid(pApplicationStatusRequestInfo);
			
//			pe=new PageSearchEntity();
//			pe.constructSqlByArray("and", "Prjid", "=", sjdlst.get(sjdcnt).getSlbh());
//			pe.setOrderSql("order by Submittime desc");
//			wfmlst=hibernateDao.findPageListByEntity(WfmActinst.class, pe, 1, 0);
			
			hql=" from WfmActinst p where p.prjid=:slbh order by p.submittime desc";
			mapValues = new HashMap<String, Object>();
			mapValues.put("slbh", sjdlst.get(sjdcnt).getSlbh());
			wfmlst =hibernateDao.findListByHql(hql,1,0, mapValues);
			if (wfmlst != null){
				pApplicationStatusResponseInfo.setStatus(wfmlst.get(0).getStepname() + "-" + wfmlst.get(0).getStepstate());
				if(wfmlst.get(0).getCompletetime() != null){
					pApplicationStatusResponseInfo.setDtime(wfmlst.get(0).getCompletetime());
				}else{
					if(wfmlst.get(0).getSavetime() != null){
						pApplicationStatusResponseInfo.setDtime(wfmlst.get(0).getSavetime());
					}else{
						if(wfmlst.get(0).getAccepttime() != null){
							pApplicationStatusResponseInfo.setDtime(wfmlst.get(0).getAccepttime());
						}else{
							pApplicationStatusResponseInfo.setDtime(wfmlst.get(0).getSubmittime());
						}
					}
					
				}
			}else{
				pApplicationStatusResponseInfo.setStatus("已提交(未受理)");
				pApplicationStatusResponseInfo.setDtime(new java.util.Date());
			}
			
			pApplicationStatusResponseInfoList.add(pApplicationStatusResponseInfo);
		}
		
		if(sjdlst == null || sjdlst.size() <=0){
			pApplicationStatusResponseInfo = new ApplicationStatusResponseInfo();
			pApplicationStatusResponseInfo.setUid(pApplicationStatusRequestInfo);
			pApplicationStatusResponseInfo.setStatus("已提交(未受理)");
			pApplicationStatusResponseInfo.setDtime(new java.util.Date());

			//Status = "未开始流程",
			pApplicationStatusResponseInfoList.add(pApplicationStatusResponseInfo);
		}
		
		return pApplicationStatusResponseInfoList;
	}
}
