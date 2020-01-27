package com.fasttrack.cvgt.service;

import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.persistance.MediaRepository;
import com.fasttrack.cvgt.transfer.SaveMediaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//The Business Layer in Service
@Service
public class MediaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Media.class);

    //IoC
    private final MediaRepository mediaRepository;

    //Dependency injection
    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Media createMedia(SaveMediaRequest request) {
        LOGGER.info("Creating product {}", request);
        Media media = new Media();
        media.setDescription(request.getDescription());
        media.setMediaUrl(request.getMediaUrl());
        media.setName(request.getName());

        return mediaRepository.save(media);
    }
}
