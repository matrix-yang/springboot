package com.yang.biz.repository;

import com.yang.biz.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<HouseEntity,Long> {
}
