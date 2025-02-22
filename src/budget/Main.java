package budget;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> inputs = new ArrayList<>();
        double sum = 0;
        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            inputs.add(temp);
            sum += Double.parseDouble(temp.split("\\$")[1]);
        }

        for(String st : inputs) {
            System.out.println(st);
        }
        System.out.println("Total: $" + sum);
    }
}
