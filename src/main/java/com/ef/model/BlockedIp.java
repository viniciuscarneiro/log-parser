package com.ef.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blocked_ip")
public class BlockedIp {

    @Id
    @Column(name = "ip")
    private String ip;

    @Column(name = "comment")
    private String comment;

    public String getIp() {
        return ip;
    }

    public BlockedIp setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public BlockedIp setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
