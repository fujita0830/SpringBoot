package com.fujita.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fujita.springboot.entity.Contents;

public interface ContentsRepository extends JpaRepository<Contents, Long>{

	public  Iterable<Contents> findByAccountId(long accountid);
	public Iterable<Contents> findByReadStatus(String readStatus);

}
