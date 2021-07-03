package src;

import java.io.IOException;
import java.util.Scanner;
import packages.apiCall;

public class userInteractions{
    public static int getInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the zip code: ");
        int zip = scanner.nextInt();
        scanner.close();
        System.out.println("-------");
        return zip;
    }
    public static void main(String[] args) throws IOException{
        int zip = getInput();
        apiCall.getConnections(zip);
        System.out.println("-------");
    }
}