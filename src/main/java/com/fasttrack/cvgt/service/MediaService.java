package com.fasttrack.cvgt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.exception.ResourceNotFoundException;
import com.fasttrack.cvgt.persistance.MediaRepository;
import com.fasttrack.cvgt.transfer.SaveMediaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MediaService.class);

    private final MediaRepository mediaRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MediaService(MediaRepository mediaRepository, ObjectMapper objectMapper) {
        this.mediaRepository = mediaRepository;
        this.objectMapper = objectMapper;
    }

    public Media createMedia(SaveMediaRequest request) {
        LOGGER.info("Creating media {} ", request);
        Media media = objectMapper.convertValue(request, Media.class);

        return mediaRepository.save(media);
    }

    public Media getMedia(long id) {
        LOGGER.info("Retrieving media {}", id);

        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media " + id + " does not exist."));
    }

    public Media updateMedia(long id, SaveMediaRequest request) {
        LOGGER.info("Updating media {}: {}", id, request);

        Media media = getMedia(id);

        BeanUtils.copyProperties(request, media);

        return mediaRepository.save(media);
    }

    public void deleteMedia(long id) {
        LOGGER.info("Deleting media {}", id);
        mediaRepository.deleteById(id);
        LOGGER.info("Deleted media {}", id);
    }
}