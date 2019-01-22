package com.iawtr.service.bdch.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.iawtr.commons.util.HttpHelper;
import com.iawtr.commons.util.PersonIdVerity;
import com.iawtr.commons.util.TimeHelper;
import com.iawtr.database.dto.DjDjb;
import com.iawtr.database.dto.DjQlr;
import com.iawtr.database.dto.DjTsgl;
import com.iawtr.database.dto.DjYg;
import com.iawtr.database.dto.FcHQsdc;
import com.iawtr.database.dto.WwDjsqTmp;
import com.iawtr.database.dto.WwDysqTmp;
import com.iawtr.database.dto.WwFjqdTmp;
import com.iawtr.database.dto.WwSqrxxTmp;
import com.iawtr.database.dto.WwTsglTmp;
import com.iawtr.service.bdch.ApplicationContentRequestService;
import com.iawtr.service.main.impl.MainServiceImpl;
import com.iawtr.web.dto.bdch.ApplicationContentRequestData;
import com.iawtr.web.dto.bdch.ApplicationContentRequestInfo;
import com.iawtr.web.dto.bdch.ApplicationContentResponse;
import com.iawtr.web.dto.bdch.ApplicationContentResponseData;
import com.iawtr.web.dto.bdch.ApplicationContentResponseInfo;
import com.iawtr.web.dto.bdch.Fj;
import com.iawtr.web.dto.bdch.Xgrxx;
import com.iawtr.web.enumerate.SystemConstants;

public class ApplicationContentRequestServiceImpl  extends MainServiceImpl implements ApplicationContentRequestService{

	public ApplicationContentResponse GetResponse(ApplicationContentRequestData data) throws Exception{
		ApplicationContentResponse pApplicationContentResponse = new ApplicationContentResponse();		//返回值
		if(data == null || data.getArrayList() == null || data.getArrayList().size() <=0){
			throw new Exception("传入数据为空");
		}
		
		ArrayList<ApplicationContentResponseInfo> pApplicationContentResponseInfoList = new ArrayList<ApplicationContentResponseInfo>();
		for(int cnt = 0; cnt<data.getArrayList().size();cnt++){
			pApplicationContentResponseInfoList.add(GetRequestInfoSingle(data.getArrayList().get(cnt)));
		}
		
		ApplicationContentResponseData rps = new ApplicationContentResponseData();
		rps.setArrayList(pApplicationContentResponseInfoList);
		rps.setLimit(pApplicationContentResponseInfoList.size());
		rps.setTotalCount(pApplicationContentResponseInfoList.size());

		pApplicationContentResponse.setData(rps);
		pApplicationContentResponse.setCode("0");
		pApplicationContentResponse.setMsg("返回查询");
					
		return pApplicationContentResponse;
	}
	
