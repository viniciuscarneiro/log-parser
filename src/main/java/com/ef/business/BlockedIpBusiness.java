package com.ef.business;

import com.ef.model.BlockedIp;
import com.ef.repository.BlockedIpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedIpBusiness {

    private static final String BLOCKED_IP_MESSAGE = "%s made more than %s requests";
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlockedIpRepository blockedIpRepository;

    public void save(BlockedIp blockedIp) {
        this.blockedIpRepository.save(blockedIp);
    }

    public void processReturnedIps(Integer threshold, List<String> ipsList) {
        ipsList.forEach(ip -> {
            log.info(String.format(BLOCKED_IP_MESSAGE, ip, threshold));
            this.save(new BlockedIp.Builder().withIp(ip).withThreshold(threshold).build());
        });
    }

    public void truncateBlockedIpTable() {
        this.blockedIpRepository.deleteAll();
    }
}
