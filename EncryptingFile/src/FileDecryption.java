import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileDecryption {
    final public ArrayList<Integer> encryptedFileData = new ArrayList<>();

    public FileDecryption() {
        //Try to read only integers from file "EncryptedFile.txt"
        try {
            File encryptedFile = new File("EncryptedFile.txt");
            Scanner myReader = new Scanner(encryptedFile);
            do {
                //If next is not integer, we move to next
                while (!myReader.hasNextInt() && myReader.hasNext()) {
                    myReader.next();
                }
                encryptedFileData.add(myReader.nextInt());
            } while (myReader.hasNextInt());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        FileDecryption test = new FileDecryption();
        System.out.println(test.encryptedFileData);
        ArrayList<Integer> mixedFile = test.mixFile();
        int sum = 0;
        for (int i = 1; i <= 3; i++) {
            sum += mixedFile.get((mixedFile.indexOf(0) + (1000 * i)) % test.encryptedFileData.size());
        }
        System.out.println(mixedFile);
        System.out.println(sum);
    }

    public ArrayList<Integer> mixFile() {
        int size = encryptedFileData.size();
        ArrayList<Integer> mixList = new ArrayList<>(encryptedFileData);
        ArrayList<Integer> indexOfMixList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexOfMixList.add(i);
        }
        for (int i = 0; i < size; i++) {
            //Calculate the current index in mixList
            int currentIndex = indexOfMixList.indexOf(i);
            int shift = mixList.get(currentIndex);
            if (shift == 0) {
                continue;
            }
            //Calculate the new index where we put our element
            int newIndex = currentIndex + shift;
            while (newIndex >= size) {
                newIndex = (newIndex + 1) - size;
            }
            if (newIndex < 0) {
                newIndex = -(-(newIndex-1) % size);
                newIndex = newIndex + size;
            }
            if(newIndex == 0){
                newIndex = size - 1;
            }
            int indexOfIndex = indexOfMixList.get(currentIndex);
            //Move elements between the starting index of the element and the index where it will be
            mixList.remove(currentIndex);
            indexOfMixList.remove(currentIndex);
            mixList.add(newIndex, shift);
            indexOfMixList.add(newIndex, indexOfIndex);
        }
        return mixList;
    }
}