	private ApplicationContentResponseInfo GetRequestInfoSingle(ApplicationContentRequestInfo pApplicationContentRequestInfo){
		String errString = CheckInfoSingle(pApplicationContentRequestInfo);
		ApplicationContentResponseInfo pApplicationContentResponseInfo = new ApplicationContentResponseInfo();
		if (errString == null || errString.isEmpty() || errString.length() <=0){
			try{
				//--登记表
				String hql=" from DjDjb p where p.lifecycle = 0 and p.slbh=:slbh ";
				HashMap<String, Object> mapValues = new HashMap<String, Object>();
				mapValues.put("slbh", pApplicationContentRequestInfo.getQzslbh());
				List<DjDjb> pDjDjbList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				//--预告
				hql=" from DjYg p where p.lifecycle = 0 and p.slbh=:slbh ";
				List<DjYg> pDjYgList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				//--权利人、权利人管理
				hql=" from DjQlr p,DjQlrGl gl where p.qlrid=gl.qlrid and gl.slbh=:slbh and (gl.qlrlx='权利人' or gl.qlrlx is null )";
				List<Object[]> pDjqlrList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				//--
				hql=" from FcHQsdc p,DjTsgl hgl where p.tstybm=hgl.tstybm and p.lifecycle = 0 and hgl.slbh=:slbh ";
				List<Object[]> pHList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				if (pDjDjbList.size() == 0 && pDjYgList.size() == 0){
					throw new Exception("没有找到相关权证");
				}
				if (pHList.size() == 0){
					throw new Exception("户信息缺失，请到不动产部门核实");
				}

				//登记申请
				WwDjsqTmp pWwDjsqTmpOld =hibernateDao.get(WwDjsqTmp.class, pApplicationContentRequestInfo.getUid());
				String xgzh ="";
				for(int cnt=0; pDjDjbList != null && cnt<pDjDjbList.size(); cnt++){
					if(cnt+1==pDjDjbList.size()){
						xgzh = xgzh+pDjDjbList.get(cnt).getBdczh();
					}else{
						xgzh = xgzh+pDjDjbList.get(cnt).getBdczh()+",";
					}
				}
				for(int cnt=0; pDjYgList != null && cnt<pDjYgList.size(); cnt++){
					if(cnt+1==pDjYgList.size()){
						xgzh = xgzh+pDjYgList.get(cnt).getBdczmh();
					}else{
						xgzh = xgzh+pDjYgList.get(cnt).getBdczmh()+",";
					}
				}
				WwDjsqTmp pWwDjsqTmp = new WwDjsqTmp();
				pWwDjsqTmp.setSqbh(pApplicationContentRequestInfo.getUid());
				pWwDjsqTmp.setSqrq(pApplicationContentRequestInfo.getSqsj());
				pWwDjsqTmp.setDjdl("");
				pWwDjsqTmp.setDjxl("抵押登记");
				pWwDjsqTmp.setSpyj("外网预审通过");
				pWwDjsqTmp.setSpr(pApplicationContentRequestInfo.getSlry());
				pWwDjsqTmp.setSpzt("通过");
				pWwDjsqTmp.setSprq(pApplicationContentRequestInfo.getSqsj());
				pWwDjsqTmp.setAjzt("");
				pWwDjsqTmp.setBz(pApplicationContentRequestInfo.getTzbz());
				pWwDjsqTmp.setSqr(pApplicationContentRequestInfo.getTzr());
				pWwDjsqTmp.setBlwd("办理网点");
				pWwDjsqTmp.setHtbh(pApplicationContentRequestInfo.getHtbh());
				pWwDjsqTmp.setXgzh(xgzh);
				pWwDjsqTmp.setSfzsbd("是");
				pWwDjsqTmp.setSfzsyx("是");
				String zjhm = "";
				DjQlr qlr = null;
				for(int cnt=0; pDjqlrList != null && cnt<pDjqlrList.size(); cnt++){
					qlr = (DjQlr)pDjqlrList.get(cnt)[0];
					
					if(cnt+1==pDjqlrList.size()){
						zjhm = zjhm+qlr.getZjhm();
					}else{
						zjhm = zjhm+qlr.getZjhm()+",";
					}
				}
				pWwDjsqTmp.setSqrsfzh(zjhm);
				pWwDjsqTmp.setSqrdhhm(pApplicationContentRequestInfo.getTzdh());
				for(int nmcnt=0;pApplicationContentRequestInfo.getDyqr() != null && nmcnt<pApplicationContentRequestInfo.getDyqr().size();nmcnt++){
					if(pApplicationContentRequestInfo.getDyqr().get(nmcnt).getName() != null && pApplicationContentRequestInfo.getDyqr().get(nmcnt).getName().equals("银行")){
						pWwDjsqTmp.setDklx("商业贷款");
					}else{
						pWwDjsqTmp.setDklx("公积金贷款");
					}
				}
				pWwDjsqTmp.setYwmx("");
				
				//抵押申请
				WwDysqTmp pWwDysqTmpOld = hibernateDao.get(WwDysqTmp.class, pApplicationContentRequestInfo.getUid());
				int pDysw;
				try{
					pDysw = Integer.parseInt(pApplicationContentRequestInfo.getDysw());
				}catch (Exception e){
					pDysw = 1;
				}
				WwDysqTmp pWwDysqTmp = new WwDysqTmp();
				pWwDysqTmp.setSqbh(pApplicationContentRequestInfo.getUid());
				pWwDysqTmp.setXgzh(xgzh);
				pWwDysqTmp.setDylx(pApplicationContentRequestInfo.getDylx());	//todo:抵押类型
				pWwDysqTmp.setDysw(new BigDecimal(pDysw));
				String zwr="",zwrzjlx="",zjh="";
				for(int cnt=0; pApplicationContentRequestInfo.getJkr() != null && cnt<pApplicationContentRequestInfo.getJkr().size(); cnt++){
					if(cnt+1==pApplicationContentRequestInfo.getJkr().size()){
						zwr = zwr+pApplicationContentRequestInfo.getJkr().get(cnt).getName();
						zwrzjlx = zwrzjlx+pApplicationContentRequestInfo.getJkr().get(cnt).getZjlx();
						zjh= zjh+pApplicationContentRequestInfo.getJkr().get(cnt).getZjh();
					}else{
						zwr = zwr+pApplicationContentRequestInfo.getJkr().get(cnt).getName()+",";
						zwrzjlx = zwrzjlx+pApplicationContentRequestInfo.getJkr().get(cnt).getZjlx()+",";
						zjh= zjh+pApplicationContentRequestInfo.getJkr().get(cnt).getZjh()+",";
					}
				}
				pWwDysqTmp.setZwr(zwr);
				pWwDysqTmp.setZwrzjlx(zwrzjlx);
				pWwDysqTmp.setZwrzjh(zjh);
				pWwDysqTmp.setDyfs(pApplicationContentRequestInfo.getDyfs());
				pWwDysqTmp.setDymj(BigDecimal.valueOf(pApplicationContentRequestInfo.getDymj()));	//to1do:增加抵押面积
				pWwDysqTmp.setZjjzwzl(pApplicationContentRequestInfo.getZjjzwzl());	//to1do:在建建筑物坐落
				pWwDysqTmp.setZjjzwdyfw(pApplicationContentRequestInfo.getZjjzwdyfw());	//to1do:在建建筑物抵押范围
				pWwDysqTmp.setBdbzzqse(BigDecimal.valueOf(pApplicationContentRequestInfo.getDyje()));
				pWwDysqTmp.setPgje(BigDecimal.valueOf(pApplicationContentRequestInfo.getPgje()));	//todo:不动产评估价格/金额
				pWwDysqTmp.setDbfw(pApplicationContentRequestInfo.getDbfw());	//to1do:担保范围
				pWwDysqTmp.setZgzqqdss("");	//to1do:最高债权确定事实
				pWwDysqTmp.setZgzqse(new BigDecimal(0));	//to1do:最高债权数额
				pWwDysqTmp.setDyqx(TimeHelper.getDifferenceDay(pApplicationContentRequestInfo.getEtime().toString(), pApplicationContentRequestInfo.getStime().toString()));	//todo: 时间差
				pWwDysqTmp.setQlqssj(pApplicationContentRequestInfo.getStime());
				pWwDysqTmp.setQljssj(pApplicationContentRequestInfo.getEtime());
				String bdcdyh="";
				FcHQsdc dc = null;
				for(int cnt=0; pHList != null && cnt<pHList.size(); cnt++){
					dc = (FcHQsdc)pHList.get(cnt)[0];
					if(cnt+1==pHList.size()){
						bdcdyh = bdcdyh+dc.getBdcdyh();
					}else{
						bdcdyh = bdcdyh+dc.getBdcdyh()+",";
					}
				}
				pWwDysqTmp.setBdcdyh(bdcdyh);
				
				//相关人员
				WwSqrxxTmp pWwSqrxxTmpOldList = hibernateDao.get(WwSqrxxTmp.class, pApplicationContentRequestInfo.getUid());
				int xgrCount = 0;
				List<WwSqrxxTmp> pWwSqrxxTmpList = new ArrayList<WwSqrxxTmp>();
				WwSqrxxTmp pWwSqrxxTmp = null;
				Xgrxx pR= null;
				for(int cnt=0;pApplicationContentRequestInfo.getDyqr() !=null && cnt<pApplicationContentRequestInfo.getDyqr().size();cnt++){
					pWwSqrxxTmp = new WwSqrxxTmp();
					pR= pApplicationContentRequestInfo.getDyqr().get(cnt);
					
					pWwSqrxxTmp.setGlbh(pApplicationContentRequestInfo.getUid() + "-" + ++xgrCount);
					pWwSqrxxTmp.setSqbh(pApplicationContentRequestInfo.getUid());
					pWwSqrxxTmp.setSqrmc(pR.getName());
					pWwSqrxxTmp.setZjlb(pR.getZjlx());
					pWwSqrxxTmp.setZjhm(pR.getZjh());
					pWwSqrxxTmp.setDh(pR.getTel());
					pWwSqrxxTmp.setSqrlx("抵押权人");
					pWwSqrxxTmp.setSxh(new BigDecimal(pR.getXh()));
					
					pWwSqrxxTmpList.add(pWwSqrxxTmp);
				}
				for(int cnt=0;pDjqlrList!=null && cnt<pDjqlrList.size();cnt++){
					pWwSqrxxTmp = new WwSqrxxTmp();
					qlr = (DjQlr)pDjqlrList.get(cnt)[1];
					
					pWwSqrxxTmp.setGlbh(pApplicationContentRequestInfo.getUid() + "-" + ++xgrCount);
					pWwSqrxxTmp.setSqbh(pApplicationContentRequestInfo.getUid());
					pWwSqrxxTmp.setSqrmc(qlr.getQlrmc());
					pWwSqrxxTmp.setZjlb(qlr.getZjlb());
					pWwSqrxxTmp.setZjhm(qlr.getZjhm());
					pWwSqrxxTmp.setDh(qlr.getDh());
					pWwSqrxxTmp.setSqrlx("抵押人");
					pWwSqrxxTmp.setSxh(null);
					
					pWwSqrxxTmpList.add(pWwSqrxxTmp);
				}
				for(int cnt=0;pApplicationContentRequestInfo.getJkr() !=null && cnt<pApplicationContentRequestInfo.getJkr().size();cnt++){
					pWwSqrxxTmp = new WwSqrxxTmp();
					pR= pApplicationContentRequestInfo.getJkr().get(cnt);
					
					pWwSqrxxTmp.setGlbh(pApplicationContentRequestInfo.getUid() + "-" + ++xgrCount);
					pWwSqrxxTmp.setSqbh(pApplicationContentRequestInfo.getUid());
					pWwSqrxxTmp.setSqrmc(pR.getName());
					pWwSqrxxTmp.setZjlb(pR.getZjlx());
					pWwSqrxxTmp.setZjhm(pR.getZjh());
					pWwSqrxxTmp.setDh(pR.getTel());
					pWwSqrxxTmp.setSqrlx("借款人");
					pWwSqrxxTmp.setSxh(new BigDecimal(pR.getXh()));
					
					pWwSqrxxTmpList.add(pWwSqrxxTmp);
				}
				
				if (pApplicationContentRequestInfo.getDyqrdlr() != null && !pApplicationContentRequestInfo.getDyqrdlr().isEmpty()){
					for(int cnt=0;pApplicationContentRequestInfo.getDyqrdlr() !=null && cnt<pApplicationContentRequestInfo.getDyqrdlr().size();cnt++){
						pWwSqrxxTmp = new WwSqrxxTmp();
						pR= pApplicationContentRequestInfo.getDyqrdlr().get(cnt);
						
						pWwSqrxxTmp.setGlbh(pApplicationContentRequestInfo.getUid() + "-" + ++xgrCount);
						pWwSqrxxTmp.setSqbh(pApplicationContentRequestInfo.getUid());
						pWwSqrxxTmp.setSqrmc(pR.getName());
						pWwSqrxxTmp.setZjlb(pR.getZjlx());
						pWwSqrxxTmp.setZjhm(pR.getZjh());
						pWwSqrxxTmp.setDh(pR.getTel());
						pWwSqrxxTmp.setSqrlx("抵押权人代理人");
						pWwSqrxxTmp.setSxh(new BigDecimal(pR.getXh()));
						
						pWwSqrxxTmpList.add(pWwSqrxxTmp);
					}
				}
				
				if (pApplicationContentRequestInfo.getDyrdlr() != null && !pApplicationContentRequestInfo.getDyrdlr().isEmpty()){
					for(int cnt=0;pApplicationContentRequestInfo.getDyrdlr() !=null && cnt<pApplicationContentRequestInfo.getDyrdlr().size();cnt++){
						pWwSqrxxTmp = new WwSqrxxTmp();
						pR= pApplicationContentRequestInfo.getDyrdlr().get(cnt);
						
						pWwSqrxxTmp.setGlbh(pApplicationContentRequestInfo.getUid() + "-" + ++xgrCount);
						pWwSqrxxTmp.setSqbh(pApplicationContentRequestInfo.getUid());
						pWwSqrxxTmp.setSqrmc(pR.getName());
						pWwSqrxxTmp.setZjlb(pR.getZjlx());
						pWwSqrxxTmp.setZjhm(pR.getZjh());
						pWwSqrxxTmp.setDh(pR.getTel());
						pWwSqrxxTmp.setSqrlx("抵押人代理人");
						pWwSqrxxTmp.setSxh(new BigDecimal(pR.getXh()));
						
						pWwSqrxxTmpList.add(pWwSqrxxTmp);
					}
				}
				
				//户信息
				hql = " from WwTsglTmp p where p.Sqbh=:sqbh ";
				mapValues = new HashMap<String, Object>();
				mapValues.put("sqbh",pApplicationContentRequestInfo.getUid());
				List<WwTsglTmp> pWwTsglTmpOldList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				hql = " from DjTsgl hgl where hgl.Slbh=:slbh ";
				mapValues = new HashMap<String, Object>();
				mapValues.put("slbh",pApplicationContentRequestInfo.getQzslbh());
				List<DjTsgl> pHglList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				int hCount = 0;
				WwTsglTmp pWwTsglTmp = null;
				List<WwTsglTmp> pWwTsglTmpList = new ArrayList<WwTsglTmp>();
				for(int cnt=0;pHglList != null && cnt<pHglList.size();cnt++){
					pWwTsglTmp = new WwTsglTmp();
					
					pWwTsglTmp.setGlbm(pApplicationContentRequestInfo.getUid() + "-" + ++hCount);
					pWwTsglTmp.setSqbh(pApplicationContentRequestInfo.getUid());
					pWwTsglTmp.setBdclx(pHglList.get(cnt).getBdclx());
					pWwTsglTmp.setTstybm(pHglList.get(cnt).getTstybm());
					pWwTsglTmp.setBdcdyh(pHglList.get(cnt).getBdcdyh());
					pWwTsglTmp.setDjzl("抵押");
					pWwTsglTmp.setCssj(new Date());
					
					pWwTsglTmpList.add(pWwTsglTmp);
				}

				//附件
				int fjCount = 0;
				hql = " from WwFjqdTmp p where p.Sqbh=:sqbh ";
				mapValues = new HashMap<String, Object>();
				mapValues.put("sqbh",pApplicationContentRequestInfo.getUid());
				List<WwFjqdTmp> pWwFjqdTmpOldList =hibernateDao.findListByHql(hql,1,0, mapValues);
				
				Fj pFj=null;
				WwFjqdTmp pWwFjqdTmp = null;
				List<WwFjqdTmp> pWwFjqdTmpList = new ArrayList<WwFjqdTmp>();
				for(int cnt=0;pApplicationContentRequestInfo.getLinks() != null && cnt<pApplicationContentRequestInfo.getLinks().size(); cnt++){
					pWwFjqdTmp = new WwFjqdTmp();
					pFj = pApplicationContentRequestInfo.getLinks().get(cnt);
					
					pWwFjqdTmp.setQdid(pApplicationContentRequestInfo.getUid() + "-" + ++fjCount);
					pWwFjqdTmp.setSqbh(pApplicationContentRequestInfo.getUid());
					pWwFjqdTmp.setXh((new Integer(fjCount)).toString());
					pWwFjqdTmp.setFjmc(pFj.getName());
					pWwFjqdTmp.setFjlx(pFj.getName());
					pWwFjqdTmp.setFtplj(SystemConstants.PreRemote+"//"+pApplicationContentRequestInfo.getUid()+"//"+pFj.getUrl());
//					pWwFjqdTmp.setFtplj($"/{Paramers.PreRemote}/{pApplicationContentRequestInfo.Uid}/{pFj.Url}");	//todo：修改路径 指向实际位置
					pWwFjqdTmp.setFjdx(null);
					pWwFjqdTmp.setFjzt(null);
					pWwFjqdTmp.setFjtjsj(null);
					pWwFjqdTmp.setFjkzm(null);
					pWwFjqdTmp.setFjml(null);
					pWwFjqdTmp.setFjzdpzml(null);
					
					pWwFjqdTmpList.add(pWwFjqdTmp);
				}

				//保存 todo:附件 使用异步
				//登记申请
				if (pWwDjsqTmpOld != null){
					hibernateDao.delete(pWwDjsqTmpOld);
				}
				hibernateDao.insert(pWwDjsqTmp);
				//抵押申请
				if (pWwDysqTmpOld != null){
					hibernateDao.delete(pWwDysqTmpOld);
				}
				hibernateDao.insert(pWwDysqTmp);
				//相关人信息
				if (pWwSqrxxTmpOldList != null){
					hibernateDao.delete(pWwSqrxxTmpOldList);
				}
				for(int cnt=0; pWwSqrxxTmpList != null && cnt<pWwSqrxxTmpList.size();cnt++){
					hibernateDao.insert(pWwSqrxxTmpList.get(cnt));
				}
				
				//图属关联
				for(int cnt=0;pWwTsglTmpOldList != null && cnt<pWwTsglTmpOldList.size();cnt++){
					hibernateDao.delete(pWwTsglTmpOldList.get(cnt));
				}
				for(int cnt=0; pWwTsglTmpList != null && cnt<pWwTsglTmpList.size();cnt++){
					hibernateDao.insert(pWwTsglTmpList.get(cnt));
				}
				
				//附件
				for(int cnt=0;pWwFjqdTmpOldList !=null && cnt<pWwFjqdTmpOldList.size();cnt++){
					hibernateDao.delete(pWwFjqdTmpOldList.get(cnt));
				}
				for(int cnt=0; pWwFjqdTmpList !=null && cnt<pWwFjqdTmpList.size();cnt++){
					hibernateDao.insert(pWwFjqdTmpList.get(cnt));
				}
				
				pApplicationContentResponseInfo.setUid(pApplicationContentRequestInfo.getUid());
				pApplicationContentResponseInfo.setErrinfo("成功");
				
			}catch (Exception e){
				pApplicationContentResponseInfo.setUid( pApplicationContentRequestInfo.getUid());
				pApplicationContentResponseInfo.setErrinfo(e.getMessage() + e.getCause().getMessage());
			}
		}else{
			pApplicationContentResponseInfo.setUid(pApplicationContentRequestInfo.getUid());
			pApplicationContentResponseInfo.setErrinfo(errString);
		}

		return pApplicationContentResponseInfo;
	}
	
