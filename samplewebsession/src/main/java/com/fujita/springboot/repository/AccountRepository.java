package com.fujita.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fujita.springboot.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	public Optional<Account> findById(long name);

	public boolean existsByLoginId(String loginId);

	public Optional<Account>findByLoginIdAndLoginPassword(String loginId,String loginPassword);

	public boolean existsByLoginIdAndLoginPassword(String loginID,String loginPassword);



}
