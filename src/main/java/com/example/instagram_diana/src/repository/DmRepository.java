package com.example.instagram_diana.src.repository;

import com.example.instagram_diana.src.model.Block;
import com.example.instagram_diana.src.model.Dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DmRepository extends JpaRepository<Dm,Long> {

    @Modifying
    @Query(value = "DELETE FROM DM WHERE dmId = :messageId",nativeQuery = true)
    void dmDelete(long messageId);
}
