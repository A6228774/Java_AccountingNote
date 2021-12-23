package com.ubayKyu.accountingSystem.Method;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class GUIDtoUUIDMethod {

	public static String convertMSGUIDToHexFormat(String guid) {
		guid = guid.replaceAll("-", "");
		guid = guid.replaceAll("(.{8})(.{4})(.{4})(.{4})(.{12})", "$1-$2-$3-$4-$5")
				.replaceAll("(.{2})(.{2})(.{2})(.{2}).(.{2})(.{2}).(.{2})(.{2})(.{18})", "$4$3$2$1-$6$5-$8$7$9");
		guid = guid.replaceAll("-", "");
		return guid;
	}

	public static String convertHexToMSGUIDFormat(String hex) {
		return hex.replaceAll("(.{8})(.{4})(.{4})(.{4})(.{12})", "$1-$2-$3-$4-$5")
				.replaceAll("(.{2})(.{2})(.{2})(.{2}).(.{2})(.{2}).(.{2})(.{2})(.{18})", "$4$3$2$1-$6$5-$8$7$9");
	}
}
