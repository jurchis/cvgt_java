package com.fasttrack.cvgt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.exception.ResourceNotFoundException;
import com.fasttrack.cvgt.persistance.MediaRepository;
import com.fasttrack.cvgt.transfer.GetMediasRequest;
import com.fasttrack.cvgt.transfer.MediaResponse;
import com.fasttrack.cvgt.transfer.SaveMediaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public Page<MediaResponse> getMedias(GetMediasRequest request, Pageable pageable) {
        LOGGER.info("Retrieving medias: {}", request);

        Page<Media> medias;

        if (request != null && request.getPartialName() != null) {
            medias = mediaRepository.findByNameContaining(
                    request.getPartialName(), pageable);
        } else if (request != null && request.getPartialName() != null) {
            medias = mediaRepository.findByNameContaining(
                    request.getPartialName(), pageable);
        } else {
            medias = mediaRepository.findAll(pageable);
        }

        List<MediaResponse> mediaResponses = new ArrayList<>();

        for (Media media : medias.getContent()) {
            MediaResponse mediaResponse = new MediaResponse();
            mediaResponse.setId(media.getId());
            mediaResponse.setName(media.getName());
            mediaResponse.setDescription(media.getDescription());
            mediaResponse.setImageUrl(media.getImageUrl());

            mediaResponses.add(mediaResponse);
        }

        return new PageImpl<>(
                mediaResponses, pageable, medias.getTotalElements());
    }
}