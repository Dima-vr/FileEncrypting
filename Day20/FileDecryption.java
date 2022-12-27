import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileDecryption {
    public ArrayList<Integer> encryptedFileData = new ArrayList<>();

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

    public long decode(int loops, long key){
        ArrayList<Long> mixedFile = mixFile(loops, key);
        long sum = 0;
        for (int i = 1; i <= 3; i++) {
            sum += mixedFile.get((mixedFile.indexOf(0L) + (1000 * i)) % encryptedFileData.size());
        }
        return sum;
    }

    public ArrayList<Long> mixFile(int loops, long key) {
        int size = encryptedFileData.size();
        ArrayList<Long> mixList = new ArrayList<>();
        for (Integer data : encryptedFileData) {
            mixList.add((long) data * key);
        }
        ArrayList<Integer> indexOfMixList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexOfMixList.add(i);
        }
        for (int times = 0; times < loops; times++) {
            for (int i = 0; i < size; i++) {
                //Calculate the current index in mixList
                int currentIndex = indexOfMixList.indexOf(i);
                Long shift = mixList.get(currentIndex);
                //Calculate the new index where we put our element
                long newIndex = (currentIndex + shift) % (size - 1);
                if (newIndex < 0) {
                    newIndex = -(-(newIndex - 1) % size);
                    newIndex = (newIndex + size) % size;
                }
                if (newIndex >= size) {
                    newIndex = (newIndex + (newIndex / size)) % size;
                }
                if (newIndex == 0) {
                    newIndex = size - 1;
                }

                //Move the element from the starting index of the element and the index where it will be
                int indexOfIndex = indexOfMixList.get(currentIndex);
                mixList.remove(currentIndex);
                indexOfMixList.remove(currentIndex);
                mixList.add((int) (newIndex), shift);
                indexOfMixList.add((int) (newIndex), indexOfIndex);
            }
        }
        return mixList;
    }
}