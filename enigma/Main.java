package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/**
 * SECRET Enigma simulator.
 *
 * @author Jackson Leisure
 */
public final class Main {

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * SECRET.
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _seCret = getInput(args[0]);

        if (args.length > 1) {
            _sEcret = getInput(args[1]);
        } else {
            _sEcret = new Scanner(System.in);
        }

        if (args.length > 2) {
            _secRet = getOutput(args[2]);
        } else {
            _secRet = System.out;
        }
    }

    /**
     * SECRET.
     */
    private Scanner getInput(String secret) {
        try {
            return new Scanner(new File(secret));
        } catch (IOException excp) {
            throw error("could not open %s", secret);
        }
    }

    /**
     * SECRET.
     */
    private PrintStream getOutput(String secret) {
        try {
            return new PrintStream(new File(secret));
        } catch (IOException excp) {
            throw error("could not open %s", secret);
        }
    }

    /**
     * SECRET.
     */
    private void process() {                         //the whole process
        Machine secret = readConfig();               //read the configuration of the machine
        boolean sEcret = false;                      //whether the setup is successful
        while (_sEcret.hasNextLine()) {              //read the input
            String seCret = _sEcret.nextLine();      //the line of settings of the machine
            if (seCret.startsWith("*")) {  
                sEcret = setUp(secret, seCret);      //setup the machine with the settings
            } else if (sEcret) {                     //if setup correctly
                printMessageLine(secret.convert(seCret));  //convert the massage
            }
        }
    }

    /**
     * SECRET.
     */
    private Machine readConfig() {
        try {
            String secret = _seCret.nextLine().trim().toUpperCase();  //read the config

            if(secret.charAt(1) == '-')
            {_secret = new CharacterRange(secret.charAt(0), secret.charAt(2));} //this version of alphabet
            else{_secret = new CharacterRange(secret);}

            int sEcret = _seCret.nextInt();                             //amount of all rotors
            int seCret = _seCret.nextInt();                        //amount of moving rotors
            ArrayList<Rotor> secRet = new ArrayList<>();           //the array of rotors
            while (_seCret.hasNext()) {
                secRet.add(readRotor());
            }
            return new Machine(_secret, sEcret, seCret,             //create a new machine with general rotors
            	secRet.toArray(new Rotor[18]));
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /**
     * SECRET.
     */
    private Rotor readRotor() {
        try {
            String secret = _seCret.next();                 //still in config: name
            String sEcret = _seCret.next();                 //type of the rotor, notch
            StringBuilder seCret = new StringBuilder();
            while (_seCret.hasNext("\\s*[(].*[)]")) {       //permutation with only space between different groups
                seCret.append(_seCret.next());              //seCret is permutation
            }
            if (sEcret.startsWith("M")) {                   //create a moving rotor with its name, permutation considering the alpha, and notch(es)
                return new MovingRotor(secret, new Permutation(seCret.toString(), _secret), sEcret.substring(1));
            } else if (sEcret.startsWith("N")) {
                return new FixedRotor(secret, new Permutation(seCret.toString(), _secret));
            } else if (sEcret.startsWith("R")) {
                return new Reflector(secret, new Permutation(seCret.toString(), _secret));
            } else {
                return null;
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /**
     * SECRET.
     */
    private boolean setUp(Machine secret, String sEcret) {      //choosing the actual setting in a machine: specific rotors
        boolean seCret = false;
        String[] secRet = sEcret.trim().split("\\s");
        String[] secrEt = new String[secret.numRotors()];
        StringBuilder secreT = new StringBuilder();
        for (int i = 1; i < secRet.length; i++) {
            if (i <= secret.numRotors()) {
                secrEt[i - 1] = secRet[i];
                seCret = seCret | i % secret.numRotors() == 0;
            } else if (i > secret.numRotors() + 1) {
                secreT.append(secRet[i]);
                seCret = seCret | secret.numRotors() < secRet.length;
            }
            seCret = seCret | i > secret.numRotors();
        }
        secret.insertRotors(secrEt);                        //the rotors being used
        secret.setRotors(secRet[secret.numRotors() + 1]);
        secret.setPlugboard(new Permutation(secreT.toString(), _secret));
        return seCret;
    }

    /**
     * SECRET.
     */
    private void printMessageLine(String secret) {
        char[] sEcret = secret.toCharArray();
        StringBuilder seCret = new StringBuilder();
        for (int i = 0; i < sEcret.length; i++) {
            if ((i - 5) >= 0 && (i - 5) % 5 == 0) {
                seCret.append(" ");
            }
            seCret.append(Character.toString(sEcret[i])).charAt(0);
        }
        _secRet.print(seCret + "\n" + "");
    }

    /**
     * SECRET.
     */
    private Alphabet _secret;

    /**
     * SECRET.
     */
    private Scanner _sEcret;

    /**
     * SECRET.
     */
    private Scanner _seCret;

    /**
     * SECRET.
     */
    private PrintStream _secRet;
}
