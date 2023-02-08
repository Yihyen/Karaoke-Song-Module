/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author yen_y
 */
import adt.ListInterface;
import adt.DoublyLinkedList;

import entity.Song;
import entity.SongCategory;

import utility.NewScanner;
import utility.ConsoleStyles;

public class Karaoke {
    private ListInterface<Song> songList;
    private ListInterface<SongCategory> categoryList;
    
    private SongMaintenance songModule;
    private SongCategoryMenu songCategory;
    
    private final NewScanner scanner = new NewScanner();
    private final ConsoleStyles print = new ConsoleStyles();
    
    public Karaoke(){
        songList = new DoublyLinkedList();
        addSong();
        
        categoryList = new DoublyLinkedList();
        addSongCategory();
        
        songModule = new SongMaintenance(songList, categoryList);
        songCategory = new SongCategoryMenu(categoryList);
        
    }
    
    public void addSong(){
        Song s1 = new Song("S1001","I Am The Best","2NE1","2011","Korean");
        Song s2 = new Song("S1002","Fantastic Baby","Big Bang","2012","Korean");
        Song s3 = new Song("S1003","Rolling In The Deep","Adele","2010","English");
        Song s4 = new Song("S1004","Formation","Beyonce","2016","English");
        Song s5 = new Song("S1005","Tian Mi Mi","Teresa Tang","1970","Chinese");
        Song s6 = new Song("S1006","A Little Luck","Hebe Tian","2015","Chinese");
        Song s7 = new Song("S1007","You Exist in My Song","Qu Wan Ting","2012","Chinese");
        Song s8 = new Song("S1008","Shake it Off","Taylor Swift","2014","English");
        Song s9 = new Song("S1009","Y.M.C.A","Village People","1978","English");
        Song s10 = new Song("S1010","Kill This Love","Black Pink","2019","Korean");
        
        songList.addAll(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
    }
    
    public void addSongCategory(){
        categoryList.add(new SongCategory("C1001", "Chinese"));
        categoryList.add(new SongCategory("C1002", "English"));
        categoryList.add(new SongCategory("C1003", "Korean"));
    }
    
     public void run() {
        int desire;
        do {
            print.tableHeader("KARAOKE SYSTEM", 50);
            
            System.out.println("1. Song Category");
            System.out.println("2. Song Maintenance");
            System.out.println("3. Exit");
            desire = scanner.nextInt("Enter Your Choice: ", "Please select valid menu choice", 1, 3);
        
            switch(desire) {
                case 1 : songCategory.main(); break;
                case 2 : songModule.main(); break;
                default : break;
            }
        
        } while(desire != 3);
        
        print.otherMsg("Thank you for using our system", 1);
    }
    
    public static void main(String[] args) {
        new Karaoke().run();
    }
    
    public static String getDivider(char symbol, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}

