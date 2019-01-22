package com.iawtr.commons.util;

import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class PersonIdVerity {

    private static String pid;
    public final static HashMap<String, String> xzqhHead = new HashMap<String, String>();

    static {
        xzqhHead.put("11", "北京");
        xzqhHead.put("12", "天津");
        xzqhHead.put("13", "河北");
        xzqhHead.put("14", "山西");
        xzqhHead.put("15", "内蒙古");
        xzqhHead.put("21", "辽宁");
        xzqhHead.put("22", "吉林");
        xzqhHead.put("23", "黑龙江");
        xzqhHead.put("31", "上海");
        xzqhHead.put("32", "江苏");
        xzqhHead.put("33", "浙江");
        xzqhHead.put("34", "安徽");
        xzqhHead.put("35", "福建");
        xzqhHead.put("36", "江西");
        xzqhHead.put("37", "山东");
        xzqhHead.put("41", "河南");
        xzqhHead.put("42", "湖北");
        xzqhHead.put("43", "湖南");
        xzqhHead.put("44", "广东");
        xzqhHead.put("45", "广西");
        xzqhHead.put("46", "海南");
        xzqhHead.put("50", "重庆");
        xzqhHead.put("51", "四川");
        xzqhHead.put("52", "贵州");
        xzqhHead.put("53", "云南");
        xzqhHead.put("54", "西藏");
        xzqhHead.put("61", "陕西");
        xzqhHead.put("62", "甘肃");
        xzqhHead.put("63", "青海");
        xzqhHead.put("64", "宁夏");
        xzqhHead.put("65", "新疆");
        xzqhHead.put("71", "台湾");
        xzqhHead.put("81", "香港");
        xzqhHead.put("82", "澳门");
        xzqhHead.put("91", "国外");
    }

//    public static Date StrToDate(String value) {
//        if (value == null || value.trim().length() != 8) {
//            return null;
//        } else {
//            int year = StrToInt(value.substring(0, 4));
//            int month = StrToInt(value.substring(4, 6)) - 1;
//            int day = StrToInt(value.substring(6, 8));
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(year, month, day);
//            return calendar.getTime();
//        }
//    }

//    public static int StrToInt(String value) {
//        if (value == null || value.trim().length() == 0) {
//            return 0;
//        }
//        try {
//            return new Integer(value);
//        } catch (Exception e) {
//            return 0;
//        }
//    }

    public static boolean isHeFa() {
        if ((pid == null)) {
            return false;
        }
        int len = pid.length();
        if (len != 15 && len != 18) {
            return false;
        }
        for (int i = 0; i < (len == 15 ? len : len - 1); i++) {
            try {
                Integer.parseInt("" + pid.charAt(i));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (xzqhHead.get(pid.substring(0, 2)) == null) {
            return false;
        }
        String csrq = "";
        if (len == 15) {
            csrq = "19" + pid.substring(6, 12);
            if (!TimeHelper.validateDate(csrq)) {
                return false;
            }
        }
        if (len == 18) {
            csrq = pid.substring(6, 14);
            if (!TimeHelper.validateDate(csrq)) {
                return false;
            }
            if (pid.charAt(17) != getVerify(pid)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isHeFa(String _sfz) {
        pid = _sfz;
        return isHeFa();
    }

    /**
     *
     * @param century  19xx 年用 19，20xx 年用 20
     * @param idCardNo15 待转换的 15 位身份证号码
     * @return
     */
    public static String from15to18(int century, String shengfenzhenghao15) {
        String centuryStr = "" + century;
        if (century < 0 || centuryStr.length() != 2) {
            return shengfenzhenghao15;
        }
        if (!(isHeFa(shengfenzhenghao15) && shengfenzhenghao15.length() == 15)) {
            return  shengfenzhenghao15;
        }
        int[] weight = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        // 通过加入世纪码, 变成 17 为的新号码本体.
        String newNoBody = shengfenzhenghao15.substring(0, 6) + centuryStr + shengfenzhenghao15.substring(6);
        //下面算最后一位校验码
        int checkSum = 0;
        for (int i = 0; i < 17; i++) {
            int ai = Integer.parseInt("" + newNoBody.charAt(i)); // 位于 i 位置的数值
            checkSum = checkSum + ai * weight[i];
        }
        int checkNum = checkSum % 11;
        String checkChar = null;
        switch (checkNum) {
            case 0:
                checkChar = "1";
                break;
            case 1:
                checkChar = "0";
                break;
            case 2:
                checkChar = "X";
                break;
            default:
                checkChar = "" + (12 - checkNum);
        }
        return newNoBody + checkChar;
    }

    public static String from18to15(String idCardNo18) {
        if (!(isHeFa(idCardNo18) && idCardNo18.length() == 18)) {
            return idCardNo18;
        }
        return idCardNo18.substring(0, 6) + idCardNo18.substring(8, 17);
    }

    public static char getVerify(String id) {
        char pszSrc[] = id.toCharArray();
        int iS = 0;
        int iW[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char szVerCode[] = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int i;
        for (i = 0; i < 17; i++) {
            iS += (int) (pszSrc[i] - '0') * iW[i];
        }
        int iY = iS % 11;
        return szVerCode[iY];
    }

    public static void main(String[] arg) {
    }
}
