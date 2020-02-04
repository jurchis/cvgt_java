package com.fasttrack.cvgt.web;

import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.service.MediaService;
import com.fasttrack.cvgt.transfer.SaveMediaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value="/medias")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public ResponseEntity<Media> createMedia(@RequestBody @Valid SaveMediaRequest request) {
        Media media = mediaService.createMedia(request);
        return new ResponseEntity<>(media, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMedia(@PathVariable("id") Long id) {
        Media media = mediaService.getMedia(id);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMedia(
            @PathVariable("id") Long id, @RequestBody SaveMediaRequest request) {
        Media media = mediaService.updateMedia(id, request);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMedia(@PathVariable("id") Long id) {
        mediaService.deleteMedia(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}