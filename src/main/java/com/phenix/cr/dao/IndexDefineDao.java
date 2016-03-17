package com.phenix.cr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phenix.cr.model.IndexDefine;

@Repository
public interface IndexDefineDao extends JpaRepository<IndexDefine, Long> {
	@Query("from IndexDefine where name = :name")
	IndexDefine findByName(@Param("name") String name);
}
