package enigma;

public class GeneralAlphabet extends Alphabet {
    public GeneralAlphabet(String setting){
        _setting = setting;
    }

    @Override
    int size(){
        return _setting.length();
    }

    @Override
    boolean contains(char ch){
        for(int i = 0; i < _setting.length(); i++){
            if(_setting.charAt(i) == ch){
                return true;
            }
        }
        return false;
    }

    @Override
    char toChar(int index){
        return _setting.charAt(index);
    }

    @Override
    int toInt(char ch) {
        for(int i = 0; i < _setting.length(); i++){
            if(_setting.charAt(i) == ch){
                return i;
            }
        }
        return -1;
    }

    private String _setting;
}
