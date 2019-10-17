//package enigma;

public class TestFunc {
    //paste function here
    static String  _cycles = "(ACOIERTY) (BSQ)";
    public static char permute(char p) {
    	// NOTE: it might be beneficial to have one permute() method always call the other
        for(int i = 0; i < _cycles.length(); i++){
            if(_cycles.charAt(i) == p){
                if(_cycles.charAt(i+1) != ')'){
                    return _cycles.charAt(i+1);
                }
                int j = i-1;
                while(_cycles.charAt(j) != '('){
                    j--;
                }
                return _cycles.charAt(j+1);
            }
        }
        return p;  // FIXME - How do we use our instance variables to get the character that P permutes to?
    }

    public static void main(String[] args){
        char input = 'Q';
        //System.out.println(permute(input));
        String e = "A";
        char a = 'A';
        if(a =='A' || "ST" == "ST"){
            e = "bop";
        }
        System.out.println(e.length());
    }
}