package com.fourmob.colorpicker.util;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * for printing the error message.
 */
public class LogUtil {

    private LogUtil() {
    }

    public static void error(String tag, String message) {
        HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0, tag);
        HiLog.error(label, message);
    }
}
