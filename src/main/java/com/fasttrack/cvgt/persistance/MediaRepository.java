package com.fasttrack.cvgt.persistance;

import com.fasttrack.cvgt.domain.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Long wrapper class
public interface MediaRepository extends JpaRepository<Media, Long> {

    Page<Media> findByNameContaining(String partialName, Pageable pageable);

    @Query(value = "SELECT * FROM media", nativeQuery = true)
    Page<Media> findByPartialName(String partialName, Pageable pageable);

}
