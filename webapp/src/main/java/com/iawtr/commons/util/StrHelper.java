/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iawtr.commons.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class StrHelper {
	/**
	 * 将字符串切分得到list
	 * @param str		原字符串
	 * @param splitStr	切分用的字符串
	 * @return
	 */
	public static List<String> splitStrToList(String str,String splitStr){
		List<String> list=new ArrayList<String>();
		if(str==null||str.equals("")){
		}else{
			String[] ary=str.split(splitStr);
			for(int i=0;i<ary.length;i++){
				list.add(ary[i]);
			}
		}		
		return list;
	}
	/**
	 * 切分字符串,得到至少包含两个元素的list. 空字符串也可以代替个元素
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static List<String> getListHasTwoElements(String str,String splitStr){
		List<String> tmp=splitStrToList(str, splitStr);
		if(tmp.size()<1){
			tmp.add("");
			tmp.add("");
		}else if(tmp.size()<2){
			tmp.add("");
		}
		return tmp;
	}
    /**
     * 检验一个字符串中有多少个给定的字符
     * @param value  字符串
     * @param a    字符
     * @return    给定字符的个数
     */
    public static int getConts(String value, char a) {
        int sum = 0;
        char[] ch = value.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == a) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 将传入的字符串后面的“00”去掉
     * @param str
     * @return String
     */
    public static String abandonStr(String str) {
        String result = "";
        for (; str.length() > 0;) {
            if (str.endsWith("00")) {
                result = str.substring(0, str.length() - 2);
                str = result;
            } else {
                result = str;
                break;
            }
        }
        return result;
    }

    /**
     * 将传入的字符串转化为给定长度的字符串
     * @param value   原字符串
     * @param i   给定的长度
     * @return   原字符串的长度小于给定的长度，那么就在原字符串的前面补“0”，否则截取从原字符串的开头截取给定的长度
     */
    public static String strBuwei(String value, int i) {
        String resultStr = "";
        if (value.length() < i) {
            for (int k = 0; k < i - value.length(); k++) {
                resultStr = resultStr + "0";
            }
            resultStr = resultStr + value;
        } else {
            resultStr = value.substring(0, i);
        }
        return resultStr;
    }
/**
     * 将传入的字符串转化为给定长度的字符串
     * @param value   原字符串
     * @param i   给定的长度
     * @return   原字符串的长度小于给定的长度，那么就在原字符串的后面补“0”，否则截取从原字符串的开头截取给定的长度
     */
    public static String strHouBuwei(String value, int i) {
        String resultStr = "";
        if (value.length() < i) {
            for (int k = 0; k < i - value.length(); k++) {
                resultStr = resultStr + "0";
            }
            resultStr = value + resultStr;
        } else {
            resultStr = value.substring(0, i);
        }
        return resultStr;
    }
    /**
     * 将两个字符串数组合并，形成的字符串数组中的元素是唯一的
     * @param stringArray1
     * @param stringArray2
     * @return
     */
    public static String[] uniteStringArray(String[] stringArray1, String[] stringArray2) {
        String[] resultArray = null;
        HashMap<String, String> resultTmp = new HashMap<String, String>();
        for (int i = 0; i < stringArray1.length; i++) {
            resultTmp.put(stringArray1[i], "hebing");
        }
        for (int i = 0; i < stringArray2.length; i++) {
            resultTmp.put(stringArray2[i], "hebing");
        }
        Set<String> se = resultTmp.keySet();
        resultArray = new String[se.size()];
        se.toArray(resultArray);
        return resultArray;
    }

    /**
     * 得到比传入的字符串大1的字符串(该传入的字符串必须是能被格式化为数字型的)
     * @param str  
     * @return  
     */
    public static String getNextStr(String str) {
        String result = "";
        int strlenth = str.length();
        Long l = Long.valueOf(str);
        result = String.valueOf(l + 1);
        if (result.length() < strlenth) {
            result = strBuwei(result, strlenth);
        }
        return result;
    }

    /**
     * 得到两个数字字符串之间的所有 字符串 
     * @param str1    5
     * @param str2     8
     * @return    new String[]{5,6,7,8}
     */
    public static String[] getBetweenStr(String str1, String str2) {
        String[] result = null;
        int str1tmp = Integer.valueOf(str1).intValue();
        int str2tmp = Integer.valueOf(str2).intValue();
        result = new String[str2tmp - str1tmp + 1];
        result[0] = str1;
        for (int i = 1; i < result.length; i++) {
            result[i] = getNextStr(result[i - 1]);
        }
        return result;
    }

    /**
     * 对全是数字的字符串进行排序
     * @param tmp   要排序的字符串数组
     */
    public static void orderNumStr(String[] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            String tmpvalue = tmp[i];
            for (int k = i + 1; k < tmp.length; k++) {
                try {
                    if (Long.valueOf(filteStr(tmp[k])).longValue() < Long.valueOf(filteStr(tmpvalue)).longValue()) {
                        tmpvalue = tmp[k];
                        tmp[k] = tmp[i];
                        tmp[i] = tmpvalue;
                    }
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * 对全是数字的字符串进行排序
     * @param tmp   要排序的字符串容器
     */
    public static void orderNumStr(Vector<String> tmp) {
        for (int i = 0; i < tmp.size(); i++) {
            String tmpvalue = tmp.get(i);
            for (int k = i + 1; k < tmp.size(); k++) {
                if (Long.valueOf(filteStr(tmp.get(k))).longValue() < Long.valueOf(filteStr(tmpvalue)).longValue()) {
                    tmpvalue = tmp.get(k);
                    tmp.add(k, tmp.get(i));
                    tmp.add(i, tmpvalue);
                }
            }
        }
    }

    /**
     * 将字符串里面的字母给过滤掉
     * @param str
     * @return
     */
    public static String filteStr(String str) {
        String result = "";
        try {
            if (str != null && str.trim().length() == 0) {
                return result;
            }
            result = str.replaceAll("\\D", "");
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 将传入的字符串进行截取
     * @param originalStr
     * @param regx   匹配的第一个表达式
     * @param regx2  匹配的第二个表达式
     * @return  没有匹配的则返回源字符串 否则返回两个表达式之间的字符串
     */
    public static String interceptStrBetween(String originalStr, String regx, String regx2) {
        String targetStr = "";
        int i = originalStr.indexOf(regx);
        int l = originalStr.indexOf(regx2);
        if (i >= 0 && l >= 0) {
            targetStr = originalStr.substring(i + 1, l);
        } else if (i >= 0 && l < 0) {
            targetStr = originalStr.substring(i + 1, originalStr.length());
        } else if (i < 0 && l >= 0) {
            targetStr = originalStr.substring(0, l);
        } else {
            targetStr = originalStr;
        }
        return targetStr;
    }

    /**
     * 将传入的字符串数组中相同的元素过虑掉,使得字符串数组中的元素唯一
     * @param sourceArray
     * @return
     */
    public static String[] fileSameElem(String[] sourceArray) {
        String[] result = null;
        HashMap<String, String> resulths = new HashMap<String, String>();
        for (int i = 0; i < sourceArray.length; i++) {
            resulths.put(sourceArray[i], "wally");
        }
        result = new String[resulths.size()];
        resulths.keySet().toArray(result);
        return result;
    }
    private static char[] base64EncodeChars = new char[]{
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/'
    };
    private static byte[] base64DecodeChars = new byte[]{
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
        -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
    };

    /**
     * 将byte[]数组使用base64编码转换为字符串
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        if (data == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 使用base64编码将字符串转换为byte[]数组
     * @param data
     * @return
     */
    public static byte[] decode(String str) {
        if (str == null || str.equals("null") || str.equals("")) {
            return null;
        }
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
            /* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }
            /* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            /* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            /* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write((int) (((b3 & 0x03) << 6) | b4));
        }
        return buf.toByteArray();
    }

    /**
     * 将给定的字符串反转过来
     * @param oralStr    如:"adfs万里"
     * @return   "里万sfda"
     */
    public static String reverseStr(String oralStr) {
        String result = "";
        StringBuffer sb = new StringBuffer(oralStr);
        result = sb.reverse().toString();
        return result;
    }
    /**
     * 将多个字符串连接起来,过略每个字符串的null和首尾空格,有效的字符串之间加上连接标志
     * @param joinMark 字符串之间项链的标志
     * @param strings	要连接的多个字符串  
     * @return
     */
    public static String joinStr(String joinMark,String... strings ){
    	String result="";
    	for(String str:strings){
    		if(str!=null&&!str.trim().equals("")){
    			if(!result.equals("")){
    				result=result+joinMark;
    			}
    			result=result+str.trim();
    		}
    	}
    	return result; 
    }
    /**
     * 验证是否是unicode汉字
     * @param str
     * @return 
     */
    public static boolean matchChinese(String str){
        return str.matches("[\\p{InCJK Unified Ideographs}]*");//||str.matches("[\u3400-\u4DB5]*")||str.matches("[\u9FA6-\u9FBB]*")||str.matches("[\uF900-\uFA2D]*")||str.matches("[\uFA30-\uFA6A]*")||str.matches("[\u20000-\u2A6D6]*")||str.matches("[\uFA30-\uFA6A]*")||str.matches("[\uFA30-\uFA6A]*");
    }
    /**
	 * 获取32位的uuid
	 * 
	 * @return
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
    public static void main(String []args){
    }
}
