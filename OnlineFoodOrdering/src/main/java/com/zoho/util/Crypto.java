package com.zoho.util;

public class Crypto {
	public static String encryption(String pass) {
		String answer = "";
		for (int i = 0; i < pass.length(); i++) {
			char ch = pass.charAt(i);
			if (Character.isDigit(ch)) {
				if (ch == '9')
					answer += '0';
				else
					answer += (char) (ch + 1);
			} else {
				if (ch == 'z' || ch == 'Z') {
					answer += (char) (ch - 25);
				} else {
					answer += (char) (ch + 1);
				}
			}
		}
		return answer;
	}

	public static String decryption(String pass) {
		String answer = "";
		for (int i = 0; i < pass.length(); i++) {
			char ch = pass.charAt(i);
			if (Character.isDigit(ch)) {
				if (ch == '0')
					answer += '9';
				else
					answer += (char) (ch - 1);
			} else {
				if (ch == 'a' || ch == 'A') {
					answer += (char) (ch + 25);
				} else {
					answer += (char) (ch - 1);
				}
			}
		}
		return answer;
	}
}
