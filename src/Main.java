import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String adres = "..\\OCRstory3.txt";
        Reader reader = new Reader(adres);

        ArrayList<String> sortedList = reader.sortLines();
        ArrayList<String> allNumbers = reader.readNumbers(sortedList);
        ArrayList<Boolean> validation = reader.validateNumbers(allNumbers);

        String adresWrite = "..\\output.txt";
        reader.writeNumbers(allNumbers, adresWrite);
    }
}
