package com.fujita.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fujita.test.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

	public Optional<LoginUser> findById(long name);

}
