import java.io.*;
import java.util.ArrayList;

public class Reader {
    private String path;
    private ArrayList<String> list;
    private ArrayList<String> digitList;
    private ArrayList<Boolean> isValid;
    private ArrayList<Boolean> isLegible;
    private int charsInNumber;

    public Reader(String path) {
        this.path = path;
        this.list = new ArrayList<>();
        this.isValid = new ArrayList<>();
        this.isLegible = new ArrayList<>();
        this.read();
        this.addDigitList();
        this.charsInNumber = 9;
    }

    private void addDigitList() {
        digitList = new ArrayList<>();
        digitList.add(0, " _ | ||_|");
        digitList.add(1, "     |  |");
        digitList.add(2, " _  _||_ ");
        digitList.add(3, " _  _| _|");
        digitList.add(4, "   |_|  |");
        digitList.add(5, " _ |_  _|");
        digitList.add(6, " _ |_ |_|");
        digitList.add(7, " _   |  |");
        digitList.add(8, " _ |_||_|");
        digitList.add(9, " _ |_| _|");
    }

    private void read() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.path));
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<String> sortLines() {
        ArrayList<String> sortedList = new ArrayList<>();
        String oneRow;
        int idx;
        for (int i = 0; i < list.size(); i += 4) {
            oneRow = "";
            for (int j = 0; j < 3; j++) {
                idx = i + j;
                oneRow += list.get(idx) + "\n";
            }
            sortedList.add(oneRow);
        }
        return sortedList;
    }

    public String digitToString(int lineNumber, int number) {
        String sigleDigitString = "";
        int idx;
        for (int i = (number * 3); i <= (number * 3 + 56); i += 28) {
            for (int j = 0; j < 3; j++) {
                idx = i + j;
                sigleDigitString += sortLines().get(lineNumber).charAt(idx);
            }
        }
        return sigleDigitString;
    }

    public String digitToNumber(int lineNumber, int number) {
        String sigleDigitString = digitToString(lineNumber, number);
        String idx = "";
        if (digitList.contains(sigleDigitString)) {
            idx = "" + digitList.indexOf(sigleDigitString);
        } else {
            isLegible.set(lineNumber, false);
            idx = "?";
        }
        return idx;
    }

    public String lineToNumber(int lineNumber) {
        String lineDigitString = "";
        for (int i = 0; i < charsInNumber; i++) {
            isLegible.add(true);
            lineDigitString += digitToNumber(lineNumber, i);
        }
        return lineDigitString;
    }

    public ArrayList<String> readNumbers(ArrayList<String> sortedList) {
        ArrayList<String> allNumbers = new ArrayList<>();
        for (int i = 0; i < sortedList.size(); i++) {
            allNumbers.add(lineToNumber(i));
            System.out.println("Scanning line " + i + ": " + lineToNumber(i));
        }
        return allNumbers;
    }

    public ArrayList<Boolean> validateNumbers(ArrayList<String> allNumbers) {
        int checkSum = 0;
        for (int i = 0; i < allNumbers.size(); i++) {
            for (int j = 0; j < charsInNumber; j++) {
                checkSum += Character.getNumericValue(allNumbers.get(i).charAt(j));
            }
            if (checkSum % 11 == 0) {
                isValid.add(true);
                System.out.println("Number " + allNumbers.get(i) + " is VALID");
            } else {
                isValid.add(false);
                System.out.println("Number " + allNumbers.get(i) + " is NOT VALID");
            }
        }
        return isValid;
    }

    public void writeNumbers(ArrayList<String> allNumbers, String adres) {
        String toWrite = "";
        for (int i = 0; i < allNumbers.size(); i++) {
            if(isValid.get(i) && isLegible.get(i)) {
                toWrite += allNumbers.get(i) + "\n";
            } else if (!isLegible.get(i)) {
                toWrite += allNumbers.get(i) + " ILL\n";
            } else {
                toWrite += allNumbers.get(i) + " ERR\n";
            }
        }

        try {
            File newTextFile = new File(adres);

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(toWrite);
            fw.close();

        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }
}
