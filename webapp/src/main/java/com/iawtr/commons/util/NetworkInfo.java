package com.iawtr.commons.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NetworkInfo {
	/**
	 * 返回所有的活动的真实ip
	 * 
	 * @return ipv4和ipv6作为主键的map集合
	 * @throws SocketException 抛出此异常指示在底层协议中存在错误，如 TCP 错误。
	 * @throws UnsupportedEncodingException 
	 */
	public static HashMap<String, HashMap<String,String>> getAllRealActiveIp() throws SocketException, UnsupportedEncodingException {
		HashMap<String, HashMap<String,String>> allIps = new HashMap<String, HashMap<String,String>>();
		Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		for (int i=0;allNetInterfaces.hasMoreElements();i++) {
			NetworkInterface netInterface = allNetInterfaces.nextElement();
			if (netInterface.isUp() == false || netInterface.isVirtual()) {
				continue;
			}
			HashMap<String, String> ipInfo=new HashMap<String, String>();
			ipInfo.put("displayName", netInterface.getDisplayName());
			byte[] macByte=netInterface.getHardwareAddress();
			StringBuffer sb=new StringBuffer();
			for (int b = 0; b < macByte.length; b++) {
				// byte表示范围-128~127，因此>127的数被表示成负数形式，这里+256转换成正数
				int m = macByte[b] < 0 ? (macByte[b] + 256) : macByte[b];
				sb.append(Integer.toHexString(m).toUpperCase() + "\t");
			}
			ipInfo.put("mac", sb.toString()) ;			
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ia = addresses.nextElement();
				if (ia == null) {
					continue;
				}
				if (ia instanceof Inet4Address) {
					ipInfo.put("ipv4", ia.getHostAddress());
				}
				if (ia instanceof Inet6Address) {
				ipInfo.put("ipv6", ia.getHostAddress());
				}
			}
			allIps.put("networkCard"+i, ipInfo);
		}
		return allIps;
	}
	/**
	 * 获取和目标ip通信的本机ip 此方法使用NetworkInterface类本身的功能
	 * 
	 * @param targetIp 目标ipv4地址 如:"192.168.2.12"
	 * @param connectFlag 通信方式 . socket ,使用socket连接方式验证通信的是哪个ip,需要目标地址的端口也可用;ni ,推荐使用NetworkInterface的isReachable(NetworkInterface netif, int ttl, int timeout)方法来验证类似ping
	 * @param targetPort 端口 socket连接方式下需要指定.该端口可以是任何基于 TCP 协议的开放服务的可用端口（如一般都会开放的 ECHO 服务端口 7, Linux 的 SSH 服务端口 22 等）
	 * @return 本机ip
	 * @throws IOException
	 */
	public static String getLocalIpv4ToConnectTargetIpv4(String targetIp, String connectFlag, int targetPort) throws IOException {
		String localIp = "";
		String[] ipArray = targetIp.split("\\.");
		byte[] ipbt = new byte[4];
		for (int i = 0; i < 4; i++) {
			ipbt[i] = (byte) Integer.valueOf(ipArray[i]).intValue();
		}
		InetAddress targetAddress = InetAddress.getByAddress(ipbt);
		if (!targetAddress.isReachable(5000)) {
			return localIp;
		}
		Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = allNetInterfaces.nextElement();
			if (netInterface.isUp() != true || netInterface.isVirtual()) {
				continue;
			}
			
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			if (connectFlag.equals("ni")) {
				if (!targetAddress.isReachable(netInterface, 0, 5000)) {
					continue;
				}
				while (addresses.hasMoreElements()) {
					InetAddress ia = addresses.nextElement();
					if (ia == null) {
						continue;
					}
					if (ia instanceof Inet4Address) {
						localIp = ia.getHostAddress();
						return localIp;
					}
				}
			} else if (connectFlag.equals("socket")) {
				while (addresses.hasMoreElements()) {
					InetAddress ia = addresses.nextElement();
					if (ia == null) {
						continue;
					}
					if (ia instanceof Inet4Address) {
						Socket socket = null;
						try {
							socket = new Socket();
							// 端口号设置为 0 表示在本地挑选一个可用端口进行连接
							SocketAddress localSocketAddr = new InetSocketAddress(ia, 0);
							socket.bind(localSocketAddr);
							InetSocketAddress endpointSocketAddr = new InetSocketAddress(targetAddress, targetPort);
							socket.connect(endpointSocketAddr, 5000);
							System.out.println("SUCCESS - connection established! Local: " + ia.getHostAddress() + " remote: " + targetAddress.getHostAddress() + " port" + targetPort);
							localIp = ia.getHostAddress();
							return localIp;
						} catch (IOException e) {
							System.out.println("FAILRE - CAN not connect! Local: " + ia.getHostAddress() + " remote: " + targetAddress.getHostAddress() + " port" + targetPort);
						} finally {
							if (socket != null) {
								try {
									socket.close();
								} catch (IOException e2) {
								}
							}
						}
					}
				}
			}
		}
		return localIp;
	}
	/**
	 * 在多网卡的情况下,和目标地址通信的appip
	 * @param iplis	目标的ip集合	list{"192.168.1.82","192.168.2.83"}
	 * @return	顺次访问list中的地址,只要其中一个能通,就返回那个活动的地址
	 * @throws IOException
	 */
	public static String getAppIp(List<String> iplis) throws IOException{
		String appIp="";
		for(int i=0;i<iplis.size();i++){
			
			String ip=NetworkInfo.getLocalIpv4ToConnectTargetIpv4(iplis.get(i), "ni", 22);
			if(ip!=null&&!ip.trim().equals("")){
				appIp=ip;
				break;
			}
		}
		return appIp;
	}
	public static void main(String[] adf) {
		try {
			HashMap<String, HashMap<String,String>> hs = NetworkInfo.getAllRealActiveIp();
			System.out.println("获取所有活动的真实ip--" + hs);
			Set<String> keys = hs.keySet();
			Iterator<String> its = keys.iterator();
			while (its.hasNext()) {
				String key = its.next();
				HashMap<String,String> subMap = hs.get(key);
				Set<String> subKeys = subMap.keySet();
				Iterator<String> subIts = subKeys.iterator();
				while(subIts.hasNext()){
					String subKey=subIts.next();
					String subValue=subMap.get(subKey);
					System.out.println(key+" 的信息为: "+subKey+" --- "+subValue);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// String targetIp="192.168.0.251";
//			String targetIp = "202.100.64.68";
			 String targetIp="115.28.170.83";
			// String targetIp = "127.0.0.1";
			String localIp = NetworkInfo.getLocalIpv4ToConnectTargetIpv4(targetIp, "ni", 1521);
			System.out.println("和目标ip " + targetIp + " 通信的本机ip是-" + localIp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
