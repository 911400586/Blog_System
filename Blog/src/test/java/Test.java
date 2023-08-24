import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        String img = "32.txt";
        String tail = img.substring(img.lastIndexOf('.'));
        System.out.println(tail);
    }
}
