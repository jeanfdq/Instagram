package com.instagram.util;

import android.util.Base64;

public class CodeEncodeBase64 {

	public static String Encode64(String text){
		return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)",""); //replace para retirar o enter da string
	}

	public static String Decode64(String text){
		return new String(Base64.decode(text, Base64.DEFAULT));
	}
}
