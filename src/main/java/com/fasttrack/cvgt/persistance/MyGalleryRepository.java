package com.fasttrack.cvgt.persistance;

import com.fasttrack.cvgt.domain.MyGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyGalleryRepository extends JpaRepository<MyGallery, Long> {

}
