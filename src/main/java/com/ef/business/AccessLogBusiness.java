package com.ef.business;

import com.ef.model.AccessLog;
import com.ef.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessLogBusiness {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public void save(AccessLog accessLog){
        this.accessLogRepository.save(accessLog);
    }

    public void truncateAccessLogTable(){
        this.accessLogRepository.deleteAll();
    }
}
