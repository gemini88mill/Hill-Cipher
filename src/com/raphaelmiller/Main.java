package com.raphaelmiller;

import java.io.*;

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
        if((success = main.readFile(testFile, 2)) != 0){
            System.out.println("read success!");
        }else{
            System.err.println("read not successful" + testFile);
        }


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

        //todo message and cipher have to be differentiated, one is int one is read as String.

        switch (function) {
            case 1:
                try {
                    int matrix;

                    //todo not parsing correctly :/
                    br = new BufferedReader(new FileReader(readFile));

                    while ((matrix = br.read()) != -1) {
                        System.out.println(matrix);
                    }
                } catch (IOException e) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 1;
        }
        return 0;
    }
}
