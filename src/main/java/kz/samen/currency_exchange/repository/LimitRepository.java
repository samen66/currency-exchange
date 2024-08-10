package kz.samen.currency_exchange.repository;

import kz.samen.currency_exchange.domain.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Query("SELECT l FROM Limit l WHERE " +
            "l.status = :status AND " +
            "l.limitDatetime BETWEEN :start AND :end")
    Optional<Limit> findLimitByCreatedAtAndStatus(ZonedDateTime start, ZonedDateTime end, Limit.Status status);
}
