package com.fasttrack.cvgt;

import com.fasttrack.cvgt.domain.User;
import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.service.MyGalleryService;
import com.fasttrack.cvgt.steps.UserSteps;
import com.fasttrack.cvgt.steps.MediaSteps;
import com.fasttrack.cvgt.transfer.AddMediaToMyGalleryRequest;
import com.fasttrack.cvgt.transfer.MyGalleryResponse;
import com.fasttrack.cvgt.transfer.MediaInMyGalleryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyGalleryServiceIntegrationTests {

    @Autowired
    private MyGalleryService myGalleryService;

    @Autowired
    private UserSteps userSteps;

    @Autowired
    private MediaSteps mediaSteps;

    @Test
    public void testAddMediaToMyGallery_whenNewMyGalleryForExistingUser_thenMyGalleryIsSaved() {
        User user = userSteps.createUser();
        Media media = mediaSteps.createMedia();

        AddMediaToMyGalleryRequest request = new AddMediaToMyGalleryRequest();
        request.setUserId(user.getId());
        request.setMediaId(media.getId());

        myGalleryService.addMediaToMyGallery(request);

        MyGalleryResponse myGallery = myGalleryService.getMyGallery(user.getId());

        assertThat(myGallery.getId(), is(user.getId()));

        Iterator<MediaInMyGalleryResponse> iterator = myGallery.getMedias().iterator();

        assertThat(iterator.hasNext(), is(true));

        MediaInMyGalleryResponse mediaFromMyGallery = iterator.next();

        assertThat(mediaFromMyGallery, notNullValue());
        assertThat(mediaFromMyGallery.getId(), is(media.getId()));
        assertThat(mediaFromMyGallery.getName(), is(media.getName()));

    }
}