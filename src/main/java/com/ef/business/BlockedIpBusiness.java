package com.ef.business;

import com.ef.model.BlockedIp;
import com.ef.repository.BlockedIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockedIpBusiness {

    @Autowired
    private BlockedIpRepository blockedIpRepository;

    public void save(BlockedIp blockedIp) {
        this.blockedIpRepository.save(blockedIp);
    }

    public void truncateBlockedIpTable() {
        this.blockedIpRepository.deleteAll();
    }
}
