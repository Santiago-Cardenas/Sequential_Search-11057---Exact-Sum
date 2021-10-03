package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner sc;
    private int n, m, start;
    private String FILE_DATA_TEST_PATH = "data/test";
    private List<Integer> bookPrices = new ArrayList<Integer>();

    public Main() {
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.menu();
    }

    public void menu() throws IOException {
        System.out.println("1) Normal test\n"+
                "2) Default test\n");
        int option=sc.nextInt();
        if(option==1) {
            readEntry();
        }
        else{
            readDefaultEntry();
        }
    }

    public ArrayList<Integer> search(int f,int duplicates) {
        ArrayList<Integer> sumCombinations = new ArrayList<>();
        ArrayList<Integer> howClose = new ArrayList<>();
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < bookPrices.size(); i++) {
            start = bookPrices.get(i);
            for(int j=0;j<bookPrices.size(); j++) {
                if (start + bookPrices.get(j) == f) {
                    if (duplicates == 1) {
                        if (bookPrices.get(j) - start >= 0) {
                            sumCombinations.add(start);
                            sumCombinations.add(bookPrices.get(j));
                            howClose.add(bookPrices.get(j) - start);
                        }
                    } else {
                        if (bookPrices.get(j) - start > 0) {
                            sumCombinations.add(start);
                            sumCombinations.add(bookPrices.get(j));
                            howClose.add(bookPrices.get(j) - start);
                        }
                    }
                }
            }

            }

        Collections.sort(howClose);
        for (int i = 0; i < sumCombinations.size(); i++) {
            if (sumCombinations.get(i + 1) - sumCombinations.get(i) == howClose.get(0)) {
                answer.add(sumCombinations.get(i));
                answer.add(sumCombinations.get(i + 1));
            }
            i++;
        }
        return answer;
    }

    public void readEntry() {
        boolean stillReading=true;
        String answer="";
        String buffer="";
       do {
           if(!buffer.isEmpty()){
               n=Integer.valueOf(buffer);
           }
           else {
               n = sc.nextInt();
               sc.nextLine();
           }
           buffer = "";
           for (int i = 0; i < n; i++) {
               bookPrices.add(sc.nextInt());
           }
           sc.nextLine();
           Collections.sort(bookPrices);
           m = sc.nextInt();
           sc.nextLine();
           buffer=sc.nextLine();
           answer += printMsg();
           buffer=sc.nextLine();
           bookPrices.clear();
       }
       while(!buffer.isEmpty());
        System.out.println(answer);
    }

    public void readDefaultEntry() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_DATA_TEST_PATH));
        String line = br.readLine();
        String answer="";
        while (line!=null) {
            String[] parts = line.split("\\|");
            n=Integer.parseInt(parts[0]);
            for(int i=1;i< parts.length-1;i++) {
                bookPrices.add(Integer.parseInt(parts[i]));
            }
            Collections.sort(bookPrices);
            m=Integer.parseInt(parts[parts.length-1]);
            answer += printMsg();
            line = br.readLine();
            bookPrices.clear();
        }
        br.close();
        System.out.println(answer);
    }

    public String printMsg(){
        String msg="";
        boolean duplicates=false;
        ArrayList<Integer> prices=null;
        for(int i=0;i<bookPrices.size();i++) {
            if (Collections.frequency(bookPrices, bookPrices.get(i))>1) {
                duplicates=true;
            }
        }
        if(duplicates) {
            prices = search(m,1);
        }
        else {
            prices = search(m,2);
        }
        if (prices.size()>0) {
            msg+="Peter should buy books whose prices are " + prices.get(0) + " and " + prices.get(1);
            msg+="\n\n";
        }
        return msg;
    }
}

