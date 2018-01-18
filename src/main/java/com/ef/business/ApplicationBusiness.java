package com.ef.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationBusiness {

    @Autowired
    private AccessLogBusiness accessLogBusiness;
    @Autowired
    private BlockedIpBusiness blockedIpBusiness;

    public void wipeDatabase() {
        this.accessLogBusiness.truncateAccessLogTable();
        this.blockedIpBusiness.truncateBlockedIpTable();
    }
}
