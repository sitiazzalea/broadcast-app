package com.broadcast.util.interceptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggingHolder {
    private String packageName;
    private String date;
    private String path;
    private String from;
    private String userInhouse;
    private String userPortal;
    private String data;
    private String version;

}
