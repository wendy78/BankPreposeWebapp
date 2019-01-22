package com.iawtr.service.bdch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iawtr.database.dto.DjDjb;
import com.iawtr.database.dto.DjDy;
import com.iawtr.database.dto.DjQlr;
import com.iawtr.database.dto.DjQlrgl;
import com.iawtr.database.dto.DjYg;
import com.iawtr.database.dto.FcHQsdc;
import com.iawtr.database.dto.ZdQsdc;
import com.iawtr.service.bdch.OwnershipInfoRequestService;
import com.iawtr.service.main.impl.MainServiceImpl;
import com.iawtr.web.dto.bdch.Bdcdyxx;
import com.iawtr.web.dto.bdch.Dyqk;
import com.iawtr.web.dto.bdch.ISxkYwb;
import com.iawtr.web.dto.bdch.OwnershipInfoRequestData;
import com.iawtr.web.dto.bdch.OwnershipInfoRequestInfo;
import com.iawtr.web.dto.bdch.OwnershipInfoResponse;
import com.iawtr.web.dto.bdch.OwnershipInfoResponseData;
import com.iawtr.web.dto.bdch.OwnershipInfoResponseInfo;
import com.iawtr.web.dto.bdch.Xgrxx;

public class OwnershipInfoRequestServiceImpl extends MainServiceImpl implements OwnershipInfoRequestService{

	public OwnershipInfoResponse GetResponse(OwnershipInfoRequestData data) throws Exception{
		OwnershipInfoResponse infoRps = new OwnershipInfoResponse();	//返回信息
		if(data == null || data.getArrayList() == null || data.getArrayList().size() <=0){
//			infoRps.setCode("1");
//			infoRps.setMsg("传入数据为空");
			throw new Exception("传入数据为空");
		}
		
		OwnershipInfoResponseData rpsData = new OwnershipInfoResponseData();
		ArrayList<OwnershipInfoResponseInfo> rpsInfolst = new ArrayList<OwnershipInfoResponseInfo>();
		//--根据传入的参数，查询数据库，得到结果返回前端（多组查询条件）--
		for(int cnt =0;data.getArrayList() != null && cnt < data.getArrayList().size(); cnt++){
			rpsInfolst.addAll(GetRequestInfoSingle(data.getArrayList().get(cnt)));
		}
		
		rpsData.setArrayList(rpsInfolst);	//查询结果列表
//		rpsData.setLimit();
//		rpsData.setTotalCount(); //查询记录数
		
		infoRps.setCode("0");
		infoRps.setMsg("返回查询");
		infoRps.setData(rpsData);
		return infoRps;
	}
	
