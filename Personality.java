import java.util.*;
import java.io.*;

public class Personality {
   public static final int DIM = 4;

   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      intro();
      File inputFile = input(console);
      Scanner results = new Scanner(inputFile);
      PrintStream output = output(console);
      while(results.hasNextLine()) {
         printName(results, output);
         int[] answersA = new int[DIM];
         int[] answersB = new int[DIM];
         counts(results, answersA, answersB);
         int[] bPercentage = percentages(answersB, answersA, output);
         personality(bPercentage, output);
      }
   }
   
   // prints introduction to console
   public static void intro() {
      System.out.println("This program processes a file of answers to the");
      System.out.println("Keirsey Temperament Sorter. It converts the");
      System.out.println("various A and B answers for each person into");
      System.out.println("a sequence of B-percentages and then into a");
      System.out.println("four-letter personality type.");
      System.out.println();
   }
   
   // asks for the input file
   // parameters:
   //    Scanner console - scanner for user input
   // returns the input file
   public static File input(Scanner console) throws FileNotFoundException{
      System.out.print("input file name? ");
      String inputFileName = console.nextLine();
      File inputFile = new File(inputFileName);
      return inputFile;
   }
   
   // asks for an output file name
   // parameters:
   //    Scanner console - scanner for user input
   // returns PrintStream to output file
   public static PrintStream output(Scanner console) throws FileNotFoundException{
      System.out.print("output file name? ");
      String outputFileName = console.nextLine();
      PrintStream output = new PrintStream(new File(outputFileName));
      return output;
   }
   
   // prints name to output file
   // parameters:
   //    Scanner results - scanner to read input file
   //    PrintStream output - print stream to output file
   public static void printName(Scanner results, PrintStream output) throws FileNotFoundException {
      String name = results.nextLine();
      output.print(name + ": ");
   }
   
   // compute counts of answers in each dimension
   // parameters:
   //    Scanner results - scanner to read input file
   //    int[] answersA - array for number of A answers in each dimension 
   //    int[] answersb - array for number of B answers in each dimension
   // returns an array of the number of B answers
   public static void counts(Scanner results, int[] answersA, int[] answersB) {
      String answers = results.nextLine();
      for(int i = 0; i < answers.length(); i++) {
         String answer = answers.charAt(i) + "";
         int dimension = DIM;
         // e/i
         if (i % 7 < 1){
            dimension = 0;
         // s/n
         } else if (i % 7 < 3) {
            dimension = 1;
         // t/f
         } else if (i % 7 < 5) {
            dimension = 2;
         // j/p
         } else if (i % 7 < 7) {
            dimension = 3;
         }
         aOrB(answersA, answersB, answer, dimension);
      }
   }
   
   // adds to the A or B array
   // parameters:
   //    int[] answersA - array for number of A answers in each dimension 
   //    int[] answersb - array for number of B answers in each dimension
   //    String answer - the single answer being added
   //    int dimension - which dimension is being added to
   public static void aOrB(int[] answersA, int[] answersB, String answer, int dimension) {
      if (answer.equalsIgnoreCase("A")) {
         answersA[dimension] += 1;
      } else if (answer.equalsIgnoreCase("B")) {
         answersB[dimension] += 1;
      }
   }
   
   // calculates the percentage of B answers
   // parameters:
   //    int[] answersB - array for number of B answers in each dimension 
   //    int[] answersA - array for number of A answers in each dimension
   //    PrintStream output - the PrintStream to the output file
   // returns an array of the percentage of B answers in each dimension
   public static int[] percentages(int[] answersB, int[] answersA, PrintStream output) {
      int[] bPercentage = new int[DIM];
      for(int i = 0; i < DIM; i++) {
         double b = answersB[i];
         double total = answersB[i] + answersA[i];
         double percentage = b / total * 100;
         int rounded = (int)Math.round(percentage);
         bPercentage[i] = rounded;
      }
      String percentage =  Arrays.toString(bPercentage);
      output.print(percentage + " = ");
      return bPercentage;
   }
   
   // determines the taker's personality type
   // parameters:
   //    int[] - bPercentage - the array of the percentage of B answers in each dimension
   //    PrintStream output - the PrintStream to the output file
   public static void personality(int[] bPercentage, PrintStream output) {
      String personality = "";
      String[] types = new String[DIM];
      types[0] = "EI";
      types[1] = "SN";
      types[2] = "TF";
      types[3] = "JP";
      for(int i = 0; i < DIM; i++) {
         String trait = types[i];
         if(bPercentage[i] < 50) {
            personality += trait.charAt(0);
         } else if (bPercentage[i] > 50) {
            personality += trait.charAt(1);
         } else {
            personality += "X";
         }
      }
      output.println(personality);
   }
}