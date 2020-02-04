package com.fasttrack.cvgt.steps;

import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.service.MediaService;
import com.fasttrack.cvgt.transfer.SaveMediaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;

@Component
public class MediaSteps {

    @Autowired
    private MediaService mediaService;

    public Media createMedia() {
        SaveMediaRequest request = new SaveMediaRequest();
        request.setName("Pic" + System.currentTimeMillis());
        request.setDescription("Nature Trees");
        request.setImageUrl("url");

        Media createdMedia = mediaService.createMedia(request);

        assertThat(createdMedia, notNullValue());
        assertThat(createdMedia.getId(), greaterThan(0L));
        assertThat(createdMedia.getName(), is(request.getName()));
        assertThat(createdMedia.getDescription(), is(request.getDescription()));

        return createdMedia;
    }

}
