package com.fasttrack.cvgt.transfer;

import java.util.Set;

public class MyGalleryResponse {

    private Long id;
    private Set<MediaInMyGalleryResponse> medias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MediaInMyGalleryResponse> getMedias() {
        return medias;
    }

    public void setMedias(Set<MediaInMyGalleryResponse> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return "MyGalleryResponse{" +
                "id=" + id +
                ", medias=" + medias +
                '}';
    }
}