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
import adt.WhereClause;
import adt.OrderClause;
import entity.Song;
import entity.SongCategory;
import utility.ConsoleStyles;
import utility.NewScanner;

import java.util.Iterator;

public class SongMaintenance {
    private ListInterface<Song> songList;
    private ListInterface<SongCategory> categoryList;
    private final NewScanner scanner = new NewScanner();
    private final ConsoleStyles print = new ConsoleStyles();
    
    private int songIndex = 1011;
    private static final int TABLE_WIDTH = 40, LIST_TABLE_WIDTH = 97, SPECIFY_TABLE_WIDTH = 45, 
            MODIFY_REPORT_TABLE_WIDTH = 55, SUMMARY_TABLE_WIDTH = 60;
    
    private String newAddedSong = "";
    private String newEditedSong = "";
    private String newRemovedSong = "";
    
    
    public SongMaintenance(ListInterface<Song> songList, ListInterface<SongCategory> categoryList) {
        this.songList = songList;
        this.categoryList = categoryList;
        
    }
    
    public void main() {
        int desire;
        
        do {
            print.tableHeader("SONG MAINTENANCE", TABLE_WIDTH);
            System.out.println("1. Add Song");
            System.out.println("2. Remove Song");
            System.out.println("3. Edit Song's Details");
            System.out.println("4. Search For Song(s)");
            System.out.println("5. Sort Song List");
            System.out.println("6. Display All Song List");
            System.out.println("7. Display Song List By Category");
            System.out.println("8. Reports");
            System.out.println("9. Exit");
            desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 8", 1, 9);
            
            switch(desire) {
                case 1 : addSong(); break;
                case 2 : removeSong(); break;
                case 3 : editSong(); break;
                case 4 : searchSong(); break;
                case 5 : sortByList(); break;
                case 6 : showAllList(); break;
                case 7 : showSongList(); break;
                case 8 : showReports(); break;
                default : {
                }
            }
        } while(desire != 9);
    }
    
