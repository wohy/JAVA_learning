package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// 异常处理使用 Maven 导入 commons logging
// 导入流程，正常将依赖引入到 pom.xml 中，maven 重写加载即可
// log4j 使用
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        log.info("start...");
        log.warn("end.");
        log.error("123"); // [main] ERROR org.example.Main - 123
    }
}