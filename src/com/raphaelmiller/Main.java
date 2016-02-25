package com.raphaelmiller;

import java.io.*;
import java.util.Scanner;

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

        //System.out.println("hello world");

        //accepts the new file name
        if(0 < args.length) {
            //if statement checks for argument existence
            //cipher = new File(args[0]);
            //message = new File(args[1]);

            //File's have been accepted.
        } else{
            //if no arguments exist, then exit program...
            System.err.println("Invalid Argument Count:" + args.length);
            System.exit(15);
        }

        int success = 0;
        //reads the File from the command line
        if((success = main.readFile(testFile, 1)) != 0){
            System.out.println("read success!");
        }else{
            System.err.println("read not successful" + testFile);
        }

        if((success = main.readFile(testPhrase, 2)) != 0){
            System.out.println("read success!");
        }else{
            System.err.println("read not successful" + testFile);
        }

        //System.out.println(main.getCipherCoderMatrix()[2]);
        char[] currentPhrase = main.parsePhrase(main.getPhrase());

    }

    private char[] parsePhrase(String phrase) {
        char[] phraseBlock = new char[3];

        for(int x = 0; x < phraseBlock.length; x++){
            if(phrase.charAt(x) == ' '){
                x++;
            }
            phraseBlock[x] = phrase.charAt(x);
        }

        if(phrase.length() > phraseBlock.length){
            parsePhrase(phrase.substring(phraseBlock.length));
        }



        return phraseBlock;
    }


    /**
     * @File readFile
     * @int function
     *
     * readFile - reads the file specified in the args of the method and sets them converts them to information for the
     * program to receive.
     * @return (condition code)
     */
    private int readFile(File readFile, int function){
        BufferedReader br = null;
        Scanner s = null;

        /* todo message and cipher have to be differentiated, one is int one is read as String. */

        switch (function) {
            case 1:
                int[] matrix = new int[81];
                try {
                    s = new Scanner(readFile);
                    int i = 0;
                    while (s.hasNext()) {
                        if (s.hasNextInt()){
                            matrix[i] = s.nextInt();
                            setCipherCoderMatrix(matrix);
                            System.out.println(matrix[i]);
                        } else{
                            s.next();
                        }
                        i++;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return 1;
            case 2:
                try {
                    String currentLine;

                    br = new BufferedReader(new FileReader(readFile));

                    while ((currentLine = br.readLine()) != null) {
                        System.out.println(currentLine);
                    }
                    setPhrase(currentLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 1;
        }
        return 0;
    }

    //todo method that takes aggrigated values and does matrix multiplication

    //todo method that converts numerical values to their letter counterparts


    public int[] getCipherCoderMatrix() {
        return cipherCoderMatrix;
    }

    public void setCipherCoderMatrix(int[] cipherCoderMatrix) {
        this.cipherCoderMatrix = cipherCoderMatrix;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
