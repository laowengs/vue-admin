package com.laowengs.vuedashboard.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.nio.charset.Charset;

public final class SerializeUtil {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static byte[] serialize(Object object) {
		if (object == null) {
			return new byte[0];
		}
		return JSON.toJSONString(object, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	public static Object unserialize(String str) {
		return JSON.parseObject(str);
	}

}