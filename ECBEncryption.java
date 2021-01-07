import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * Quellcodevorlage fuer das Projekt
 * 
 * ECB Verschluesselung
 * 
 * KLASSE: 11IT2B
 * 
 * @author Miro Ruhl, Dennis Neumann
 */
public class ECBEncryption {

    static HashMap<Character, String> encryptionCodeMap = createEncryptionCodeMap();
    static HashMap<String, Character> decryptionCodeMap = createDecryptionCodeMap();

    public static void main(String[] args) {
        System.out.println("Willkommen bei PlanetExpress!");
        System.out.println("Welchen Modus möchten Sie nutzen?");
        System.out.println("1) Verschlüsselung einer Nachricht");
        System.out.println("2) Entschlüsselung einer Nachricht");

        Scanner sc = new Scanner(System.in); // anlegen von einem Scanner Objekt
        int userInput = sc.nextInt(); // Einlesen der Auswahl

        // Verschluesselung einer Nachricht
        if (userInput == 1) {
            boolean invalidChar = true;
            boolean invalidTextLength = true;
            String inputText = null;
            int blockSize = 0;

            while (invalidChar) {
                System.out.println("Geben Sie den zu verschlüsselnden Text ein:");
                inputText = sc.next(); // Einlesen des Textes
                invalidChar = CheckForInvalidChar(inputText);
            }
            while (invalidTextLength) {
                System.out.println("Geben Sie die Länger der Blöcke (r) an:");
                blockSize = sc.nextInt(); // Einlesen der laenge r
                invalidTextLength = CheckTextLength(inputText, blockSize);
            }

            String encryptedText = encrypt(inputText, blockSize);// Verschluesseln der Nachricht
            System.out.println("Der verschlüsselte Text lautet: " + encryptedText); // Ausgabe auf der Konsole
        }
        // Entschluesselung einer Nachricht
        if (userInput == 2) {
            boolean invalidChar = true;
            boolean invalidTextLength = true;
            String inputText = null;
            int blockSize = 0;

            while (invalidChar) {
                System.out.println("Geben Sie den zu Entschlüsselnden Text ein:");
                inputText = sc.next(); // Einlesen des Textes
                invalidChar = CheckForInvalidChar(inputText);
            }
            while (invalidTextLength) {
                System.out.println("Geben Sie die Länger der Blöcke (r) an:");
                blockSize = sc.nextInt(); // Einlesen der laenge r
                invalidTextLength = CheckTextLength(inputText, blockSize);
            }

            String decryptedText = decrypt(inputText, blockSize);// Verschluesseln der Nachricht
            System.out.println("Der verschlüsselte Text lautet: " + decryptedText); // Ausgabe auf der Konsole
        }
        System.out.println("Danke für Ihre Nutzung und auf Wiedersehen!");
        sc.close(); // schliessen des Scanner Objekts

    }

    // ************************************************************
    //
    // AB HIER BEGINNT IHR QUELLCODE
    //
    // ************************************************************

    //
    // Darstellung eines Textes als Binaercode
    //


    // Returns Array of Bits from given char
    static char[] symbolToBits(char symbol) {
        char[] charBitArray = (encryptionCodeMap.get(symbol)).toCharArray();  // Get Binary Code as String from HashMap | Convert it to char[]
        return charBitArray;
    }

    // Returns an flat Array of bits for each char from given Text
    static char[] textToBits(String text) {
        char[] flatBitArray = new char[text.length() * symbolLenght()];
        for (int i = 0; i < text.length(); i++) {
            char[] charBitArray = symbolToBits(text.toUpperCase().charAt(i)); // Get Binary Code as char[]
            for (int j = 0; j < charBitArray.length; j++) {
                flatBitArray[i * symbolLenght() + j] = charBitArray[j]; // Add charBitArray at the end of bitArray
            }
        }
        return flatBitArray;
    }

    //
    // Zerteilung in Bloecke der Laenge r
    //

