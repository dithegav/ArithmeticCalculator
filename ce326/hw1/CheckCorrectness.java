package ce326.hw1;
import java.util.Objects;
import java.util.regex.*;

public class CheckCorrectness {
    String str;

    public CheckCorrectness (String str){
        this.str = str;
    }

    public void CheckParenthesis (){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("[()]");
        Matcher m = p.matcher(strNoWhitspace);

        int flag = 0;
        while(m.find()) {
        
            if (Objects.equals(strNoWhitspace.substring(m.start(), m.end()), "(")){
                flag = flag + 1;
            }
            else if (Objects.equals(strNoWhitspace.substring(m.start(), m.end()), ")")){
                flag = flag - 1;
            }
        }

        if (flag > 0){
            System.out.println("\n[ERROR] Not closing opened parenthesis");
            System.exit (0);
        }
        else if (flag < 0){
            System.out.println("\n[ERROR] Closing unopened parenthesis");
            System.exit  (0);
        }
    }

    public void CheckCharacters (){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("[^ \\( \\) \\+ \\- \\* \\/ \\^ x \\d \\s \\. ]");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);
    
        if (m.find()){
            System.out.println("\n[ERROR] Invalid character");
            System.exit (0);
        }
    }

    public void CheckConsecutiveNum (){
        Pattern p = Pattern.compile("\\d\\s+\\d");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(str);

        if (m.find()){
            System.out.println("\n[ERROR] Consecutive operands");
            System.exit (0);
        }
    }

    public void CheckOperatorInEdge (){

        String strNoWhitspace = str.replaceAll(" ", "");

        //first for the left
        Pattern p = Pattern.compile("^[ \\+ | \\- | \\* | \\/ | x | \\^]");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);

        if (m.find()){
            System.out.println("\n[ERROR] Starting or ending with operator");
            System.exit (0);
        }

        //then check rigth edge
        Pattern p1 = Pattern.compile("[ \\+ | \\- | \\* | \\/ | x | \\^]");  // d -> numbers, s-> whitespace
        Matcher m2 = p1.matcher(strNoWhitspace);

        while(m2.find()) {
    
            if (m2.start() == strNoWhitspace.length()-1){
                System.out.println("\n[ERROR] Starting or ending with operator");
                System.exit (0);
            }
        }
    }

    public void CheckConsecutiveOperators(){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("[\\+\\-\\*\\/x\\^]{2}");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);

        if (m.find()){
            System.out.println("\n[ERROR] Consecutive operators");
            System.exit (0);
        }
    }

    public void CheckParenthesesOperator (){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("\\([\\+\\-\\*\\/x\\^]");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);

        if (m.find()){
            System.out.println("\n[ERROR] Operator appears after opening parenthesis");
            System.exit (0);
        }

        Pattern p1 = Pattern.compile("[\\+\\-\\*\\/x\\^]\\)");  // d -> numbers, s-> whitespace
        Matcher m1 = p1.matcher(strNoWhitspace);

        if (m1.find()){
            System.out.println("\n[ERROR] Operator appears before closing parenthesis");
            System.exit (0);
        }
    }
    
    public void CheckCorrectUsageParenthesis (){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("\\)\\(");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);

        if (m.find()){
            System.out.println("\n[ERROR] ')' appears before opening parenthesis");
            System.exit (0);
        }
    }

    public void CheckNumBeforeParenthesis (){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("\\d\\(");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);

        if (m.find()){
            System.out.println("\n[ERROR] Operand before opening parenthesis");
            System.exit (0);
        }
    }

    public void CheckNumAfterParenthesis (){

        String strNoWhitspace = str.replaceAll(" ", "");

        Pattern p = Pattern.compile("\\)\\d");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(strNoWhitspace);

        if (m.find()){
            System.out.println("\n[ERROR] Operand after closing parenthesis");
            System.exit (0);
        }
    }
}
