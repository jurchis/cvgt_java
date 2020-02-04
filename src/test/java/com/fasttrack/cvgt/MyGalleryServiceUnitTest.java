package com.fasttrack.cvgt;

import com.fasttrack.cvgt.domain.Media;
import com.fasttrack.cvgt.domain.MyGallery;
import com.fasttrack.cvgt.domain.User;
import com.fasttrack.cvgt.persistance.MyGalleryRepository;
import com.fasttrack.cvgt.service.MediaService;
import com.fasttrack.cvgt.service.MyGalleryService;
import com.fasttrack.cvgt.service.UserService;
import com.fasttrack.cvgt.transfer.AddMediaToMyGalleryRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyGalleryServiceUnitTest {

    private MyGalleryService myGalleryService;

    @Mock
    private MyGalleryRepository myGalleryRepository;

    @Mock
    private UserService userService;

    @Mock
    private MediaService mediaService;

    @Before
    public void setup() {
        myGalleryService = new MyGalleryService(myGalleryRepository, userService, mediaService);

    }

    @Test
    public void testAddMediaToMyGallery_whenNewMyGallery_thenThrowNoError() {
        when(myGalleryRepository.findById(anyLong())).thenReturn(Optional.empty());

        User user = new User();
        user.setId(1L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");

        when(userService.getUser(anyLong())).thenReturn(user);


        Media media = new Media();
        media.setId(2L);
        media.setName("Media");

        when(mediaService.getMedia(anyLong())).thenReturn(media);

        when(myGalleryRepository.save(any(MyGallery.class))).thenReturn(null);

        AddMediaToMyGalleryRequest request = new AddMediaToMyGalleryRequest();
        request.setMediaId(2L);
        request.setUserId(1L);

        myGalleryService.addMediaToMyGallery(request);

        verify(myGalleryRepository).findById(anyLong());
        verify(myGalleryRepository).save(any(MyGallery.class));
        verify(userService).getUser(anyLong());
        verify(mediaService).getMedia(anyLong());

    }
}
