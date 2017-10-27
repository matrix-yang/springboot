package com.yang.biz.repository;

import com.yang.biz.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
}
