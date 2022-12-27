import java.util.ArrayList;

public class TestDay20 {
    public static void main(String[] args) {
        //---Part1---
        FileDecryption test = new FileDecryption();
        long decodeTest1 = test.decode(1,1);
        System.out.println("Part1: " + decodeTest1);
        //---Part2---
        long decodeTest2 = test.decode(10, 811589153);
        System.out.println("Part2: " + decodeTest2);
    }
}
