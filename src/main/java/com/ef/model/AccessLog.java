package com.ef.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_log")
public class AccessLog {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "ip")
    private String ip;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "http_response_status")
    private String httpResponseStatus;

    @Column(name = "user_agent")
    private String userAgent;

    public Long getId() {
        return id;
    }

    public AccessLog setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public AccessLog setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public AccessLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public AccessLog setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public String getHttpResponseStatus() {
        return httpResponseStatus;
    }

    public AccessLog setHttpResponseStatus(String httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public AccessLog setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }
}
