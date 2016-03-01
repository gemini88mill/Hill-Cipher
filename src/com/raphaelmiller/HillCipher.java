/**
 * Created by Raphael Miller under the influence of Coffee...
 * ra677877
 * 02.29.2016
 *
 * Note: The argument collector is untested since my compiler did not recognize the file name because of the space in
 * project name. So if there is a problem then I do not know about it, tested with hard coded File declaration. It
 * should work.
 *
 * java.io.FileNotFoundException: file:/home/raphael/IdeaProjects/Hill (No such file or directory) -- error noted
 * this Stops at hill because the Java compiler freaks out on spaces #linuxproblems #javalivesmatter
 *
 * Hill Cipher Project - the purpose is to create a simple hill cipher that accepts a String (up to 10000 chars long),
 * encrypts the message and presents the message, the encryption and the cipher used. this project used some Java 8
 * implementation so please feel free to use Java 8, only used foreach statement that are native to the Java lib.
 *
 */

package com.raphaelmiller;

import java.io.*;
import java.util.ArrayList;

public class HillCipher {

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
    private StringBuilder plaintextFile;

    private int[] cipherCoderMatrix;
    private String phrase;

    public static void main(String[] args) {
        //implements the class Hillcipher in order for the methods to be used in the static method
        HillCipher hillCipher = new HillCipher();

        //stringBuilder declaration
        StringBuilder encodedMessage = new StringBuilder();

        //transition variables
        String[] phrase;
        int matrixSize;
        int[] messageToEncodeNum;

        //file string holders for arguments
        File argumentFile0 = new File(args[0]);
        File argumentFile1 = new File(args[1]);

        try {
            //loads the hill key
            matrixSize = hillCipher.loadCipher(argumentFile0);


            //loads the key to be encoded and separates them into chunks based on the dimensions of the hill cipher
            phrase = hillCipher.loadString(argumentFile1, matrixSize);
            int[] matrixNum;
            for (String messageToEncodeAlpha : phrase) {
                messageToEncodeNum = hillCipher.convertToInt(messageToEncodeAlpha);
                matrixNum = hillCipher.matrixMultipication(hillCipher.getMatrixKey(), messageToEncodeNum, matrixSize);
                encodedMessage.append(hillCipher.matrixIntToCharString(matrixNum));

            }

            hillCipher.output(encodedMessage);
            //System.out.println(encodedMessage.toString());



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Output - outputs the message, cipher key and the plainTextFile
     * @param encodedMessage
     */
    private void output(StringBuilder encodedMessage) {
        System.out.println(getMatrixKey().toString());
        System.out.println(getPlaintextFile().toString() + "\n");
        System.out.println(encodedMessage);
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
        StringBuilder stringFile = new StringBuilder();
        String phrase;
        BufferedReader br = new BufferedReader(new FileReader(file));
        phrase = br.readLine();
        setPlaintextFile(stringFile.append(phrase));
        String formattedPhrase = phrase.replaceAll("\\W", "");
        formattedPhrase = formattedPhrase.toLowerCase();
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

        //System.out.println(aMatrix.toString());
        //System.out.println(bMatrix.length);
        int[] result = new int[aMatrix.size()];
        int holder = 0, x = 0, y = 0;
        int[] matrixTotal = new int[bMatrix.length];

        //do matrix multiplication
        while(x < aMatrix.size()){
            //System.out.println(aMatrix.get(x) + " * " + bMatrix[y]);
            result[x] = aMatrix.get(x) * bMatrix[y];
            if(y < bMatrix.length - 1){
                y++;
            } else {
                y = 0;
            }
            x++;
        }

        for(int p = 0; p < bMatrix.length; p++) {
            for (int z = 0; z < bMatrix.length; z++) {
                matrixTotal[p] = matrixTotal[p] + result[z + (bMatrix.length * p)];
                //System.out.println(p + " = " + result[z + bMatrix.length * p] + " = " + matrixTotal[p]);
            }
            matrixTotal[p] = matrixTotal[p] % 26;
            //System.out.println(matrixTotal[p]);
        }



        result[0] = holder;

        //System.out.println(result[0]);

        return matrixTotal;
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

    /**
     * splitStringEvery() - Spilts the string at a certain interval.  
     * @param s
     * @param interval
     * @return
     */
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

    /*-------------------------------------GETTERS AND SETTERS-------------------------------------------------------*/
    public ArrayList<Integer> getMatrixKey() {
        return matrixKey;
    }

    public void setMatrixKey(ArrayList<Integer> matrixKey) {
        this.matrixKey = matrixKey;
    }

    public StringBuilder getPlaintextFile() {
        return plaintextFile;
    }

    public void setPlaintextFile(StringBuilder plaintextFile) {
        this.plaintextFile = plaintextFile;
    }
}
