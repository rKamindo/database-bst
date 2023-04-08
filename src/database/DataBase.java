package database;

import index.DoubleThreadedBinarySearchTree;
import index.IndexRecord;

import java.util.Scanner;

public class DataBase {

    private DataBaseArray myDB;
    private int size;
    private DoubleThreadedBinarySearchTree ID;
    private DoubleThreadedBinarySearchTree First;
    private DoubleThreadedBinarySearchTree Last;
    private String idEntry; // for temporarily holding externally imported data
    private String firstEntry; // for temporarily holding externally imported data
    private String lastEntry; // for temporarily holding externally imported data
    Scanner scanner = new Scanner(System.in);

    public DataBase() {
        myDB = new DataBaseArray(100);
        ID = new DoubleThreadedBinarySearchTree();
        First = new DoubleThreadedBinarySearchTree();
        Last = new DoubleThreadedBinarySearchTree();
        size = 100;
    }

    // prompts user to enter ID to find by
    public void findIt() {
        System.out.println("Enter ID to find by: ");
        String id = scanner.nextLine();
        findRecordByID(id);
    }

    // helper method that searches for the id
    private void findRecordByID(String id) {
        if (!ID.contains(id))
            System.out.println("Student by ID: " + id + " not found");
        else {
            int temp = ID.get(id).getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }

    // prompts user the name and id that they are adding
    public void addIt() {
        System.out.println("Enter Last Name, First Name, ID: ");
        lastEntry = scanner.next();
        firstEntry = scanner.next();
        idEntry = scanner.next();
        scanner.nextLine();
        insertIt(idEntry, firstEntry, lastEntry);
    }

    // inserts a DataBaseRecord and 3 index records in the underlying data structure
    public void insertIt(String id, String firstName, String lastName) {
        if (this.size == myDB.getSize())
            throw new ArrayStoreException("myDB array is full! with" + myDB.getSize() + " elements");
        if (!ID.contains(id)) {
            int where = myDB.insert(new DataBaseRecord(id, firstName, lastName));
            ID.insert(new IndexRecord(id, where));
            First.insert(new IndexRecord(firstName, where));
            Last.insert(new IndexRecord(lastName, where));
        }
        else
            System.out.println("Cannot insert, duplicate exists by id: " + id);
    }

    // prompts user to enter ID to delete by
    public void deleteIt() {
        System.out.println("Enter ID to delete by: ");
        String id = scanner.nextLine();
        delete(id);
    }

    // delete records from underlying data structure and DBRecord
    private boolean delete(String id) {
        int where = ID.get(id).getWhere();
        DataBaseRecord record = myDB.retrieve(where);

        ID.remove(record.getID());
        First.remove(record.getFirstName());
        Last.remove(record.getSecondName());
        myDB.delete(where);
        System.out.println("Deleted student by id: " + id);
        return true;
    }

    public void ListByIDAscending() {
        ID.setIteratorHead();
        while (ID.hasNext()) {
            int temp = ID.next().getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }

    public void ListByIDDescending() {
        ID.setIteratorTail();
        while (ID.hasPrevious()) {
            int temp = ID.previous().getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }

    public void ListByFirstAscending() {
        First.setIteratorHead();
        while (First.hasNext()) {
            int temp = First.next().getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }

    public void ListByFirstDescending() {
        First.setIteratorTail();
        while (First.hasPrevious()) {
            int temp = First.previous().getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }

    public void ListByLastAscending() {
        Last.setIteratorHead();
        while (Last.hasNext()) {
            int temp = Last.next().getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }

    public void ListByLastDescending() {
        Last.setIteratorTail();
        while (Last.hasPrevious()) {
            int temp = Last.previous().getWhere();
            System.out.println(myDB.retrieve(temp));
        }
    }
}
