package com.example.podcast.Activity;

public class Episode {

    private String titleEpisode;
    private int File;

    public Episode(String titleEpisode, int file) {
        this.titleEpisode = titleEpisode;
        this.File = file;
    }

    public String getTitleEpisode() {
        return titleEpisode;
    }


    public int getFile() {
        return File;
    }

    public void setTitleEpisode(String titleEpisode) {
        this.titleEpisode = titleEpisode;
    }

    public void setFile(int file) {
        File = file;
    }
}
