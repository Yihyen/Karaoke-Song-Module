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
import adt.DoublyLinkedList;
import adt.ListInterface;
import adt.OrderClause;
import entity.Song;
import entity.SongCategory;
import utility.ConsoleStyles;
import utility.NewScanner;

import java.util.Iterator;

public class SongCategoryMenu {
    private ListInterface<SongCategory> categoryList;
    private final NewScanner scanner = new NewScanner();
    private final ConsoleStyles print = new ConsoleStyles();
    
    private int categoryIndex = 1004;
    private static final int TABLE_WIDTH = 40, LIST_TABLE_WIDTH = 76, SPECIFY_TABLE_WIDTH = 50;
    
    public SongCategoryMenu(ListInterface<SongCategory> categoryList) {
        this.categoryList = categoryList;
    }
    
     public void main() {
        int desire;
        
        do {
            print.tableHeader("SONG CATEGORY", TABLE_WIDTH);
            System.out.println("1. Add Category");
            System.out.println("2. Remove Category");
            System.out.println("3. Edit Category's details");
            System.out.println("4. Search Category");
            System.out.println("5. Sort Category List");
            System.out.println("6. Display Category List");
            System.out.println("7. Exit");
            desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 7", 1, 7);
            
            switch(desire) {
                case 1 : 
                    addCategory();
                    break;
                case 2 : 
                    removeCategory();
                    break;
                case 3 : 
                    editCategory();
                    break;
                case 4 : 
                    searchCategory();
                    break;
                case 5 : 
                    sortByList();
                    break;
                case 6:
                    showCategoryList();
                    break;
                default : break;
            }
            
        } while (desire != 7);
    }
     
