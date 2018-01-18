package com.ef.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blocked_ip")
public class BlockedIp {

    private static final String BLOCK_MESSAGE = "BLOCKED - This IP made more than %s or more requests";

    @Id
    @Column(name = "ip")
    private String ip;

    @Column(name = "comment")
    private String comment;

    public BlockedIp() {
    }

    private BlockedIp(String ip, String comment) {
        this.ip = ip;
        this.comment = comment;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static class Builder {

        private String ip;
        private Integer threshold;

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withThreshold(Integer threshold) {
            this.threshold = threshold;
            return this;
        }

        public BlockedIp build() {
            return new BlockedIp(ip, String.format(BLOCK_MESSAGE, threshold));
        }
    }
}
