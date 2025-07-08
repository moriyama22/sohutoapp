package com.example.demo.Repository;

import com.example.demo.Entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {
    Optional<SessionEntity> findBySessionId(String sessionId); // セッションIDで検索
}