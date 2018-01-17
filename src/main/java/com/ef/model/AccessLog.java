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
    private Integer httpResponseStatus;

    @Column(name = "user_agent")
    private String userAgent;

}
