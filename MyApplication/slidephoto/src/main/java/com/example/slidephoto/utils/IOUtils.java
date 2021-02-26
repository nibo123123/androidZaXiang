package com.example.slidephoto.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLConnection;

public class IOUtils {
	/** 关闭流 */
	public static boolean closeIO(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return true;
	}

	/** 关闭URLConnection */
	public static boolean closeURLConnection(URLConnection connection) {
		if (connection != null) {
			try {
				connection.connect();
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return true;
	}
}
