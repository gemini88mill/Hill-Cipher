package com.raphaelmiller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    private ArrayList<Integer> matrixKey;

    private int[] cipherCoderMatrix;
    private String phrase;

    public static void main(String[] args) {
        Main main = new Main();

        String[] phrase = null;
        int matrixSize;
        int[][] place = {{6,24,1},
                         {13,16,10},
                         {20,17,15}};
        int[] testb = {0,2,19};
        int[] strNum;

        try {
            //loads the hill key
            matrixSize = main.loadCipher(testFile);


            //loads the key to be encoded and separates them into chunks based on the dimensions of the hill cipher
            phrase = main.loadString(testPhrase, matrixSize);
            int[] matrixNum;
            for (String aPhrase : phrase) {
                //System.out.println(phrase[2]);
                strNum = main.convertToInt(aPhrase);
                System.out.println(Arrays.toString(strNum));
                matrixNum = main.matrixMultipication(main.getMatrixKey(), strNum, matrixSize);
                main.matrixIntToCharString(matrixNum);
            }





        } catch (IOException e) {
            e.printStackTrace();
        }





        //System.out.println(strNum[7]);


    }


    /**
     * matrixIntToCharString - converts a int into a char representation of that number a = 0 to z = 25, then puts that
     * data into a new string and returns that string.
     * @param matrixNum
     * @return StringBuilder
     */
    private StringBuilder matrixIntToCharString(int[] matrixNum) {

        StringBuilder toEncodedMessage = new StringBuilder();

        for(int x = 0; x < matrixNum.length; x++){
            char charString = (char) (matrixNum[x] + 97);
            toEncodedMessage.append(charString);
        }

        return toEncodedMessage;
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
            //System.out.println(buffLine);

            String[] tokens = buffLine.split("\\s");
            for (int x = 0; x < tokens.length; x++){
                cipher.add(Integer.parseInt(tokens[x]));


            }
            //System.out.println(cipher);
        }
        setMatrixKey(cipher);
        //System.out.println(matrixSize);
        return matrixSize;


    }

    /*Method to do matrix multiplication*/

    /**
     * matrixMultiplication - collects two matrices and adds them together 
     *
     * @param aMatrix
     * @param bMatrix
     * @param matrixSize
     * @return int[]
     */
    private int[] matrixMultipication(ArrayList<Integer> aMatrix, int[] bMatrix, int matrixSize){


        //todo fix matrix multiplication to return results... 

        System.out.println(aMatrix.toString());
        //System.out.println(bMatrix.length);
        int[] result = new int[matrixSize];
        int holder = 0;

        for (int x = 0; x < bMatrix.length; x++) {
            System.out.println(aMatrix.get(x) + " * " + bMatrix[x]);
            holder = holder + (aMatrix.get(x) * bMatrix[x]);
            holder = holder % 26;
            System.out.println(holder);
        }
        result[0] = holder;
        System.out.println(result[0]);

        return result;
    }

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

    public ArrayList<Integer> getMatrixKey() {
        return matrixKey;
    }

    public void setMatrixKey(ArrayList<Integer> matrixKey) {
        this.matrixKey = matrixKey;
    }
}
