package com.fasttrack.cvgt;

import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.exception.ResourceNotFoundException;
import com.fasttrack.cvgt.service.MediaService;
import com.fasttrack.cvgt.steps.MediaSteps;
import com.fasttrack.cvgt.transfer.SaveMediaRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaServiceIntegrationTests {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaSteps mediaSteps;

    @Test
    public void testCreateMedia_whenValidRequest_thenMediaIsSaved() {
        mediaSteps.createMedia();
    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateMedia_whenInvalidRequest_thenThrowException() {
        SaveMediaRequest request = new SaveMediaRequest();

        mediaService.createMedia(request);
    }

    @Test
    public void testGetMedia_whenExistingMedia_thenReturnMedia() {
        Media createdMedia = mediaSteps.createMedia();

        Media retrievedMedia = mediaService.getMedia(createdMedia.getId());

        assertThat(retrievedMedia, notNullValue());
        assertThat(retrievedMedia.getId(), is(createdMedia.getId()));
        assertThat(retrievedMedia.getName(), is(createdMedia.getName()));
        assertThat(retrievedMedia.getDescription(), is(createdMedia.getDescription()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetMedia_whenNotExistingMedia_thenThrowResourceNotFound() {
        mediaService.getMedia(0);
    }

    @Test
    public void testUpdateMedia_whenValidRequest_thenReturnUpdatedMedia() {
        Media createdMedia = mediaSteps.createMedia();

        SaveMediaRequest request = new SaveMediaRequest();
        request.setName(createdMedia.getName() + " updated");
        request.setDescription(createdMedia.getDescription() + " updated");
        request.setImageUrl(createdMedia.getDescription() + "updated");

        Media updatedMedia = mediaService.updateMedia(createdMedia.getId(), request);

        assertThat(updatedMedia, notNullValue());
        assertThat(updatedMedia.getId(), is(createdMedia.getId()));
        assertThat(updatedMedia.getName(), is(request.getName()));
        assertThat(updatedMedia.getDescription(), is(request.getDescription()));
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteMedia_whenExistingMedia_thenMediaIsDeleted() {
        Media media = mediaSteps.createMedia();

        mediaService.deleteMedia(media.getId());

        mediaService.getMedia(media.getId());

    }
}
