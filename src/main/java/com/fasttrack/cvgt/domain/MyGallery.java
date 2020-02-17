package com.fasttrack.cvgt.domain;

import javax.persistence.*;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MyGallery {

    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "myGallery_media",
            joinColumns = @JoinColumn(name = "myGallery_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> medias = new HashSet<>();

    public void addToMyGallery(Media media) {
        medias.add(media);
        media.getMyGalleries().add(this);
    }

    public void removeFromMyGallery(Media media) {
        medias.remove(media);
        media.getMyGalleries().remove(this);
    }

    public void runComputerVision(String imageURL) throws Exception {
        String fileName = "C:\\Users\\Florin Jurchis\\Desktop\\Training\\JAVA\\cvgt-py-side\\runPy.bat";
        FileWriter writer = new FileWriter(fileName);
        writer.write("set VAR_1=imageURL\n");
        writer.write("c:\n");
        writer.write("call \"C:\\Users\\Florin Jurchis\\Anaconda3\\Scripts\\\"activate base\n");
        writer.write("python \"C:\\Users\\Florin Jurchis\\Desktop\\Training\\JAVA\\cvgt-py-side\\opencv-semantic-segmentation\\segment.py\" %VAR_1%\n");
        writer.write("conda deactivate");
        writer.close();

        String[] commands = {"cmd", "/c", "start", "\"cvgt-web-app\"",fileName};
        Runtime.getRuntime().exec(commands);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return "MyGallery{" +
                "id=" + id +
                ", user=" + user +
                ", medias=" + medias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyGallery myGallery = (MyGallery) o;

        return id.equals(myGallery.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}