package com.fasttrack.cvgt.web;

import com.fasttrack.cvgt.domain.MyGallery;
import com.fasttrack.cvgt.service.MyGalleryService;
import com.fasttrack.cvgt.transfer.AddMediaToMyGalleryRequest;
import com.fasttrack.cvgt.transfer.DeleteMediaFromMyGalleryRequest;
import com.fasttrack.cvgt.transfer.MyGalleryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value="/my-gallery")
public class MyGalleryController {
    private final MyGalleryService myGalleryService;

    @Autowired
    public MyGalleryController(MyGalleryService myGalleryService) {
        this.myGalleryService = myGalleryService;
    }

    @PutMapping()
    public ResponseEntity addMediaToMyGallery(@RequestBody @Valid AddMediaToMyGalleryRequest request) {
        myGalleryService.addMediaToMyGallery(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity deleteMediaFromMyGallery(@RequestBody @Valid DeleteMediaFromMyGalleryRequest request) {
        myGalleryService.deleteMediaFromMyGallery(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyGalleryResponse> getMyGallery(@PathVariable("id") long id) {
        MyGalleryResponse myGallery = myGalleryService.getMyGallery(id);
        return new ResponseEntity<>(myGallery, HttpStatus.OK);
    }

    @PostMapping
    public void createMedia(String imageURL) {
        MyGallery galleryItem = new MyGallery();
        try {
            galleryItem.runComputerVision(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}