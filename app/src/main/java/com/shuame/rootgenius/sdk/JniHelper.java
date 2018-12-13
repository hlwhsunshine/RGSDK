package com.shuame.rootgenius.sdk;

import java.util.HashMap;

/**
 * Function:
 * Project:RGSDK
 * Date:2018/11/15
 * Created by xiaojun .
 */

public class JniHelper {

    public static class Data implements Cloneable {
        public Object cntx;
        public HashMap field;

        public Data clone() {
            try {
                return (Data) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public static native int rgMain(Data data);
    public static native boolean verify(Object object);
}
