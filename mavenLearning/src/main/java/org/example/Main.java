package org.example;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// 异常处理使用 Maven 导入 commons logging
public class Main {
    public static void main(String[] args) {
        Log log = LogFactory.getLog(Main.class);
        log.info("start...");
        log.warn("end.");
    }
}