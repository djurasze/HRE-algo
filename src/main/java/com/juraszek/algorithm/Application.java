package com.juraszek.algorithm;

import com.juraszek.algorithm.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) throws Throwable {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        HRERunner hreRunner = (HRERunner) ctx.getBean("HRERunner");
        hreRunner.start(args);
    }
}
