package dachuan.com.tianyan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private final String[] strDigits = {
			"0", "1", "2", "3", "4", "5", "6", "7","8", "9", "a", "b", "c", "d", "e", "f"};
			
		private String byteToArrayString(byte bByte) {
			int iRet = bByte;
			//System.out.println("iRet="+iRet);
			if (iRet < 0) {
				iRet+=256;
			}
			int iD1 = iRet / 16;
			int iD2 = iRet % 16;
			return strDigits[iD1] + strDigits[iD2];
		}	
		
		private String byteToString(byte[] bByte) {
			StringBuffer sBuffer=new StringBuffer();
			for (int i = 0; i < bByte.length; i++) {
				sBuffer.append(byteToArrayString(bByte[i]));
			}
			return sBuffer.toString();
		}
		
		public String getCode(String strObj){
			String resultString = null;
			try {
				resultString=new String(strObj);
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString=byteToString(md.digest(strObj.getBytes()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return resultString;		
		}
	
}