    public void addSong(){
        print.tableHeader("ADD SONG", TABLE_WIDTH);
        print.otherMsg("New Song Details",1);
        
        // generate song id
        String songID = "S" + String.format("%4d", songIndex++);
        System.out.println("Song ID              : " + songID);
        
        
        // Get songName
        String songName = scanner.nextLine("Enter Song Name      : ");
        
        // Get artist name
        String artist = validateName("Enter Artist Name    : ");
        
        // Get song publishedYear
        String publishedYear = validatePublishedYear("Enter Published Year : ");
        
         // Choose type
        print.otherMsg("Choose The Song Category", 0);
        displayCategoryList();
        int categoryNum = scanner.nextInt("Enter The Category   : ", String.format("Please enter a number between 1 to %d", categoryList.sizeOf()), 1, categoryList.sizeOf());
        String categoryName = categoryList.get(categoryNum - 1).getCategoryName();
        
        
        // Display result will be added
        print.tableHeader("New Song Details", SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-20s |\n", "Song ID", songID);
        System.out.printf("| %-18s | %-20s |\n", "Song Name ", songName);
        System.out.printf("| %-18s | %-20s |\n", "Artist", artist);
        System.out.printf("| %-18s | %-20s |\n", "Published Year", publishedYear);
        print.tableFooter(SPECIFY_TABLE_WIDTH);
  
        
        // Confirmation
        print.otherMsg("Please make sure that all the details given above is correct", 1);
        if (scanner.confirmation("Sure want to add song above (Y = yes / N = no)? >> ")) {
            
            songList.add(new Song(songID,songName,artist,publishedYear,categoryName));
            newAddedSong += String.format("| %5s | %20s | %20s |\n",  songID, songName, categoryName);
            
            print.success("The above song have been added to the list");
        } else {
            print.cancelled("Adding the above song has been cancelled");
        }
    }
    
  
    
    public void removeSong(){
         // Check
        if (songList.isEmpty()) {
            print.failed("No Songs In The List");
        } else {
            print.tableHeader("REMOVE SONG", LIST_TABLE_WIDTH);
            displayList(songList, categoryList);
            print.otherMsg(String.format("Total Number Of Song: %d", songList.sizeOf()), 0);
            
            // Get the song id
            String songId = scanner.nextLine("\nEnter The Song ID To Remove: ");
            Song toRemove = songList.firstOrDefault(s -> s.getSongID().equalsIgnoreCase(songId));
        
            // Compare the id from list
            if(toRemove == null) {
                print.failed("Song ID Not Found");
            } else {
                // Show the list that search by user
                displaySpecification(toRemove, categoryList);
                
                if(scanner.confirmation("Sure want to remove song above (Y = yes / N = no)? >> ")) {
                    songList.remove(toRemove);
                    newRemovedSong += String.format("| %5s | %20s | %20s |\n", toRemove.getSongID(), toRemove.getSongName(), toRemove.getCategory());
                    print.success("The above song have been removed to the list of songs");
                } else {
                    print.cancelled("The removal the above song has been cancelled");
                }
            }
        }
    }
    
    public void editSong(){
        if (songList.isEmpty()) {
            print.failed("No Songs In The List");
        } else {
            print.tableHeader("EDIT SONG", LIST_TABLE_WIDTH);
            displayList(songList, categoryList);
            print.otherMsg(String.format("Total Number Of Songs: %d", songList.sizeOf()), 0);
            
            String songId = scanner.nextLine("Enter The Song ID To Edit: ");
            Song toEdit = songList.firstOrDefault(s -> s.getSongID().equalsIgnoreCase(songId));
            
            if(toEdit == null) {
                print.failed("Song ID not found");
            } else {
                
                 print.tableHeader("SELECT FIELD TO EDIT", TABLE_WIDTH);
                System.out.println("1. Song Name");
                System.out.println("2. Artist");
                System.out.println("3. Published Year");
                System.out.println("4. Category");
                System.out.println("5. Back To Song Mainenance Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 5", 1, 5);
               
        switch(desire) {
            case 1 : //editSongName();
                String songName = scanner.nextLine("Enter New Song Name      : ");
                if(scanner.confirmation("Sure to edit song name above (Y = yes / N = no)? >> ")) {
                    toEdit.setSongName(songName);
                     print.success("The above song has been edited with new song name");
                } else {
                    print.cancelled("Edit the above song name has been cancelled");
                }
                
                break;
            case 2 : //editArtist(); 
                String artist = validateName("Enter Artist Name    : ");
                if(scanner.confirmation("Sure to edit artist name above (Y = yes / N = no)? >> ")) {
                    toEdit.setArtist(artist);
                     print.success("The above song has been edited with new artist name");
                } else {
                    print.cancelled("Edit the above song artist has been cancelled");
                }
                break;
            case 3 : //editPublishedYear(); 
                String publishedYear = validatePublishedYear("Enter Published Year : ");
                if(scanner.confirmation("Sure to edit published year above (Y = yes / N = no)? >> ")) {
                    toEdit.setPublishedYear(publishedYear);
                    print.success("The above song has been edited with new published year");
                } else {
                    print.cancelled("Edit the above published year has been cancelled");
                }
                break;
            case 4 : //editCategory();
                print.otherMsg("Choose The Song Category", 0);
                displayCategoryList();
                int categoryNum = scanner.nextInt("Enter The Category   : ", 
                        String.format("Please enter a number between 1 to %d", categoryList.sizeOf()), 1, categoryList.sizeOf());
                String categoryName = categoryList.get(categoryNum - 1).getCategoryName();
                SongCategory sc = categoryList.firstOrDefault(c -> c.getCategoryName().equalsIgnoreCase(categoryName));
                if(scanner.confirmation("Sure to edit song category above (Y = yes / N = no)? >> ")) {
                    toEdit.setCategory(categoryName);
                    print.success("The above song has been edited with new category");
                } else {
                    print.cancelled("Edit the above published year has been cancelled");
                }
                break;
            default : {
            }
            
        }
            }
        }
    }
    
    
    public void searchSong(){
        print.tableHeader("SEARCH SONG", TABLE_WIDTH);
        System.out.println("1. Search Song ID");
        System.out.println("2. Search Song Name");
        System.out.println("3. Search Artist");
        System.out.println("4. Search Published Year");
        System.out.println("5. Back To Song Mainenance Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 5", 1, 5);
        
        switch(desire) {
            case 1 : searchSongId(); break;
            case 2 : searchSongName(); break;
            case 3 : searchArtist(); break;
            case 4 : searchPublishedYear(); break;
            default : break;
        }
    }
    
    public void searchSongId(){
        print.tableHeader("SEARCH SONG ID", TABLE_WIDTH);
        String songId = scanner.nextLine("Enter song id to search (e.g. S1001): ");
        
        Song sg = songList.firstOrDefault(s -> s.getSongID().equalsIgnoreCase(songId));
    
        if(sg != null)
            displaySpecification(sg, categoryList);
        else
            print.failed("Song ID not found");
    }
    
    public void searchSongName() {
        print.tableHeader("SEARCH SONG NAME", TABLE_WIDTH);
        String songName = scanner.nextLine("Enter song name to search: ");
            
        Song sg = songList.firstOrDefault(s-> s.getSongName().equalsIgnoreCase(songName));
          
            
        if(sg != null)
            displaySpecification(sg,categoryList);
        else
               print.failed("Song name not found");
    }
    
    public void searchArtist(){
        print.tableHeader("SEARCH ARTIST", TABLE_WIDTH);
        String artist = scanner.nextLine("Enter artist to search: ");
            
        Song sg = songList.firstOrDefault(s -> s.getArtist().equalsIgnoreCase(artist));
          
            
        if(sg != null)
            displaySpecification(sg,categoryList);
        else
               print.failed("Artist not found");
    }
    
    public void searchPublishedYear(){
           print.tableHeader("SEARCH PUBLISHED YEAR", TABLE_WIDTH);
        int startRange = scanner.nextInt("Enter starting range: ");
        int endRange = scanner.nextInt("Enter ending range  : ");
        
        ListInterface<Song> sList = new DoublyLinkedList<>();
        
        if(startRange <= endRange) {
            sList = songList.where(s -> Integer.parseInt(s.getPublishedYear()) >= startRange && Integer.parseInt(s.getPublishedYear()) <= endRange);
        } else {
            System.out.println("\n< Out of range >");
        }
        
        if(sList.isEmpty())
            System.out.println("\n< Song not found >");
        else
            displaySpecification(sList);
    }
    
     public void sortByList() {
        print.tableHeader("SORT SONG LIST BY", TABLE_WIDTH);
        
        System.out.println("1. Sort By Song ID");
        System.out.println("2. Sort By Song Name");
        System.out.println("3. Sort By Artist Name");
        System.out.println("4. Sort By Published Year");
        System.out.println("5. Back To Song Mainenance Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 5", 1, 5);
        
        if (desire != 5) {
            print.otherMsg("Sort By List", 1);
            System.out.println("1. Ascending Order");
            System.out.println("2. Descending Order");
            System.out.println("3. Back To Song Mainenance Main Page");

            int sequenceType = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

            if (sequenceType != 3) {
                switch(desire) {
                    case 1 : sortById(sequenceType);break;
                    case 2 : sortBySongName(sequenceType);break;
                    case 3 : sortByArtist(sequenceType);break;
                    case 4 : sortByPublishedYear(sequenceType);break;
                    default :break; 
                }
                print.success("Sort successfully, press 6 or 7 to view song list");
            }
        }
    }
     public void sortById(int sequenceType) {
        switch(sequenceType) {
            case 1 : songList.orderBy((Song s1, Song s2) -> {
                    int val = s1.getSongID().compareToIgnoreCase(s2.getSongID());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            case 2 : songList.orderBy((Song s1, Song s2) -> {
                    int val = s1.getSongID().compareToIgnoreCase(s2.getSongID());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            default :break; 
            
        }
    }
     
    public void sortBySongName(int sequenceType) {
        switch(sequenceType) {
            case 1 : songList.orderBy((Song s1, Song s2) -> {
                    int val = s1.getSongName().compareToIgnoreCase(s2.getSongName());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            case 2 : songList.orderBy((Song s1, Song s2) -> {
                    int val = s1.getSongName().compareToIgnoreCase(s2.getSongName());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            default :break;
        }
    }     

    public void sortByArtist(int sequenceType) {
        switch(sequenceType) {
            case 1 : songList.orderBy((Song s1, Song s2) -> {
                    int val = s1.getArtist().compareToIgnoreCase(s2.getArtist());
                     
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            case 2 : songList.orderBy((Song s1, Song s2) -> {
                    int val = s1.getArtist().compareToIgnoreCase(s2.getArtist());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0  ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            default :break;
        }
    } 
        public void sortByPublishedYear(int sequenceType) {
        switch(sequenceType) {
            case 1 : songList.orderBy((Song s1, Song s2) -> {
                    int val = Integer.parseInt(s1.getPublishedYear())-Integer.parseInt(s2.getPublishedYear());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            case 2 : songList.orderBy((Song s1, Song s2) -> {
                    int val = Integer.parseInt(s1.getPublishedYear())-Integer.parseInt(s2.getPublishedYear());
                    
                    if (val == 0) {
                        return s1.getSongName().compareToIgnoreCase(s2.getSongName()) < 0  ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });break;
            default :break;
        }
    }
     
     public void showSongList() {
        Iterator<SongCategory> categoryItr = categoryList.getIterator();
        print.tableHeader("DISPLAY SONG LIST", TABLE_WIDTH);
        
        int backNum = 1;
        for (int i = 0; categoryItr.hasNext(); i++) {
            System.out.println(String.format("%d. Only Display %s Category", (i + 1), categoryItr.next().getCategoryName()));
            backNum++;
        }
        
        System.out.printf("%d. Back To Song Mainenance Main Page\n", backNum);
        
        int desire = scanner.nextInt("Enter Your Choice: ", String.format("Please enter a number between 1 to %d", backNum), 1, backNum);
        
        if (desire != backNum) {
            int numOf = 0;
            // Get category name
            String categoryName = categoryList.get(desire - 1).getCategoryName();
            
           
            print.tableHeader(String.format("SONG LIST (%s)", categoryName.toUpperCase()), LIST_TABLE_WIDTH);

            Iterator<Song> songItr = songList.getIterator();
            
            System.out.printf("| %-8s | %-20s | %-20s | %-20s | %-13s |\n", "ID", "SONG NAME", "ARTIST", "PUBLISHED YEAR", "CATEGORY");
            print.tableMiddleLine(LIST_TABLE_WIDTH);
            while (songItr.hasNext()) {
                Song s = songItr.next();
                if (categoryName.compareToIgnoreCase(s.getCategory()) == 0) {
                    System.out.println(s);
                    numOf++;
                }
            }   
            
            if (numOf == 0)
                print.toCenter(String.format("No Record Found In %s Category", categoryName), LIST_TABLE_WIDTH);

            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of %s Song: %d", categoryName, numOf), 0);
        }
    }
    

     
    public void showAllList(){
        print.tableHeader("ALL SONG LIST", LIST_TABLE_WIDTH);
        displayList(songList, categoryList);
        print.otherMsg(String.format("Total Number Of Song: %d", songList.sizeOf()), 0);
    }
    
    public void showReports(){
         Iterator<SongCategory> categoryItr = categoryList.getIterator();
        
        print.tableHeader("OVERALL SUMMARY REPORT", SUMMARY_TABLE_WIDTH);
        print.toCenter(String.format("Total of Song: %d", songList.sizeOf()), SUMMARY_TABLE_WIDTH);
        print.tableMiddleLine(SUMMARY_TABLE_WIDTH);
        
        System.out.println(String.format("| %25s | %28s |", "Category", "Total Number of Song"));
        print.tableMiddleLine(SUMMARY_TABLE_WIDTH);
        
        int totalSong;
        //print.otherMsg(String.format("Total Number Of %s Song: %d", categoryName, numOf), 0);
        while (categoryItr.hasNext()) {
            SongCategory sc = categoryItr.next();
           
            
            Iterator<Song> songItr = songList.getIterator();
            
            totalSong = 0;
            
            while (songItr.hasNext()) {
                Song s = songItr.next();

                
               if (sc.getCategoryName().equals(s.getCategory()))
                   totalSong ++;
               //     totalSong += s.getCategory();
            }
            System.out.println(String.format("| %25s | %28s |", sc.getCategoryName(), totalSong));
        }
        print.tableFooter(SUMMARY_TABLE_WIDTH);
    }
    
    public void displaySpecification(Song song, ListInterface<SongCategory> categoryList) {    // Search for 1 result
        Iterator<SongCategory> categoryItr = categoryList.getIterator();
        String categoryUnit = null;
        
     
        
        print.tableHeader("SONG SEARCH RESULT", SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-20s |\n", "Song ID", song.getSongID());
        System.out.printf("| %-18s | %-20s |\n", "Song Name ", song.getSongName());
        System.out.printf("| %-18s | %-20s |\n", "Artist", song.getArtist());
        System.out.printf("| %-18s | %-20s |\n", "Published Year", song.getPublishedYear());
        System.out.printf("| %-18s | %-20s |\n", "Song Category",song.getCategory());

        print.tableFooter(SPECIFY_TABLE_WIDTH);
    }
    
       public void displaySpecification(ListInterface<Song> song) {   // Search for more than 1 results
        print.tableHeader("SONG SEARCH RESULT", LIST_TABLE_WIDTH);
        displayList(song, categoryList);
    }
    
    
    public void displayCategoryList() {
        Iterator<SongCategory> categoryItr = categoryList.getIterator();
        
        for (int i = 0; categoryItr.hasNext(); i++) {
            System.out.println(String.format("%d. %s", (i + 1), categoryItr.next().getCategoryName()));
        }
    }
    
    public void displayList(ListInterface<Song> songList, ListInterface<SongCategory> categoryList) {
        Iterator<SongCategory> categoryItr = categoryList.getIterator();
        int numOf;
        
        while (categoryItr.hasNext()) {
            SongCategory sc = categoryItr.next();
            print.toCenter(sc.getCategoryName().toUpperCase(), LIST_TABLE_WIDTH);
            String categoryUnit = null;
            print.tableMiddleLine(LIST_TABLE_WIDTH);

            
            System.out.printf("| %-8s | %-20s | %-20s | %-20s | %-13s |\n", "ID", "SONG NAME", "ARTIST", "PUBLISHED YEAR", "CATEGORY");
            print.tableMiddleLine(LIST_TABLE_WIDTH);
        
            Iterator<Song> songItr = songList.getIterator();
            
            numOf = 0;
            
            while (songItr.hasNext()) {
                Song s = songItr.next();
                if (sc.getCategoryName().compareToIgnoreCase(s.getCategory()) == 0) {
                    System.out.println(s);
                    numOf++;
                }
            }
            
            if (numOf == 0)
                print.toCenter("No record for this category", LIST_TABLE_WIDTH);
            print.tableFooter(LIST_TABLE_WIDTH);
        }
    }
    
     // Validation of Artist Name
    public String validateName(String promptInfo) {
        boolean validName = false;//,  unicodeName = true;
        String mainErrorName = "Please enter a valid artist name. (e.g. Adele)";
        int numChar = 0;
        String artist;
        
       do {
            artist = scanner.nextLine(promptInfo);
    
            if (artist == "") {
                print.error(mainErrorName);
                print.error("Artist name cannot be empty");
            } else if (artist.length() >= 20) {
                print.error(mainErrorName);
                print.error("For record purposes, the artist’s name must not exceed 20 characters");
            } else
                validName = true;
            
        } while(!validName);
        
        return artist;
    }
        
     // Validation of Published Year
    public String validatePublishedYear(String promptInfo) {
        boolean validYear = false;
        String mainErrorYear = "Please enter a valid published year (e.g. not less than 1920 or more than current year: ";
        String publishedYear;

        
        do{
           publishedYear = scanner.nextLine(promptInfo);
           
           if((publishedYear == "")&&(publishedYear.matches("[0-9]+"))){
               print.error(mainErrorYear);
               print.error("Please enter numeric values only.");
         } else if(publishedYear.length() != 4){
             print.error(mainErrorYear);
             print.error("Published Year is not proper. Please check");
         }else if((Integer.parseInt(publishedYear) < 1920)||Integer.parseInt(publishedYear) > 2022){
               print.error(mainErrorYear);
               print.error("Published year should not less than 1920 or more than current year");
        }else
             validYear = true;
           
        }while(!validYear);
        
        return publishedYear;
    }
    
}
