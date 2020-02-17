package com.fasttrack.cvgt.transfer;

import java.util.Set;

public class MyGalleryResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}