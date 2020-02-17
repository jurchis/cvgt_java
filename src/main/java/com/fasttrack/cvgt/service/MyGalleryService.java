package com.fasttrack.cvgt.service;

import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.domain.MyGallery;
import com.fasttrack.cvgt.domain.User;
import com.fasttrack.cvgt.exception.ResourceNotFoundException;
import com.fasttrack.cvgt.persistance.MyGalleryRepository;
import com.fasttrack.cvgt.transfer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class MyGalleryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyGalleryService.class);

    private final MyGalleryRepository myGalleryRepository;
    private final UserService userService;
    private final MediaService mediaService;

    @Autowired
    public MyGalleryService(MyGalleryRepository myGalleryRepository, UserService userService, MediaService mediaService) {
        this.myGalleryRepository = myGalleryRepository;
        this.userService = userService;
        this.mediaService = mediaService;
    }

    @Transactional
    public void addMediaToMyGallery(AddMediaToMyGalleryRequest request) {
        LOGGER.info("Adding media to myGallery: {}", request);

        MyGallery myGallery = myGalleryRepository.findById(request.getUserId())
                .orElse(new MyGallery());

        if (myGallery.getUser() == null) {
            LOGGER.info("New myGallery will be created. " +
                            "Retrieving user {} to map the relationship",
                    request.getUserId());

            User user =
                    userService.getUser(request.getUserId());

            myGallery.setId(user.getId());
            myGallery.setUser(user);
        }

        Media media = mediaService.getMedia(request.getMediaId());
        myGallery.addToMyGallery(media);

        myGalleryRepository.save(myGallery);
    }

    @Transactional
//    Python Integration
    public PyCvRequest runComputerVision(@Valid PyCvRequest request) throws Exception {

        String imageURL=request.getImageUrl();

        String fileName = "C:\\Users\\Florin Jurchis\\Desktop\\Training\\JAVA\\cvgt-py-side\\runPy.bat";
        FileWriter writer = new FileWriter(fileName);
        writer.write("set VAR_1="+imageURL+"\n");
        writer.write("c:\n");
        writer.write("call \"C:\\Users\\Florin Jurchis\\Anaconda3\\Scripts\\\"activate base\n");
        writer.write("python \"C:\\Users\\Florin Jurchis\\Desktop\\Training\\JAVA\\cvgt-py-side\\opencv-semantic-segmentation\\segment.py\" %VAR_1%\n");
        writer.write("conda deactivate\n");
        writer.write("exit /b 0");
        writer.close();

        String[] commands = {"cmd", "/c", "start", "\"cvgt-web-app\"",fileName};
        Runtime.getRuntime().exec(commands);
        return null;
    }

    @Transactional
    public void deleteMediaFromMyGallery(DeleteMediaFromMyGalleryRequest request) {
        LOGGER.info("Deleting media from myGallery: {}", request);

        MyGallery myGallery = myGalleryRepository.findById(request.getUserId())
                .orElse(new MyGallery());

        Media media = mediaService.getMedia(request.getMediaId());

        myGallery.removeFromMyGallery(media);

        myGalleryRepository.save(myGallery);
    }

    @Transactional
    public MyGalleryResponse getMyGallery(long id) {
        LOGGER.info("Retrieving myGallery {}", id);

        MyGallery myGallery = myGalleryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "MyGallery " + id + " does not exist."));

        MyGalleryResponse response = new MyGalleryResponse();
        response.setId(myGallery.getId());

        Set<MediaInMyGalleryResponse> mediasInMyGallery = new HashSet<>();

        Iterator<Media> myGalleryIterator = myGallery.getMedias().iterator();

        while (myGalleryIterator.hasNext()) {
            Media media = myGalleryIterator.next();

            MediaInMyGalleryResponse mediaResponse = new MediaInMyGalleryResponse();
            mediaResponse.setId(media.getId());
            mediaResponse.setName(media.getName());
            mediaResponse.setImageUrl(media.getImageUrl());
            mediaResponse.setDescription(media.getDescription());

            mediasInMyGallery.add(mediaResponse);
        }

        response.setMedias(mediasInMyGallery);
        return response;
    }
}