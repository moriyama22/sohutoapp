package com.example.demo.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findBySeq(Long seq);
    AccountEntity findById(String id);
    AccountEntity findByPassword(String password);
    AccountEntity findByName(String name);
    AccountEntity findByNewdate(LocalDateTime newdate);           // 修正
    AccountEntity findByNewdateuser(String newdateuser);         // 修正
    AccountEntity findByUpdate(LocalDateTime update);            // 修正
    AccountEntity findByUpdateuser(String updateuser);           // 修正
    AccountEntity findByAdflag(String adflag);                   // 修正（A→a）

    AccountEntity findByIdAndPassword(String id, String password);

    boolean existsById(String id);
}