    // Returns an array of the first bits from given array by length r
    static char[] firstN(char[] bits, int r) {
        char[] bitBlock = new char[r];
        for (int i = 0; i < r; i++) {
            bitBlock[i] = bits[i];
        }
        return bitBlock;
    }

    // Removes first bits by length r and return the rest
    static char[] lastN(char[] bits, int r) {
        char[] bitBlock = new char[bits.length - r];
        for (int i = 0; i < bits.length - r; i++) {
            bitBlock[i] = bits[i + r];
        }
        return bitBlock;
    }

    // Returns an 2D Array of BitBlocks by given char[]
    static char[][] bitsToBlocks(char[] bits, int size) {
        char[][] arrayOfBitBlocks = new char[(int) Math.floor(bits.length / size)][size]; // Init 2D Array [number of bit blocks] [length of bit Blocks]
        char[] reducedBitsArray = bits; // Init bufferArray which contains the (bits Array - the firstN`s) for each loop
        for (int i = 0; i < Math.floor(bits.length / size); i++) {
            arrayOfBitBlocks[i] = firstN(reducedBitsArray, size); // Add first bits by length r from reducedBits to 2D Array
            reducedBitsArray = lastN(reducedBitsArray, size); // Removes first bits by length r | Update reducedBits
        }
        return arrayOfBitBlocks;
    }

    //
    // Verschluesselung von Bloecken
    //

    // Returns given 2D Array rightshifted
    static char[][] encryptBlocks(char[][] blocks) {
        char[][] encryptedBlocks = new char[blocks.length][blocks[0].length]; // Init 2D Array [number of bit blocks] [length of bit Blocks]
        for (int i = 0; i < blocks.length; i++) {
            //encryptedBlocks[i] = shiftRight(blocks[i]); // Shift each Bit one position to their right | Add shifted BitBlock to 2D Array
            encryptedBlocks[i] = shift(blocks[i], 5, false);
        }
        return encryptedBlocks;
    }

    //
    // Zusammenfuegen von Bloecken
    //

