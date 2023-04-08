/*Name: <Randy Kamindo>
ID: E02691297
Assignment Number: <Program 2>
COSC 311 - Winter 2023
 */
package database;

public class DataBaseArray {
    private DataBaseRecord[] dataBaseArray;
    private ArrayStack<Integer> deleteStack;
    private int count = 0;
    private int size;

    public DataBaseArray() {
        this(100);
    }

    public DataBaseArray(int size) {
        // initial array size cannot be 0
        if (size < 0)
            throw new IllegalArgumentException("Illegal capacity " + size);
        dataBaseArray = new DataBaseRecord[size];
        deleteStack = new ArrayStack<>(size);
    }

    // inserts record either at
    // the last deleted position if the delete stack
    // is not empty, we pop the stack to get the position
    // of the last deleted record
    // else at the end of the array
    // returns the position where the record was inserted at
    public int insert(DataBaseRecord record) {
        if (!deleteStack.isEmpty()) {
            int lastDeletedPos = deleteStack.pop();
            dataBaseArray[lastDeletedPos] = record;
            return lastDeletedPos;
        }
        else {
            dataBaseArray[count] = record;
            return count++;
        }
    }

    public boolean delete(int index) {
        dataBaseArray[index] = null;
        deleteStack.push(index);
        count--;
        return true;
    }

    public DataBaseRecord[] getDataBaseArray() {
        return dataBaseArray;
    }

    public void setDataBaseArray(DataBaseRecord[] dataBaseArray) {
        this.dataBaseArray = dataBaseArray;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DataBaseRecord retrieve(int temp) {
        return dataBaseArray[temp];
    }
}