	/**
	 * 检查抵押信息
	 * @param pApplicationContentRequestInfo
	 * @return
	 */
	private String CheckInfoSingle(ApplicationContentRequestInfo pApplicationContentRequestInfo){
		//检查项  非空检查
		//贷款金额小于评估价值。 抵押终止日期小于抵押起始日期 身份证号检查
		ArrayList<String> outPut = new ArrayList<String>();
		try
		{
			if(pApplicationContentRequestInfo == null){
				throw new RuntimeException("登记申请内容不存在");
			}
			if(pApplicationContentRequestInfo.getUid() == null || pApplicationContentRequestInfo.getUid().isEmpty() || pApplicationContentRequestInfo.getUid().length()<=0){
				outPut.add("业务号为空");
			}
			if(pApplicationContentRequestInfo.getSqsj() == null){
				outPut.add("申请日期为空");
			}
			if(pApplicationContentRequestInfo.getYwlx() == null || pApplicationContentRequestInfo.getYwlx().isEmpty() || pApplicationContentRequestInfo.getYwlx().length()<=0){
				outPut.add("业务类型为空");
			}
			if(pApplicationContentRequestInfo.getDyfs() == null || pApplicationContentRequestInfo.getDyfs().isEmpty() || pApplicationContentRequestInfo.getDyfs().length()<=0){
				outPut.add("抵押方式为空");
			}
			if(pApplicationContentRequestInfo.getDyje() < 1){
				outPut.add("本次抵押金额为空");
			}
			if(pApplicationContentRequestInfo.getStime() == null){
				outPut.add("权利起始日期为空");
			}
			if(pApplicationContentRequestInfo.getEtime() == null){
				outPut.add("权利终止日期为空");
			}
			if(pApplicationContentRequestInfo.getEtime() != null && pApplicationContentRequestInfo.getStime() != null && Integer.valueOf(TimeHelper.getDifferenceDay(pApplicationContentRequestInfo.getEtime().toString(), pApplicationContentRequestInfo.getStime().toString())) < 0){
				outPut.add("权利终止日期不应小于起始日期");
			}
			if(pApplicationContentRequestInfo.getDysw() == null || pApplicationContentRequestInfo.getDysw().isEmpty() || pApplicationContentRequestInfo.getDysw().length()<=0){ //to1do:抵押顺位验证暂时去掉
				outPut.add("抵押顺位为空");
//				pApplicationContentRequestInfo.Dysw = "1";
			}
			if(pApplicationContentRequestInfo.getSlry() == null || pApplicationContentRequestInfo.getSlry().isEmpty() || pApplicationContentRequestInfo.getSlry().length()<=0){
				outPut.add("受理人员为空");
			}
			if(pApplicationContentRequestInfo.getTzr() == null || pApplicationContentRequestInfo.getTzr().isEmpty() || pApplicationContentRequestInfo.getTzr().length()<=0){
				outPut.add("通知人为空");
			}
			outPut.addAll(CheckXgrxx(pApplicationContentRequestInfo.getDyqr(), true, "抵押权人"));
			outPut.addAll(CheckXgrxx(pApplicationContentRequestInfo.getJkr(), true, "借款人"));
			if(pApplicationContentRequestInfo.getDyqrdlr() != null && pApplicationContentRequestInfo.getDyqrdlr().size() >0){
				outPut.addAll(CheckXgrxx(pApplicationContentRequestInfo.getDyqrdlr(), false, "抵押权人代理人"));
			}
			if(pApplicationContentRequestInfo.getDyrdlr() != null && pApplicationContentRequestInfo.getDyrdlr().size() >0){
				outPut.addAll(CheckXgrxx(pApplicationContentRequestInfo.getDyrdlr(), false, "抵押人代理人"));
			}
			if(SystemConstants.IfCheckFj){//是否坚持附件
				//outPut.AddRange(pApplicationContentRequestInfo.Links
				//    .Select(p => DoFj(p, pApplicationContentRequestInfo.Uid))
				//    .Where(pError => !string.IsNullOrEmpty(pError)));

				//普通循环写法
				for (int lkcnt=0; pApplicationContentRequestInfo.getLinks() != null && lkcnt< pApplicationContentRequestInfo.getLinks().size();lkcnt++){
					String pDoFjOutPut = DoFj(pApplicationContentRequestInfo.getLinks().get(lkcnt), pApplicationContentRequestInfo.getUid());
					if (pDoFjOutPut != null && !pDoFjOutPut.isEmpty() && pDoFjOutPut.length()>0){
						outPut.add(pDoFjOutPut);
					}
				}

				//todo:使用并行循环 测试
				//Parallel.ForEach(pApplicationContentRequestInfo.Links, p =>
				//{
				//    var pDoFjOutPut = DoFj(p, pApplicationContentRequestInfo.Uid);
				//    if (!string.IsNullOrEmpty(pDoFjOutPut))
				//        outPut.Add(pDoFjOutPut);
				//});
			}
		}
		catch (Exception e){
			outPut.add(e.getMessage());
		}
		return outPut.toString();
	}
	
