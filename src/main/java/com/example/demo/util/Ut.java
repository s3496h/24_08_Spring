package com.example.demo.util;

import java.lang.reflect.Array;
import java.util.Map;

public class Ut {

	public static boolean isEmptyOrNull(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return ((String) obj).trim().length() == 0;
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}

		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}

		return false;
	}
}