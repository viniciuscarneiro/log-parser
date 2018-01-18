package com.ef.repository;

import com.ef.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    @Query(nativeQuery = true, value = "SELECT ip FROM (SELECT ip, COUNT(1) AS COUNT FROM access_log WHERE date BETWEEN :startDate AND :endDate GROUP BY ip HAVING COUNT > :threshold) log")
    Optional<List<String>> findByDateRangeAndCount(@Param("startDate") LocalDateTime dateTime, @Param("endDate") LocalDateTime localDateTime, @Param("threshold") Integer threshold);

    List<AccessLog> findByIp(String ip);
}
