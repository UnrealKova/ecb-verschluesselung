import java.util.HashMap;
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
            System.out.println("Geben Sie den zu verschlüsselnden Text ein:");
            String inputText = sc.next(); // Einlesen des Textes
            System.out.println("Geben Sie die Länger der Blöcke (r) an:");
            int blockSize = sc.nextInt(); // Einlesen der laenge r

            String encryptedText = encrypt(inputText, blockSize); // Verschluesseln der Nachricht
            System.out.println(encryptedText); // Ausgabe auf der Konsole
        }
        // Entschluesselung einer Nachricht
        if (userInput == 2) {
            System.out.println("Geben Sie den zu entschlüsselnden Text ein:");
            String inputText = sc.next(); // Einlesen des Textes
            System.out.println("Geben Sie die Länger der Blöcke (r) an:");
            int blockSize = sc.nextInt(); // Einlesen der Laenge r

            String decryptedText = decrypt(inputText, blockSize); // Entschluesseln der Nachricht
            System.out.println(decryptedText); // Ausgabe auf der Konsole
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

    static char[] symbolToBits(char symbol) {
        return null;
    }

    static char[] textToBits(String text) {
        return null;
    }

    //
    // Zerteilung in Bloecke der Laenge r
    //

    static char[] firstN(char[] bits, int n) {
        return null;
    }

    static char[] lastN(char[] bits, int n) {
        return null;
    }

    static char[][] bitsToBlocks(char[] bits, int size) {
        return null;
    }

    //
    // Verschluesselung von Bloecken
    //

    static char[][] encryptBlocks(char[][] blocks) {
        return null;
    }

    //
    // Zusammenfuegen von Bloecken
    //

    static char[] blocksToBits(char[][] blocks) {
        return null;
    }

    //
    // Umwandlung eines Binaercodes als Text
    //

    static char bitsToSymbol(char[] bits) {
        return ' ';
    }

    static char[] bitsToText(char[] bits, int size) {
        return null;
    }

    //
    // Verschluesselung eines Textes
    //

    static String encrypt(String text, int blockSize) {
        return null;
    }

    //
    // Entschluesselung von Bloecken
    //

    static char[][] decryptBlocks(char[][] blocks) {
        return null;
    }

    //
    // Entschluesselung eines Textes
    //

    static String decrypt(String text, int blockSize) {
        return null;
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