	/**
	 * 根据传入的一组参数，查询数据库，得到结果返回
	 * @param pOwnershipInfoRequestInfo
	 * @return
	 * @throws Exception 
	 */
	private ArrayList<OwnershipInfoResponseInfo> GetRequestInfoSingle(OwnershipInfoRequestInfo pOwnershipInfoRequestInfo) throws Exception{
		ArrayList<OwnershipInfoResponseInfo> rpsInfo = new ArrayList<OwnershipInfoResponseInfo>();
		
		//--判断传入的查询条件是否合法--
		if(pOwnershipInfoRequestInfo == null || pOwnershipInfoRequestInfo.getQlr() == null || pOwnershipInfoRequestInfo.getQlrzjh() == null || pOwnershipInfoRequestInfo.getBdczh() == null || pOwnershipInfoRequestInfo.getBdcdyh() == null){
			throw new Exception("传入数据为空");
		}
		
		String hql=" from DjDjb p,DjQlrgl rgl,DjQlr r where p.slbh=rgl.slbh and rgl.qlrid=r.qlrid and p.lifecycle=0 ";
		HashMap<String, Object> mapValues = new HashMap<String, Object>();
		if(pOwnershipInfoRequestInfo.getQlr() != null && !pOwnershipInfoRequestInfo.getQlr().isEmpty()){
			hql = hql + " and r.qlrmc=:rmc ";
			mapValues.put("rmc", pOwnershipInfoRequestInfo.getQlr());
		}
		if(pOwnershipInfoRequestInfo.getQlrzjh() != null && !pOwnershipInfoRequestInfo.getQlrzjh().isEmpty()){
			hql = hql + " and r.zjhm=:zjhm ";
			mapValues.put("zjhm", pOwnershipInfoRequestInfo.getQlrzjh());
		}
		if(pOwnershipInfoRequestInfo.getBdczh() != null && !pOwnershipInfoRequestInfo.getBdczh().isEmpty()){
			hql = hql + " and p.bdczh=:bdczh ";
			mapValues.put("bdczh", pOwnershipInfoRequestInfo.getBdczh());
		}
		if(pOwnershipInfoRequestInfo.getBdcdyh() != null && !pOwnershipInfoRequestInfo.getBdcdyh().isEmpty()){
			hql = hql + " and p.bdcdyh=:bdcdyh ";
			mapValues.put("bdcdyh", pOwnershipInfoRequestInfo.getBdcdyh());
		}
		
		List<Object[]> obj =hibernateDao.findListByHql(hql,1,0, mapValues);
		if(obj != null && obj.size() >30){
			throw new Exception("查询出权证过多，请输入更多的条件");
		}
		
		//组织返回数据
		ISxkYwb ywb = null;
		DjDjb djb = null;
		DjQlrgl qlrgl = null;
		DjQlr qlr = null;
		for(int qzcnt=0; qzcnt<obj.size(); qzcnt++){
			ywb = new ISxkYwb();
			djb = (DjDjb)obj.get(qzcnt)[0];
			qlrgl = (DjQlrgl)obj.get(qzcnt)[1];
			qlr = (DjQlr)obj.get(qzcnt)[2];
			
			ywb.setSlbh(djb.getSlbh());
			ywb.setBdczh(djb.getBdczh());
			ywb.setZsxlh("权证");
			rpsInfo.add(GetOwnershipInfoResponseInfo(ywb));
		}
		
		hql=" from DjYg p,DjQlrgl rgl,DjQlr r where p.slbh=rgl.slbh and rgl.qlrid=r.qlrid and p.lifecycle=0 ";
		mapValues = new HashMap<String, Object>();
		if(pOwnershipInfoRequestInfo.getQlr() != null && !pOwnershipInfoRequestInfo.getQlr().isEmpty()){
			hql = hql + " and r.qlrmc=:rmc ";
			mapValues.put("rmc", pOwnershipInfoRequestInfo.getQlr());
		}
		if(pOwnershipInfoRequestInfo.getQlrzjh() != null && !pOwnershipInfoRequestInfo.getQlrzjh().isEmpty()){
			hql = hql + " and r.zjhm=:zjhm ";
			mapValues.put("zjhm", pOwnershipInfoRequestInfo.getQlrzjh());
		}
		if(pOwnershipInfoRequestInfo.getBdczh() != null && !pOwnershipInfoRequestInfo.getBdczh().isEmpty()){
			hql = hql + " and rgl.bdczh=:bdczh ";
			mapValues.put("bdczh", pOwnershipInfoRequestInfo.getBdczh());
		}
		if(pOwnershipInfoRequestInfo.getBdcdyh() != null && !pOwnershipInfoRequestInfo.getBdcdyh().isEmpty()){
			hql = hql + " and p.bdcdyh=:bdcdyh ";
			mapValues.put("bdcdyh", pOwnershipInfoRequestInfo.getBdcdyh());
		}
		obj =hibernateDao.findListByHql(hql,1,0, mapValues);
		if(obj != null && obj.size() >30){
			throw new Exception("查询出预告过多，请输入更多的条件");
		}
		
		//组织返回数据
		DjYg yg = null; 	//预购
		for(int ygcnt=0; ygcnt<obj.size(); ygcnt++){
			ywb = new ISxkYwb();
			yg = (DjYg)obj.get(ygcnt)[0];
			qlrgl = (DjQlrgl)obj.get(ygcnt)[1];
			qlr = (DjQlr)obj.get(ygcnt)[2];
			
			ywb.setSlbh(djb.getSlbh());
			ywb.setBdczh(djb.getBdczh());
			ywb.setZsxlh("预告");
			rpsInfo.add(GetOwnershipInfoResponseInfo(ywb));
		}

		return rpsInfo;
	}
	
