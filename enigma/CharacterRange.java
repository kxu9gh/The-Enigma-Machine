package enigma;

import static enigma.EnigmaException.*;

/** An Alphabet consisting of the Unicode characters in a certain range in
 *  order.
 *  @author P. N. Hilfinger
 */
public class CharacterRange extends Alphabet {

    /** An alphabet consisting of all characters between FIRST and LAST,
     *  inclusive. */
    public CharacterRange(char first, char last) {
        _first = Character.toUpperCase(first);
        _last = Character.toUpperCase(last);
        type = true;
        if (_first > _last) {
            throw error("empty range of characters");
        }
    }

    public CharacterRange(String setting){
        _setting = setting;
        type = false;
        System.out.println("constructor: "+false);
    }

    @Override
    int size() {
        if(type){return _last - _first + 1;}
        else{return _setting.length();}
    }

    @Override
    boolean contains(char ch) {
        if(type){return ch >= _first && ch <= _last;}
        else{
            for(int i = 0; i < _setting.length(); i++){
                if(_setting.charAt(i) == ch){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    char toChar(int index) {
        if(type){
            if (!contains((char) (_first + index))) {
                throw error("character index out of range");
            }
            return (char) (_first + index);
        }
        else{
            return _setting.charAt(index);
        }

    }

    @Override
    int toInt(char ch) {
        if(type){
            if (!contains(ch)) {
                System.out.println(ch);
                System.out.println(type);
                throw error("character out of range");
            }
            return ch - _first;
        }
        else{
            for(int i = 0; i < _setting.length(); i++){
                if(_setting.charAt(i) == ch){
                    return i;
                }
            }
            return -1;
        }

//        for(int i = 0; i < _setting.length(); i++){
//            if(_setting.charAt(i) == ch){
//                return i;
//            }
//        }
//        return -1;

    }

    /** Range of characters in this Alphabet. */
    private char _first, _last;
    private String _setting;
    private boolean type;

    public static void main(String[] args){
        CharacterRange a = new CharacterRange('A', 'I');
        System.out.println(a.contains('t'));
        System.out.println(a.toInt('E'));
        System.out.println(a.toChar(3));
        System.out.println(a.size());
    }
}
