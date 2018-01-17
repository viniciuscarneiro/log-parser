package com.ef.repository;

import com.ef.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}
