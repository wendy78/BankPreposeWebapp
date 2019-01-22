package com.iawtr.web.init;

import com.iawtr.commons.util.TimeHelper;
import com.iawtr.web.enumerate.SystemConstants;

public class AppClusterThread {
	public static void init() {
//		ScheduleJobDescription wsjd = new ScheduleJobDescription();
//		wsjd.setRecordId("AppClusterThread_" + SystemConstants.APPLICATIONIP.replaceAll("\\.", "_"));// private String recordId; //任务主键 uuid
//		wsjd.setModifyTime(TimeHelper.getCurrentTime14());// private String modifyTime; //修改时间
//		/**
//		 * prototype 标准 singleton 单例 类似spring的bean配置
//		 */
//		wsjd.setJobGroup("prototype");// private String jobGroup; //任务组
//		wsjd.setJobName("threadInit_" + SystemConstants.APPLICATIONIP.replaceAll("\\.", "_"));// private String jobName; //任务名 在prototype组中,可以存在相同的任务名,在singleton组中,任务名唯一
//		wsjd.setExecutiveOnIp(SystemConstants.APPLICATIONIP);// private String executiveOnIp; //执行任务的ip
//		wsjd.setClazz("clusterRegister");// private String clazz; //工作类,使用spring中配置的beanid,由spring来提供类的创建,必须是AbstractJob的子类
//		AbstractJob aj = (AbstractJob) SpringFactoryHelp.getApplicationContext().getBean("clusterRegister");
//		wsjd.setDescription(aj.getDescription());// private String description; //工作类描述
//		wsjd.setArguments("");// private String arguments; //执行用参数,推荐使用json表达式
//		wsjd.setExceptionAgainCount("0");// private String exceptionAgainCount; //工作异常已重新执行的次数
//		wsjd.setExceptionAgaiMaxCount("3");// private String exceptionAgaiMaxCount="3";//工作异常重新执行的最大次数
//		wsjd.setRepeatCount("1");// private String repeatCount="0";//工作重复执行次数 小于等于0表示无限循环
//		wsjd.setRightCount("0");// private String rightCount; //工作执行成功的次数 在非无限循环的条件下不会大于repeatCount
//		// /**
//		// * 1 CronTrigger
//		// * 2 SimpleTrigger
//		// */
//		wsjd.setTriggerType("2");// private String triggerType; //使用的触发器类型 取值1 表示CronTrigger或者2 表示SimpleTrigger
//		wsjd.setCronExpression("");// private String cronExpression; //执行时间表达式 triggerType类型为CronTrigger时,使用该表达式
//		wsjd.setStartDelay("1");// private String startDelay; //延时执行,单位妙. triggerType类型为impleTrigger时,指定任务多长时间后开始,以任务添加成功后的时间作为参照
//		wsjd.setRepeatInterval("30");// private String repeatInterval; //重复执行间隔时间,单位妙 triggerType类型为impleTrigger时,指定任务执行的间隔时间
//		wsjd.setStartedTimeLately("");// private String startedTimeLately; //工作不久前开始时间
//		wsjd.setEndedTimeLately("");// private String endedTimeLately; //工作不久前结束时间
//		// /**
//		// * None：Trigger已经完成，且不会再执行，或者找不到该触发器，或者Trigger已经被删除
//		// NORMAL:正常状态
//		// PAUSED：暂停状态
//		// COMPLETE：触发器完成，但是任务可能还正在执行中
//		// BLOCKED：线程阻塞状态
//		// ERROR：出现错误
//		// */
//		wsjd.setTriggerStatus("none");// private String triggerStatus; //触发器状态 此字段描述的是定时器的状态. 由另外的线程来监控该触发器,zookeeper保证定时器的正常
//		// /**
//		// * 0 工作未安排
//		// * 1 工作未执行等待中
//		// 2 工作执行中
//		// 3 工作执行完成等待下次执行中
//		// 4 工作执行完成,已退出任务.
//		// 5 工作执行出错,等待下次执行中
//		// 6 工作执行出错,已退出任务.
//		// 7 工作被停止了
//		// */
//		wsjd.setJobStatus("0");// private String jobStatus; //工作状态 此字段描述的就是业务本身的逻辑是否正常执行
//		// /**
//		// * 1 异步上报
//		// 2 异步下载
//		// 3 同步上报
//		// 4 同步下载
//		// 5 核查
//		// 6 统计报表
//		// 7 服务器维护 该类型的任务会在每个应用服务器上都启动相同任务
//		// 99 其他
//		// */
//		wsjd.setCategoryCode("7");// private String categoryCode; //业务类型
//		wsjd.setCategoryParaphrase("服务器维护");// private String categoryParaphrase;//业务类型释义
//		wsjd.setJobComment("项目部署启动后进行集群的注册");// private String jobComment; //备注
//		new JobAssist().startTaskAsync(wsjd);
	}
}
