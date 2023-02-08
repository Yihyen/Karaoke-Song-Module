/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import adt.*;
/**
 *
 * @author yen_y
 */
public class SongCategory {
    private String categoryID;
    private String categoryName;
    
    private ListInterface<Song> songList = new DoublyLinkedList<>();

    public SongCategory(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSongList(ListInterface<Song> songList){
        this.songList = songList;
    }
    
    @Override
    public String toString() {
        return "| " + String.format("%-18s",categoryID)  + " | " + String.format("%-51s",categoryName) + " |" ;
    }
    
    
     @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final SongCategory other = (SongCategory) obj;
        if (!Objects.equals(this.categoryID, other.categoryID))
            return false;
        return true;
    }
    
    
    public void addSong(Song song) {
        songList.add(song);
    }
    
    public void removeSong(Song song) {
        songList.remove(song);
    }
    
    public Song getSong(String songId) {
        for (int i = 0; i < songList.sizeOf(); i++) {
            if (songList.get(i).getSongID().equals(songId))
                return songList.get(i);
        }
        return null;
    }
    
    
}
