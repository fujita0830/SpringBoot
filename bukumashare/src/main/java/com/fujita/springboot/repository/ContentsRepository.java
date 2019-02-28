package com.fujita.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fujita.springboot.entity.Contents;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

	public Iterable<Contents> findByAccountIdOrderByInsertDateDesc(long accountid);

	public Iterable<Contents> findByshareStatusOrderByInsertDateDesc(String shareStatus);

	public Iterable<Contents> findByAccountIdAndReadStatusOrderByInsertDateDesc(long accountId, String readStatus);

	public Contents findByContentsId(long contentsID);

}