     public void addCategory(){
        print.tableHeader("ADD CATEGORY", TABLE_WIDTH);
        print.otherMsg("New Category Details", 1);
        
        // Get category Id
        String categoryId = "C" + String.format("%4d", categoryIndex++);
        System.out.println("Category ID         : " + categoryId);
        
        // Get category name
        String categoryName = scanner.nextLine("Enter Category Name : ");
        
        

        // Show the details
        print.tableHeader("New Category Details", SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-25s |\n", "Category ID", categoryId);
        System.out.printf("| %-18s | %-25s |\n", "Category Name", categoryName);
        print.tableFooter(SPECIFY_TABLE_WIDTH);
        
        // Confirmation
        print.otherMsg("Please make sure that all the details given above is correct", 1);        
        print.hint("The above details can be edited in the future");

        if (scanner.confirmation("Sure want to add category above (Y = yes / N = no)? >> ")) {
            categoryList.add(new SongCategory(categoryId, categoryName));
            print.success("The above category have been added to the list");
        } else {
            print.cancelled("Adding the above category has been cancelled");
        }
     }
     
     public void removeCategory(){
           // Check whether is empty
        if (categoryList.isEmpty()) {
            print.failed("No Category In The List");
        } else {
            // Show all category in the list
            print.tableHeader("REMOVE CATEGORY", LIST_TABLE_WIDTH);
            displayList(categoryList);
            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of Category: %d", categoryList.sizeOf()), 0);
            
            // Get the category id
            String categoryId = scanner.nextLine("\nEnter The Category ID To Remove (e.g. C1001): ");
            SongCategory toRemove = categoryList.firstOrDefault(s -> s.getCategoryID().equalsIgnoreCase(categoryId));
        
            // Compare the id from list
            if(toRemove == null) {
                print.failed("Category ID Not Found");
            } else {
                // Show the list that search by user
                displaySpecification("category to remove", toRemove);
                
                if(scanner.confirmation("Sure want to remove category above (Y = yes / N = no)? >> ")) {
                    categoryList.remove(toRemove);
                    print.success("The above category have been removed to the list of category");
                } else {
                    print.cancelled("The removal the above category has been cancelled");
                }
            }
        }
     }
     
     public void editCategory(){
          // Check whether is empty
        if (categoryList.isEmpty()) {
            print.failed("No Category In The List");
        } else {
            // Show all category in the list
            print.tableHeader("EDIT CATEGORY", LIST_TABLE_WIDTH);
            displayList(categoryList);
            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of Category: %d", categoryList.sizeOf()), 0);

            // Get the category id
            String categoryId = scanner.nextLine("\nEnter The Category ID To Edit (e.g. C1001): ");
            SongCategory toEdit = categoryList.firstOrDefault(s -> s.getCategoryID().equalsIgnoreCase(categoryId));
            
            // Get the category id
            if (toEdit == null) {
                print.failed("Category ID Not Found");
            } else {
                displaySpecification("Category to edit", toEdit);
                // Enter new name
                String categoryName = scanner.nextLine("Enter New Category Name      : ");
          
                if(scanner.confirmation("Sure want to edit category above (Y = yes / N = no)? >> ")) {
                    // Set the details into the list
                    toEdit.setCategoryName(categoryName);
                    
                    print.success("The above category have been edited with the new details");
                } else {
                    print.cancelled("Edit the above category has been cancelled");
                }
            }
        }
     }
     
     public void searchCategory(){
        // Choose search by
        print.tableHeader("SEARCH CATEGORY", TABLE_WIDTH);
        System.out.println("1. Search By Category ID");
        System.out.println("2. Search By Category Name");
        System.out.println("3. Back To Song Category Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);
        
        switch(desire) {
            case 1 : searchCategoryId(); break;
            case 2 : searchCategoryName(); break;          
            default : break;
        }
     }
     
         
    public void searchCategoryId() {
        print.tableHeader("SEARCH CATEGORY ID", TABLE_WIDTH);
        String categoryId = scanner.nextLine("Enter category id to search (e.g. C1001): ");
        
        // Check id
        SongCategory sc = categoryList.firstOrDefault(s -> s.getCategoryID().equalsIgnoreCase(categoryId));
    
        // Show result
        if(sc != null)
            displaySpecification("category search result", sc);
        else
            print.failed("Category ID not found");
    }
    
    public void searchCategoryName() {
        print.tableHeader("SEARCH CATEGORY NAME", TABLE_WIDTH);
        System.out.println("1. Search Name Starts With");
        System.out.println("2. Search Name Ends With");
        System.out.println("3. Search Name Contains");
        System.out.println("4. Back To Search Main Page");
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);
        
        if(desire != 4) {
            String categoryName = scanner.nextLine("Enter category name to search : ").toUpperCase();
            ListInterface<SongCategory> sList = new DoublyLinkedList<>();
        
        switch(desire){
            case 1:
                sList = categoryList.where(s -> s.getCategoryName().toUpperCase().startsWith(categoryName));
                break;
            case 2:
                sList = categoryList.where(s -> s.getCategoryName().toUpperCase().endsWith(categoryName));
                break;
            case 3:
                sList = categoryList.where(s -> s.getCategoryName().toUpperCase().contains(categoryName));
                break;
            default:break;
        }
         // Show result
        if(sList.isEmpty())
            print.failed("Category name not found");
        else
            displaySpecification(sList);
     }
    } 
    
     public void sortByList() {
        print.tableHeader("SORT CATEGORY LIST BY", TABLE_WIDTH);
        
        System.out.println("1. Sort By Category ID");
        System.out.println("2. Sort By Category Name");
        System.out.println("3. Back To Category List Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 5", 1, 3);
    
        if (desire != 3) {
            print.otherMsg("Sort Category List By", 1);
            System.out.println("1. Ascending Order");
            System.out.println("2. Descending Order");
            System.out.println("3. Back To Category List Main Page");

            int sequenceType = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

            if (sequenceType != 3) {
                switch(desire) {
                    case 1 : 
                        sortById(sequenceType);
                        break;
                    case 2 : 
                        sortByName(sequenceType);
                        break;
                    default :
                        break; 
                }
                print.success("Sort successfully, press 6 to view category list");
            }
        }
    }
     
    public void sortById(int sequenceType) {
        switch (sequenceType) {
            case 1 : categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryID().compareToIgnoreCase(c2.getCategoryID()) < 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                    break;
            case 2 : categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryID().compareToIgnoreCase(c2.getCategoryID()) > 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                    break;
            default :break; 
        }
    }
     
    public void sortByName(int sequenceType) {
        switch (sequenceType) {
            case 1 : categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName()) < 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD); 
                    break;
            case 2 : categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName()) > 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                    break;
            default :break; 
        }
    }
    
     
    public void showCategoryList() {
        print.tableHeader("CATEGORY LIST", LIST_TABLE_WIDTH);
        displayList(categoryList);
        print.tableFooter(LIST_TABLE_WIDTH);
        print.otherMsg(String.format("Total Number Of Category: %d", categoryList.sizeOf()), 0);
    }
    
     
    public void displaySpecification(String title, SongCategory category) {
        print.tableHeader(title.toUpperCase(), SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-25s |\n", "Category ID", category.getCategoryID());
        System.out.printf("| %-18s | %-25s |\n", "Category Name", category.getCategoryName()); 
        print.tableFooter(SPECIFY_TABLE_WIDTH);
    }
    
       
    public void displaySpecification(ListInterface<SongCategory> category) {
        print.tableHeader("CATEGORY SEARCH RESULT", LIST_TABLE_WIDTH);
        displayList(category);
        print.tableFooter(LIST_TABLE_WIDTH);
    }
     
     
     public void displayList(ListInterface<SongCategory> categoryList){
        Iterator<SongCategory> itr = categoryList.getIterator();
        
        System.out.printf("| %-18s | %-51s |\n", "ID", "NAME");
        print.tableMiddleLine(LIST_TABLE_WIDTH);
        
        while(itr.hasNext()) {
            SongCategory dc = itr.next();
            System.out.println(dc.toString());
        }
    }
         
}
     

