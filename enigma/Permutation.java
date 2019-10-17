package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author
 */
public class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    public Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
        // FIXME - Assign any additional instance variables.
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    public int size() {
        return _alphabet.size(); // FIXME - How do we ask the alphabet for its size?
    }

    /** Return the index result of applying this permutation to the character
     *  at index P in ALPHABET. */
    public int permute(int p) {
    	// NOTE: it might be beneficial to have one permute() method always call the other
        p = wrap(p);
        return wrap(_alphabet.toInt(permute(_alphabet.toChar(p))));

          // FIXME - How do we use our instance variables to get the index that P permutes to?
    }

    /** Return the index result of applying the inverse of this permutation
     *  to the character at index C in ALPHABET. */
    public int invert(int c) {
        // NOTE: it might be beneficial to have one invert() method always call the other
        c = wrap(c);
        return wrap(_alphabet.toInt(invert(_alphabet.toChar(c))));
        // FIXME - How do we use our instance variables to get the index that C inverts to?
    }

    /** Return the character result of applying this permutation to the index
     * of character P in ALPHABET. */
    public char permute(char p) {
    	// NOTE: it might be beneficial to have one permute() method always call the other
//         for(int i = 0; i < _cycles.length(); i++){
//             if(_cycles.charAt(i) == p){
//                 if(_cycles.charAt(i+1) != ')'){
//                     return _cycles.charAt(i+1);
//                 }
//                 int j = i-1;
//                 while(_cycles.charAt(j) != '('){
//                     j--;
//                 }
//                 return _cycles.charAt(j+1);
//             }
//         }

        String peT = _cycles.replace("(", " ");
        peT = peT.replace(")", " ");
        String[] perms = peT.split("\\s");   //final version; TEST: PASS
        for(String a : perms){
            int i;
            for(i = 0; i < a.length(); i++){
                if(p == a.charAt(i)){
                    break;
                }
            }
            if(i == a.length()-1){
                return a.charAt(0);
            }else if(i+1<a.length()){
                return a.charAt(i+1);
            }
        }

        return p;  // FIXME - How do we use our instance variables to get the character that P permutes to?
    }

    /** Return the character result of applying the inverse of this permutation
	 * to the index of character P in ALPHABET. */
    public char invert(char c) {
    	// NOTE: it might be beneficial to have one invert() method always call the other
        for(int i = 0; i < _cycles.length(); i++){
            if(_cycles.charAt(i) == c){
                if(_cycles.charAt(i-1) != '('){
                    return _cycles.charAt(i-1);
                }
                int j = i+1;
                while(_cycles.charAt(j) != ')'){
                    j++;
                }
                return _cycles.charAt(j-1);
            }
        }
        //System.out.println(c);

        return c;  // FIXME - How do we use our instance variables to get the character that C inverts to?

//        String peT = _cycles.replace("(", "");
//        peT = peT.replace(")", "");
//        String[] perms = peT.split("\\s");   //final version; TEST: PASS
//        for(String a : perms){
//            int i;
//            for(i = 0; i < a.length(); i++){
//                if(c == a.charAt(i)){
//                    break;
//                }
//            }
//            if(i == 0){
//                return a.charAt(a.length()-1);
//            }else if(i>0){
//                return a.charAt(i-1);
//            }
//        }
//
//        System.out.println(c);
//        return c;

    }

    /** Return the alphabet used to initialize this Permutation. */
    public Alphabet alphabet() {
        return _alphabet;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    private String _cycles;

    // FIXME - How do we store which letter permutes/inverts to which?

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED

    // Some starter code for unit tests. Feel free to change these up!
    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Permutation
    public static void main(String[] args) {
        Permutation perm = new Permutation("(ABCDEFGHIJKLM) (NOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        System.out.println(perm.size() == 26);
        System.out.println(perm.permute('Z') == 'N');
        System.out.println(perm.invert('B') == 'A');
        System.out.println(perm.permute(0) == 1);
        System.out.println(perm.invert(1) == 0);
    }
}
