package com.invop.iobackend.repository;

import com.invop.iobackend.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Integer> {
    @Query(value = "SELECT IFNULL(SUM(lot), 0) as total " +
            "FROM details",
            nativeQuery = true)
    Integer totalSold();

    @Query(value = "SELECT IFNULL(SUM(lot), 0) as total " +
            "FROM details " +
            "WHERE product_id = :idProduct",
            nativeQuery = true)
    Integer totalSoldByProduct(@Param("idProduct") Integer idProduct);
}
