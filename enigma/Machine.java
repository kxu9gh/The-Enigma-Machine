package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author
 */
public class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls. ALLROTORS contains all the
     *  available rotors. */
    public Machine(Alphabet alpha, int numRotors, int pawls,
            Rotor[] allRotors) {
        this.alpha = alpha;
        this.numRotors = numRotors;
        this.allRotors = allRotors;
        this.pawls = pawls;
        usedRotors = new Rotor[numRotors+1];
        doAdvance = new boolean[numRotors+1];
        // FIXME - Assign any additional instance variables.
    }

    /** Return the number of rotor slots I have. */
    public int numRotors() {
        return numRotors; // FIXME - How do we access the number of Rotor slots I have?
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    public int numPawls() {
        return pawls; // FIXME - How do we access the number of pawls I have?
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of available rotors
     * (ROTORS[0] names the reflector). Initially, all rotors are set at their 0
     * setting.
     */
    public void insertRotors(String[] rotors) {     //initialize usedRotors; set the order of rotors; TEST: Not Yet
        // FIXME - How do we fill this Machine with Rotors, based on names of available
        // Rotors?
        // for(int r = 0; )
        for (int i = 0; i < rotors.length; i++) { // for each name of the actual rotor

            for (int j = 0; j < allRotors.length; j++) {
                String b = (allRotors[j].name()).toUpperCase();
                //System.out.println(b);
                if (rotors[i].toUpperCase().equals(b)) {
                    allRotors[j].order = i+1;
                    allRotors[j].mach = this;
                    usedRotors[i+1] = allRotors[j];
                    break;
                }
            }
        }
    }

    /**
     * Set my rotors according to SETTING, which must be a string of numRotors()-1
     * upper-case letters. The first letter refers to the leftmost rotor setting
     * (not counting the reflector).
     */
    public void setRotors(String setting) {             //take in String like "AXLE", set the rotors; TEST: Not Yet
        // FIXME - How do we set the positions of each Rotor in this Machine?
        for(int i = 0; i < setting.length(); i++){      //start from 2 to end, each set the corresponding char, no reflector
            usedRotors[i+2].set(setting.charAt(i)); 
        } 
    }

    /** Set the plugboard to PLUGBOARD. */
    public void setPlugboard(Permutation plugboard) {
        // FIXME - How do we assign our plugboard, based on a given Permutation?
        _plugboard = plugboard;
    }

    /**
     * Returns the result of converting the input character C (as an index in the
     * range 0..alphabet size - 1), after first advancing the machine.
     */
    public int convert(int c) {                     //convert a single index of char
        // HINT: This one is tough! Consider using a helper method which advances
        // the appropriate Rotors. Then, send the signal into the
        // Plugboard, through the Rotors, bouncing off the Reflector,
        // back through the Rotors, then out of the Plugboard again.
        //parts: 1: advance; 2: plugboard permutes; 3: rotors permute; 4: reflector;
        //5: rotors invert; 6: plugboard inverts
        advance();
        int op = _plugboard.permute(c);             //plugboard
        for(int i = numRotors; i >= 2; i--){        //rotors
            op = usedRotors[i].convertForward(op);
        }
        op = usedRotors[1].convertForward(op);      //reflector
        for(int i = 2; i <= numRotors; i++){        //rotors invert
            op = usedRotors[i].convertBackward(op);
        }
        op = _plugboard.invert(op);                 //plugboard invert
        return op; // FIXME - How do we convert a single character index?
    }

    /** Optional helper method for convert() which rotates the necessary Rotors. */
    private void advance() {                        //per character, rotors advance once; TEST: Not Yet
        // FIXME - How do we make sure that only the correct Rotors are advanced?
        for(int i = 1; i < numRotors; i++){
            //doAdvance[i] = usedRotors[i].rotates();
            doAdvance[i] = false;
        }
        for(int j = 2; j <= numRotors; j++){
            if(usedRotors[j].atNotch()){
                doAdvance[j - 1] = usedRotors[j - 1].rotates(); //the rotor before Notch advance if it rotates
                if(doAdvance[j - 1]){                      //if the left neighbor advances, this rotor advances;
                    doAdvance[j] = true;
                }
            }
        }
        doAdvance[numRotors] = true;
        for(int k = 2; k <= numRotors; k++){
            if(doAdvance[k]){
                usedRotors[k]._posn = wrap(usedRotors[k]._posn+1);
                //System.out.println(usedRotors[k]._posn);
            }
        }
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of the rotors
     * accordingly.
     */
    public String convert(String msg) {                 //convert the whole message; TEST: Not Yet
        // HINT: Strings are basically just a series of characters
        msg = msg.replace(" ", "");
        msg = msg.toUpperCase();
        StringBuilder ot = new StringBuilder();
        for(int i = 0; i < msg.length(); i++){
            if(alpha.contains(msg.charAt(i))){
                int a = alpha.toInt(msg.charAt(i));
                a = convert(a);
                ot.append(alpha.toChar(a));
            }

        }
        String a = ot.toString();
        //System.out.println(a);
        return a; // FIXME - How do we convert an entire String?
    }
    final int wrap(int p) {
        int r = p % alpha.size();
        if (r < 0) {
            r += alpha.size();
        }
        return r;
    }
    private Alphabet alpha;
    private int numRotors;
    private int pawls;
    private Rotor[] allRotors;
    private Rotor[] usedRotors;        //start from 1, the rotor in i position
    private Permutation _plugboard;
    private boolean[] doAdvance;       //the rotor will advance if this is true

    // FIXME - How do we keep track of my available Rotors/my Rotors/my pawls/my plugboard

    // FIXME: ADDITIONAL FIELDS HERE, IF NEEDED.

    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Machine.java enigma/Rotor.java enigma/FixedRotor.java enigma/Reflector.java enigma/MovingRotor.java enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Machine
    public static void main(String[] args) {

        CharacterRange upper = new CharacterRange('A', 'Z');
        MovingRotor rotorI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", upper),
                "Q");
        MovingRotor rotorII = new MovingRotor("II",
                new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", upper),
                "E");
        MovingRotor rotorIII = new MovingRotor("III",
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", upper),
                "V");
        MovingRotor rotorIV = new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", upper),
                "J");
        MovingRotor rotorV = new MovingRotor("V",
                new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)", upper),
                "Z");
        MovingRotor rotorVII = new MovingRotor("VII",
                new Permutation("(ANOUPFRIMBZTLWKSVEGCJYDHXQ)", upper),
                "ZM");
        FixedRotor rotorBeta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", upper));
        FixedRotor rotorGamma = new FixedRotor("Gamma",
                new Permutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)", upper));
        Reflector rotorB = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", upper));
        Reflector rotorC = new Reflector("C",
                new Permutation("(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW) (QZ) (SX) (UY)", upper));

        Rotor[] allRotors = new Rotor[10];
        allRotors[0] = rotorI;
        allRotors[1] = rotorII;
        allRotors[2] = rotorIII;
        allRotors[3] = rotorIV;
        allRotors[4] = rotorV;
        allRotors[5] = rotorBeta;
        allRotors[6] = rotorGamma;
        allRotors[7] = rotorB;
        allRotors[8] = rotorC;
        allRotors[9] = rotorVII;

//        Machine machine = new Machine(upper, 5, 3, allRotors);
//        machine.insertRotors(new String[]{"B", "BETA", "III", "IV", "I"});
//        machine.setRotors("AXLE");
//        machine.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", upper));
//
//        System.out.println(machine.numRotors() == 5);
//        System.out.println(machine.numPawls() == 3);
//        System.out.println(machine.convert(5) == 16);
//        System.out.println(machine.convert(17) == 21);
//        System.out.println(machine.convert("OMHISSHOULDERHIAWATHA").equals("PQSOKOILPUBKJZPISFXDW"));
//        System.out.println(machine.convert("TOOK THE CAMERA OF ROSEWOOD").equals("BHCNSCXNUOAATZXSRCFYDGU"));
//        System.out.println(machine.convert("Made of sliding folding rosewood").equals("FLPNXGXIXTYJUJRCAUGEUNCFMKUF"));

        Machine machine = new Machine(upper, 6, 4, allRotors);
        machine.insertRotors(new String[]{"C", "BETA", "VII", "V", "III", "I"});
        machine.setRotors("ATHOT");
        machine.setPlugboard(new Permutation("(JA) (CK) (SO) (NL)", upper));
        System.out.println(machine.convert("PHOEBEWASHERE").equals("JDFZTBTCNIJKC"));

    }
}
