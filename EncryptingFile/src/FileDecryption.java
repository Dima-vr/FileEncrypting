import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println(sum);
    }

    public ArrayList<Integer> mixFile() {
        ArrayList<Integer> mixList = new ArrayList<>(encryptedFileData.stream().toList());
        int size = encryptedFileData.size();
        int index = 0;
        for (int i = 0; i < size; i++) {
            //Calculate the index in encryptedFileData
            int indexInFile = i % size;
            //TODO problem: in file can be the same numbers.
            // solution: Index of each number
            //Calculate the index in mixList( if encryptedFileData[indexInFile] = 3
            //that mean we need to find which index in mixList[index] = 3
            index = mixList.indexOf(encryptedFileData.get(indexInFile));
            int shift = mixList.get(index);
            //Calculate the new index where we put our element
            int newIndex = index + shift - (shift < 0 ? 1 : 0);
            if (newIndex < 0) {
                newIndex = mixList.size() + newIndex;
            }
            if (newIndex >= mixList.size()) {
                newIndex = newIndex - mixList.size() + 1;
            }
            //Move elements between the starting index of the element and the index where it will be
            if (newIndex - index > 0) {
                for (int j = index; j < newIndex; j++) {
                    mixList.set((j) % size, mixList.get((j + 1) % size));
                }
                mixList.set(newIndex, shift);
            }
            if (newIndex - index < 0) {
                for (int j = index; j > newIndex; j--) {
                    mixList.set((j) % size, mixList.get((j - 1) % size));
                }
                mixList.set(newIndex, shift);
            }
            System.out.println(mixList);
        }
        return mixList;
    }
}