    // Returns given 2DArray flatted
    static char[] blocksToBits(char[][] blocks) {
        char[] flatBitArray = new char[blocks.length * blocks[0].length]; // Init flatted Array by (length of each block * number of blocks)
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                flatBitArray[i * blocks[0].length + j] = blocks[i][j]; // Add each bit of 2D Array in order to flattArray
            }
        }
        return flatBitArray;
    }

    //
    // Umwandlung eines Binaercodes als Text
    //

    // Returns char by given bitArray
    static char bitsToSymbol(char[] bits) {
        String bitBlockString = new String(bits); // Convert bitArray into a String
        return decryptionCodeMap.get(bitBlockString);
    }

    // Returns an Array of Chars by given bit Array
    static char[] bitsToText(char[] bits, int size) {
        char[] newCharArray = new char[(int) Math.ceil(bits.length / size)]; // Init array of chars by (length of bitArray / size)
        char[] reducedBitsArray = bits; /// Init bufferArray which contains the (bits Array - the firstN`s) for each loop
        for (int i = 0; i < Math.ceil(bits.length / size); i++) {
            newCharArray[i] = bitsToSymbol(firstN(reducedBitsArray, size)); // Gets first bits by given size | Add char at the end of newCharArray
            reducedBitsArray = lastN(reducedBitsArray, size); // Removes first bits by size | Update reducedBitsArray
        }
        return newCharArray;
    }

    //
    // Verschluesselung eines Textes
    //

    // Returns encrypted String by given text and blocksize
    static String encrypt(String text, int blockSize) { // Zuweisungs probleme in struktogramm
        char[] textBits = textToBits(text); // Get flat Array of Bits by given text
        // Convert flat bitArray to 2D BlockArray (length of each Block: size)| shift bits to their right | Add to encryptedArray
        char[][] encryptedBitArray = encryptBlocks(bitsToBlocks(textBits, blockSize));
        char[] flatBitArray = blocksToBits(encryptedBitArray);
        char[] encodedTextBits = new char[textBits.length];
        // Prevent losing rest bits by not matching blockSize
        for (int i = 0; i < flatBitArray.length; i++) {
            encodedTextBits[i] = flatBitArray[i];
        }
        // Convert 2DArray (length of each Block: size) to flat bitArray | Convert bits[] to char[] | Convert char[] to String | Add to encryptedString
        return new String(bitsToText(encodedTextBits, symbolLenght()));
    }

    //
    // Entschluesselung von Bloecken
    //

    static char[][] decryptBlocks(char[][] blocks) {
        char[][] decryptedBlocks = new char[blocks.length][blocks[0].length]; // Init 2D Array [number of bit blocks] [length of bit Blocks]
        for (int i = 0; i < blocks.length; i++) {
            //decryptedBlocks[i] = shiftLeft(blocks[i]); // Shift each Bit one position to their left | Add shifted BitBlock to 2D Array
            decryptedBlocks[i] = shift(blocks[i], 5, true);
        }
        return decryptedBlocks;
    }

    //
    // Entschluesselung eines Textes
    //

    static String decrypt(String text, int blockSize) {
        char[] textBits = textToBits(text); // Get flat Array of Bits by given text

        // Convert flat bitArray to 2D BlockArray (length of each Block: size)| shift bits to their left | Add to encryptedArray
        char[][] decryptedBitArray = decryptBlocks(bitsToBlocks(textBits, blockSize));
        char[] flatBitArray = blocksToBits(decryptedBitArray);
        char[] decodedTextBits = new char[textBits.length];
        // Prevent losing rest bits by not matching blockSize
        for (int i = 0; i < flatBitArray.length; i++) {
            decodedTextBits[i] = flatBitArray[i];
        }
        // Convert 2DArray (length of each Block: size) to flat bitArray | Convert bits[] to char[] | Convert char[] to String | Add to encryptedString
        return new String(bitsToText(decodedTextBits, symbolLenght()));
    }

    //
    // AddOns
    //

    // Input Handling checks that blockSize is not greater than textBitLength
    static Boolean CheckTextLength (String text, int blockSize) {
        if (blockSize > text.length() * symbolLenght()) {
            System.out.println("Die Block Länge ist zu lang!");
            return true;
        }
        return false;
    }

    // Input Handling checks each Char in the string if its in the encodeMap
    static Boolean CheckForInvalidChar(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (encryptionCodeMap.get(text.toUpperCase().charAt(i)) == null) {
                System.out.println("Ungültiges Zeichen: " + "'" + text.charAt(i) + "'");
                return true;
            }
        }
        return false;
    }

    // Own Shift Method
    static char[] shift(char[] bitBlock,int blockSize, boolean decode) {
        printCharArray(bitBlock);
        char[] shiftedBitBlock = new char[bitBlock.length];

            if (!decode) {
                for (int j = 0; j < bitBlock.length; j++) {
                    if (j < bitBlock.length - 1) {
                        if (!decode) {
                            shiftedBitBlock[j] = bitBlock[j + 1];
                        } else {
                            shiftedBitBlock[j + 1] = bitBlock[j];
                        }
                    } else {
                        if (!decode) {
                            shiftedBitBlock[j] = bitBlock[0];
                        } else {
                            shiftedBitBlock[0] = bitBlock[j];
                        }
                    }
                }
            }

        printCharArray(shiftedBitBlock);
        return shiftedBitBlock;
    }



    // ************************************************************
    // HIER ENDET IHR QUELLCODE
    //
    // Quellcodevorlage
    //
    // BITTE NEHMEN SIE AB HIER KEINE AENDERUNGEN MEHR VOR!
    // ************************************************************

    /**
     * Gibt ein uebergebenes Character Array auf der Konsole aus.
     * 
     * @param text - a Character array
     */
    static void printCharArray(char[] text) {
        for (int i = 0; i < text.length; i++) {
            System.out.print(text[i]);
        }
        System.out.println();
    }

    /**
     * Bekommt ein Array uebergeben und verschiebt den Inhalt um eine Position nach
     * rechts. Das letzte Element wird somit zum ersten. Das verschobene Array wird
     * zurueckgegeben.
     */
    static char[] shiftRight(char[] bits) {
        char[] shiftedBits = new char[bits.length];
        for (int i = 0; i < bits.length; i++) {
            int targetPos = i + 1;
            if (targetPos >= bits.length) {
                targetPos = i - (bits.length - 1);
            }
            shiftedBits[targetPos] = bits[i];
        }
        return shiftedBits;
    }

    /**
     * Bekommt ein Array uebergeben und verschiebt den Inhalt um eine Possition nach
     * links im Array. Das erste Element wird somit zum letzten. Das verschobene
     * Array wird zurueckgegeben.
     */
    static char[] shiftLeft(char[] bits) {
        char[] shiftedBits = new char[bits.length];
        for (int i = 0; i < bits.length; i++) {
            int targetPos = i - 1;
            if (targetPos < 0) {
                targetPos = i + (bits.length - 1);
            }
            shiftedBits[targetPos] = bits[i];
        }
        return shiftedBits;
    }

    static int symbolLenght() {
        return 5;
    }

    /**
     * Erstellung des Binaercode fuer die Verschluesselung
     */
    static HashMap<Character, String> createEncryptionCodeMap() {
        HashMap<Character, String> codeMap = new HashMap<>();
        codeMap.put('A', "00000");
        codeMap.put('C', "00001");
        codeMap.put('E', "00010");
        codeMap.put('G', "00011");
        codeMap.put('I', "00100");
        codeMap.put('K', "00101");
        codeMap.put('M', "00110");
        codeMap.put('O', "00111");
        codeMap.put('Q', "01000");
        codeMap.put('S', "01001");
        codeMap.put('U', "01010");
        codeMap.put('W', "01011");
        codeMap.put('Y', "01100");
        codeMap.put('!', "01101");
        codeMap.put('?', "01110");
        codeMap.put('Z', "01111");

        codeMap.put('X', "10000");
        codeMap.put('V', "10001");
        codeMap.put('T', "10010");
        codeMap.put('R', "10011");
        codeMap.put('P', "10100");
        codeMap.put('N', "10101");
        codeMap.put('L', "10110");
        codeMap.put('J', "10111");
        codeMap.put('H', "11000");
        codeMap.put('F', "11001");
        codeMap.put('D', "11010");
        codeMap.put('B', "11011");
        codeMap.put('_', "11100");
        codeMap.put('=', "11101");
        codeMap.put('+', "11110");
        codeMap.put('-', "11111");
        return codeMap;
    }

    /**
     * Erstellung des Binaercode fuer die Entschluesselung
     */
    static HashMap<String, Character> createDecryptionCodeMap() {
        HashMap<String, Character> codeMap = new HashMap<>();
        codeMap.put("00000", 'A');
        codeMap.put("00001", 'C');
        codeMap.put("00010", 'E');
        codeMap.put("00011", 'G');
        codeMap.put("00100", 'I');
        codeMap.put("00101", 'K');
        codeMap.put("00110", 'M');
        codeMap.put("00111", 'O');
        codeMap.put("01000", 'Q');
        codeMap.put("01001", 'S');
        codeMap.put("01010", 'U');
        codeMap.put("01011", 'W');
        codeMap.put("01100", 'Y');
        codeMap.put("01101", '!');
        codeMap.put("01110", '?');
        codeMap.put("01111", 'Z');

        codeMap.put("10000", 'X');
        codeMap.put("10001", 'V');
        codeMap.put("10010", 'T');
        codeMap.put("10011", 'R');
        codeMap.put("10100", 'P');
        codeMap.put("10101", 'N');
        codeMap.put("10110", 'L');
        codeMap.put("10111", 'J');
        codeMap.put("11000", 'H');
        codeMap.put("11001", 'F');
        codeMap.put("11010", 'D');
        codeMap.put("11011", 'B');
        codeMap.put("11100", '_');
        codeMap.put("11101", '=');
        codeMap.put("11110", '+');
        codeMap.put("11111", '-');
        return codeMap;
    }
}