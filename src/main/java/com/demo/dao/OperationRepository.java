package com.demo.dao;

import com.demo.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select o from Operation o order by o.dateOperation desc")
    public Page<Operation> operations(Pageable pageable);

    @Query("select o from Operation o where o.compte.codeCompte=:x order by o.dateOperation desc")
    public Page<Operation> operations(@Param("x") String codeCompte, Pageable pageable);    // N'existe pas dans springData. Pageable permet d'avoir la pagination

}
