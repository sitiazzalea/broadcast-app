package com.broadcast.util.interceptor;

import com.broadcast.util.DateHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    private final LoggingHolder loggingHolder;

    @Value("${app.version}")
    private String versionApp;

    private static final Logger log = LogManager.getLogger(LoggingInterceptor.class);

    public LoggingInterceptor(LoggingHolder loggingHolder) {
        this.loggingHolder = loggingHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String remoteAddress = request.getRemoteAddr() == null ? "127.0.0.1" : request.getRemoteAddr();
            String endPointPath = request.getRequestURI() == null ? "" : request.getRequestURI();
            String param = request.getQueryString() == null ? "" : request.getRequestURI();
            Package pkg = getClass().getPackage();
            String packageName = pkg.getName();
            String version = versionApp != null ? versionApp : "Version App Not Found!";
            String date = String.valueOf(new DateHelper().getCurrentDate());
            loggingHolder.setPackageName(packageName);
            loggingHolder.setDate(date);
            loggingHolder.setFrom(remoteAddress);
            loggingHolder.setPath(endPointPath);
            loggingHolder.setData(param);
            loggingHolder.setVersion(version);
            log.info("App: {}, Date: {}, From: {}, Path: {}, Data: {}, Version: {}",
                    packageName, date, remoteAddress, endPointPath, param, version);
            return true;
        } catch (Exception e) {
            logError("Error in LoggingInterceptor", e);
            return false;
        }
    }

    public void logError(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