	/**
	 *  检查相关人信息
	 * @param pXgrxxList
	 * @param noNull
	 * @param xgrlx
	 * @return
	 */
	private ArrayList<String> CheckXgrxx(ArrayList<Xgrxx> pXgrxxList, boolean noNull, String xgrlx){
		ArrayList<String> outPut = new ArrayList<String>();
		for(int cnt=0;pXgrxxList != null && cnt<pXgrxxList.size();cnt++){
			outPut.addAll(CheckXgrxx(pXgrxxList.get(cnt), noNull, xgrlx));
		}
		return outPut;
	}
	
	private ArrayList<String> CheckXgrxx(Xgrxx pXgrxx, boolean noNull, String xgrlx){
		ArrayList<String> outPut = new java.util.ArrayList<String>();
		if(noNull){
			if(pXgrxx == null){
				throw new RuntimeException(xgrlx + "为空");
			}

			if(pXgrxx.getName() == null || pXgrxx.getName().isEmpty() || pXgrxx.getName().length()<=0){
				outPut.add(xgrlx + "姓名为空");
			}
			if(pXgrxx.getZjh() == null || pXgrxx.getZjh().isEmpty() || pXgrxx.getZjh().length() <=0){
				outPut.add(xgrlx + "证件号为空");
			}
			if(pXgrxx.getZjlx() == null || pXgrxx.getZjlx().isEmpty() || pXgrxx.getZjlx().length() <=0){
				outPut.add(xgrlx + "证件类型为空");
			}
			if(pXgrxx.getZjh() != null && !pXgrxx.getZjh().isEmpty() && pXgrxx.getZjlx() != null && !pXgrxx.getZjlx().isEmpty() && pXgrxx.getZjlx().equals("1")){
				if (!PersonIdVerity.isHeFa(pXgrxx.getZjh())){
					outPut.add(xgrlx + "身份证号错误");
				}
			}

		}else{
			//非必填的相关人，如果全部为空不检查。有一个不为空就检查
			if (pXgrxx == null || ((pXgrxx.getName() == null || pXgrxx.getName().isEmpty() || pXgrxx.getName().length() <=0)  && (pXgrxx.getZjh() == null || pXgrxx.getZjh().isEmpty() || pXgrxx.getZjh().length() <=0) && (pXgrxx.getZjlx() == null || pXgrxx.getZjlx().isEmpty() || pXgrxx.getZjlx().length() <=0) && (pXgrxx.getTel() == null || pXgrxx.getTel().isEmpty() || pXgrxx.getTel().length() <=0))){
			}else{
				if(pXgrxx.getName() == null || pXgrxx.getName().isEmpty() || pXgrxx.getName().length() <=0){
					outPut.add(xgrlx + "姓名为空");
				}
				if(pXgrxx.getZjh() == null || pXgrxx.getZjh().isEmpty() || pXgrxx.getZjh().length() <=0){
					outPut.add(xgrlx + "证件号为空");
				}
				if(pXgrxx.getZjlx() == null || pXgrxx.getZjlx().isEmpty() || pXgrxx.getZjlx().length() <=0){
					outPut.add(xgrlx + "证件类型为空");
				}
				if(pXgrxx.getZjh() != null && !pXgrxx.getZjh().isEmpty() && pXgrxx.getZjlx().equals("1")){
					if (!PersonIdVerity.isHeFa(pXgrxx.getZjh())){
						outPut.add(xgrlx + "身份证号错误");
					}
				}
			}
		}
		
		return outPut;
	}
	
