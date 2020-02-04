package com.fasttrack.cvgt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Media {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String description;
    @NotNull
    private String name;
    @NotNull
    private String imageUrl;

    @ManyToMany(mappedBy = "medias")
    private Set<MyGallery> myGalleries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<MyGallery> getMyGalleries() {
        return myGalleries;
    }

    public void setMyGalleries(Set<MyGallery> myGalleries) {
        this.myGalleries = myGalleries;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", myGalleries=" + myGalleries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Media media = (Media) o;

        return id.equals(media.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
