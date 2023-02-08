/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
/**
 *
 * @author yen_y
 */
public class Song {
    private String songID;
    private String songName;
    private String artist;
    private String publishedYear;
    private String category;

    public Song(String songID, String songName, String artist, String publishedYear,String category) {
        this.songID = songID;
        this.songName = songName;
        this.artist = artist;
        this.publishedYear = publishedYear;
        this.category = category;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPublishedYear() {
        return publishedYear;
    }


    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    @Override
    public String toString() {
        return "| " + String.format("%-8s", songID) 
            + " | " + String.format("%-20s",songName) 
            + " | " + String.format("%-20s", artist) 
            + " | " + String.format("%-20s", publishedYear) 
            + " | " + String.format("%-13s", category) + " |";
    }
    
        @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final Song other = (Song) obj;
        if (!Objects.equals(this.songID, other.songID))
            return false;
        return true;
    }
         
}
