package com.raphaelmiller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /*In this assignment you'll write a program that encrypts the alphabetic letters in a file using the Hill cipher
     where the Hill matrix can be any size from 2 x 2 up to 9 x 9. Your program will take two command line parameters
      containing the names of the file storing the encryption key and the file to be encrypted. The program must
       generate output to the console (terminal) screen as specified below.*/

    //class variables
    static File cipher;
    static File message;

    //test file
    static File testFile = new File("/home/raphael/IdeaProjects/Hill Cipher/test_cipher");
    static File testPhrase = new File("/home/raphael/IdeaProjects/Hill Cipher/keyphrase");

    private int[] cipherCoderMatrix;
    private String phrase;

    public static void main(String[] args) {
        Main main = new Main();

        String[] phrase = null;

        try {
            int matrixSize = main.loadCipher(testFile);
            phrase = main.loadString(testPhrase, matrixSize);


        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] strNum = main.convertToInt("thebookisonthetable");
        System.out.println(strNum[7]);

    }

    /*Method to load files to String*/

    /**
     * loadString - loads the specified file to be encrypted in order to process for encryption
     * @param file
     * @param matrixSize
     *
     */
    private String[] loadString(File file, int matrixSize) throws IOException {

        String phrase;
        BufferedReader br = new BufferedReader(new FileReader(file));
        phrase = br.readLine();
        String formattedPhrase = phrase.replaceAll("\\s", "");
        return splitStringEvery(formattedPhrase, matrixSize);


    }

    /*Method to load cipher encoder into array*/

    /**
     * loadCipher - loads the specific file and converts the values contained inside to an array, with the exception of
     *              the first value, this value will be used for the array size.
     * @param file
     */
    private int loadCipher(File file) throws IOException {

        ArrayList<Integer> cipher = new ArrayList<>();
        String buffLine;
        int matrixSize;

        BufferedReader br = new BufferedReader(new FileReader(file));

        //reads first line
        matrixSize = Integer.parseInt(br.readLine());

        while ((buffLine = br.readLine()) != null){
            System.out.println(buffLine);

            String[] tokens = buffLine.split("\\s");
            for (int x = 0; x < tokens.length; x++){
                cipher.add(Integer.parseInt(tokens[x]));
                System.out.println(cipher.toString());
            }
        }

        return matrixSize;
        //System.out.println(matrixSize);

    }

    /*Method to do matrix multiplication*/
    /*Method to display output*/
    /*switch from integer to char representation based on Hill Cipher guidelines*/

    /**
     * convertToInt - accepts String tokens and converts them to number representations of their number starting with
     * a = 0, b = 1, etc...
     * @param string
     * @return
     */
    private int[] convertToInt(String string){
        int[] number = new int[string.length()];
        for(int x = 0; x < string.length(); x++){
            number[x] = string.charAt(x) - 'a';
        }
        return number;
    }

    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

}
