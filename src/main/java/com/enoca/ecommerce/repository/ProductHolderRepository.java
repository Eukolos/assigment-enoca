package com.enoca.ecommerce.repository;

import com.enoca.ecommerce.entity.ProductHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductHolderRepository extends JpaRepository<ProductHolder, Long> {

    @Transactional
    @Modifying
    @Query("update ProductHolder c set c.deleted = true where c.id = :id")
    void softDeleteById(Long id);
}