	/**
	 * 处理附件
	 * @param pFj
	 * @param slbh
	 * @return
	 */
	private String DoFj(Fj pFj, String slbh){
		if(pFj.getName() != null && !pFj.getName().isEmpty() && pFj.getName().length()>0){
			return DoFj(pFj.getUrl(), slbh);
		}
		return null;
	}
	
	/**
	 * 处理附件
	 * @param url
	 * @param slbh
	 * @return
	 */
	private String DoFj(String url, String slbh){
		//return null;//todo：通过拼接路径取得附件，如果有附件先返回成功，附件慢慢下载
		String outPut = null;
		//string localDirName = Paramers.LocalDirName;
		//string preRemote = Paramers.PreRemote;
		try
		{
			url = url.replace("/", "\\");
			String remotePath = slbh + "\\" + url;
			HttpDl(remotePath);
		}
		catch (Exception e){
			outPut = "读取附件异常： " + url + " " + e.getMessage() + e.getCause().getMessage();
		}
		return outPut;
	}
	
	private void HttpDl(String remotePath){
//		String preString = Paramers.FjHttpAddress; //附件下载地址
//		var localPath = Path.Combine(Paramers.LocalDirName, remotePath); //根据远端地址生成本地地址        Paramers.LocalDirName：本地临时存放ftp文件夹名字
//		HttpDldFile df = new HttpDldFile();
//		var pHttpDlPath = $"{preString}{remotePath}";
//		df.Download(pHttpDlPath, localPath);
		
		HttpHelper df = new HttpHelper();
		df.downloadFile(remotePath, "e:\\test");
	}
	
}
