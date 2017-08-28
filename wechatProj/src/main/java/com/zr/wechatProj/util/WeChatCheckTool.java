package com.zr.wechatProj.util;

import com.zr.wechatProj.dto.WeChatDataDto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author zourui On 15:23 2017/8/28
 * @version 1.0
 */
public class WeChatCheckTool {
    /**
     * 校验微信服务器验证信息
     *
     * @param
     * @return true or false
     * @throws
     */
    public static boolean checkData(WeChatDataDto dto, String token) throws Exception {
        String[] code = new String[]{token, dto.getTimestamp(), dto.getNonce()};
        //字典序排序
        Arrays.sort(code);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < code.length; i++) {
            sb.append(code[i]);
        }
        String result = encipherBySHA(sb.toString());
        if (result.equals(dto.getSignature().toUpperCase())) {
            return true;
        }
        return false;
    }

    /**
     * sha1加密
     *
     * @param
     * @return
     * @throws
     */
    private static String encipherBySHA(String code) throws Exception {
        String bysha = "";
        try {
            byte[] b = code.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] digest = sha.digest(b);
            bysha = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bysha;
    }

    /**
     * byte转string
     *
     * @param
     * @return
     * @throws
     */
    private static String byteToStr(byte[] byteArray) throws Exception {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * byte转hex
     *
     * @param
     * @return
     * @throws
     */
    private static String byteToHexStr(byte mByte) throws Exception {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
