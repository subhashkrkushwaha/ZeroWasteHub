package com.zerowastehub.backend.repository;

import com.zerowastehub.backend.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository <SubCategory,Integer>{
}