	private OwnershipInfoResponseInfo GetOwnershipInfoResponseInfo(ISxkYwb pZsZm){
		OwnershipInfoResponseInfo info = new OwnershipInfoResponseInfo();
		
		//查封异议信息
		String hql=" from DjCf p,DjXgdjgl xggl where p.slbh=xggl.zslbh and p.lifecycle=0  and xggl.fslbh=:slbh ";
		HashMap<String, Object> mapValues = new HashMap<String, Object>();
		mapValues.put("slbh", pZsZm.getSlbh());
		List<Object[]> obj =hibernateDao.findListByHql(hql,1,0, mapValues);
		if(obj != null && obj.size()>0){
			info.setXzxx("已查封");
		}else{
			hql=" from DjCf p,DjTsgl gl,DjTsgl gll where p.slbh=gl.slbh and gl.tstybm=gl1.tstybm and p.lifecycle=0 and gl1.slbh=:slbh ";
//			mapValues = new HashMap<String, Object>();
//			mapValues.put("slbh", pZsZm.getSlbh());
			obj =hibernateDao.findListByHql(hql,1,0, mapValues);
			if(obj != null && obj.size()>0){
				info.setXzxx("已查封");
			}else{
				hql=" from DjYy p,DjXgdjgl xggl where p.slbh=xggl.zslbh and p.lifecycle=0  and xggl.fslbh=:slbh ";
//				mapValues = new HashMap<String, Object>();
//				mapValues.put("slbh", pZsZm.getSlbh());
				obj =hibernateDao.findListByHql( hql,1,0, mapValues);
				if(obj != null && obj.size()>0){
					info.setXzxx("已异议");
				}else{
					hql=" from DjYy p,DjTsgl gl,DjTsgl gll where p.slbh=gl.slbh and gl.tstybm=gl1.tstybm and p.lifecycle=0 and gl1.slbh=:slbh ";
//					mapValues = new HashMap<String, Object>();
//					mapValues.put("slbh", pZsZm.getSlbh());
					obj =hibernateDao.findListByHql(hql,1,0, mapValues);
					if(obj != null && obj.size()>0){
						info.setXzxx("已异议");
					}
				}
				
			}
		}
		
		//------------权利人信息
		hql=" from DjQlr p,DjQlrgl rgl where p.qlrid = rgl.qlrid and (rgl.qlrlx='权利人' or rgl.qlrlx is null or rgl.qlrlx='') and rgl.slbh=:slbh ";
//		mapValues = new HashMap<String, Object>();
//		mapValues.put("slbh", pZsZm.getSlbh());
		obj =hibernateDao.findListByHql(hql,1,0, mapValues);
		
		Xgrxx xgr = null;
		ArrayList<Xgrxx> xgrlst = new ArrayList<Xgrxx>();
		DjQlr qlr = null;
		for(int qlrcnt=0; obj != null && qlrcnt<obj.size(); qlrcnt++){
			xgr = new Xgrxx();
			qlr = (DjQlr)obj.get(qlrcnt)[0];
			
			xgr.setName(qlr.getQlrmc());	//姓名
			xgr.setXh(0);	//序号
			xgr.setZjlx(qlr.getZjlb());	//证件类别
			xgr.setZjh(qlr.getZjhm());	//证件号码
			//电话
			if(qlr.getDh() == null || qlr.getDh().isEmpty() || qlr.getDh().trim().length() <=0){
				xgr.setTel("");
			}else{
				xgr.setTel(qlr.getDh());
			}
			
			xgrlst.add(xgr);
		}
		info.setQlr(xgrlst);
		
		//------------不动产单元信息
		hql=" from FcHQsdc p,DjTsgl gl where p.tstybm=gl.tstybm and p.lifecycle=0 and gl.slbh=:slbh";
//		mapValues = new HashMap<String, Object>();
//		mapValues.put("slbh", pZsZm.getSlbh());
		obj =hibernateDao.findListByHql(hql,1,0, mapValues);
		
		Bdcdyxx bdcxx = null;
		ArrayList<Bdcdyxx> bdclst = new ArrayList<Bdcdyxx>();
		FcHQsdc dc = null;
		for(int bdccnt=0; obj != null && bdccnt< obj.size(); bdccnt++){
			bdcxx = new Bdcdyxx();
			dc = (FcHQsdc)obj.get(bdccnt)[0];
			
			bdcxx.setHbh(dc.getTstybm());	//图属统一编码
			bdcxx.setBdcdyh(dc.getBdcdyh());	//不动产单元号
			bdcxx.setZl(dc.getZl());	//坐落
			//实测建筑面积
			if(dc.getJzmj() != null){
				bdcxx.setJzmj(dc.getJzmj().doubleValue());
			}else{
				bdcxx.setJzmj(0);
			}
			
			bdclst.add(bdcxx);
		}
		if(obj != null && obj.size() >0 ){
			info.setBdcdyxx(bdclst);
		}else{
			//针对宗地的情况
			hql=" from ZdQsdc p,DjTsgl gl where p.tstybm = gl.tstybm and p.lifecycle=0 and gl.slbh:= slbh";
//			mapValues = new HashMap<String, Object>();
//			mapValues.put("slbh", pZsZm.getSlbh());
			obj =hibernateDao.findListByHql(hql,1,0, mapValues);
			
			ZdQsdc zd = null;
			for(int zdcnt=0; obj != null && zdcnt< obj.size(); zdcnt++){
				bdcxx = new Bdcdyxx();
				zd = (ZdQsdc)obj.get(zdcnt)[0];
				
//				bdcxx.setHbh(zd.getTstybm());	//图属统一编码
				bdcxx.setBdcdyh(zd.getBdcdyh());	//不动产单元号
				bdcxx.setZl(zd.getTdzl());	//土地坐落
				//发证面积
				if(zd.getFzmj() != null){
					bdcxx.setJzmj(zd.getFzmj().doubleValue());
				}else{
					bdcxx.setJzmj(0);
				}
				
				bdclst.add(bdcxx);
			}
			info.setBdcdyxx(bdclst);
		}
		
		//抵押情况
		hql=" from DjDy p,DjXgdjgl xggl where p.slbh = xggl.zslbh and p.lifecycle=0 and xggl.fslbh=:slbh ";
//		mapValues = new HashMap<String, Object>();
//		mapValues.put("slbh", pZsZm.getSlbh());
		obj =hibernateDao.findListByHql(hql,1,0, mapValues);
		
		Dyqk dyqk = null;	//抵押情况
		ArrayList<Dyqk> Dyqklst = new ArrayList<Dyqk>();
		DjDy dy= null;
		for(int dycnt=0; obj != null && dycnt < obj.size(); dycnt++){
			dyqk = new Dyqk();
			dy =(DjDy)obj.get(dycnt)[0];
			
			dyqk.setId(dy.getSlbh());
			if(dy.getBdbzzqse() != null){
				dyqk.setJe(dy.getBdbzzqse().doubleValue());
			}else{
				dyqk.setJe(0);
			}
			dyqk.setDysw(dy.getDysw().intValue());
			
			Dyqklst.add(dyqk);
		}
		info.setDyqk(Dyqklst);
		
		
		return info;
	}
}
