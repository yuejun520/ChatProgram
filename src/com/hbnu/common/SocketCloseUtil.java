package com.hbnu.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author luanhao
 * @date 2022年11月03日 17:13
 */
public class SocketCloseUtil {
    public static void closeAll(Closeable... able) {
        for (Closeable c : able) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
