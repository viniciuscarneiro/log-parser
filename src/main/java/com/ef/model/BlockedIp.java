package com.ef.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_ip")
public class BlockedIp {

    @Id
    @Column(name = "ip")
    private String ip;

    @Column(name = "comment")
    private String comment;

}
