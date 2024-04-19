package ce326.hw1;

import java.util.regex.*;
//Use Object library for string compare
import java.util.Objects;

public class ArithmeticCalculator {
    static TreeNode root;

    public void Hwk1ArithmeticalCalculator (String correctStr){
        //create the root fo the tree that can be used from the whole class
        //i have not created a tree only the root as a node
        //no need for more
        root = CreateTree (null, correctStr);
    }

    public static void CheckResults(String line){
        //As i created a different class for the checks that need to happen in the math expression
        //thats why i created this method in order to run all those methods in the file CheckCorrectness
        CheckCorrectness check = new CheckCorrectness(line);
        
        check.CheckParenthesis();
        check.CheckCharacters();
        check.CheckConsecutiveNum();
        check.CheckOperatorInEdge();
        check.CheckConsecutiveOperators();
        check.CheckParenthesesOperator();
        check.CheckCorrectUsageParenthesis();
        check.CheckNumBeforeParenthesis();
        check.CheckNumAfterParenthesis();


    }

    public TreeNode CreateNewNode (TreeNode ParentNode){
        //Simple method that creates the nect node needed for the tree
        //the code of the creation is ina different file named TreeNode
        TreeNode NewNode = new TreeNode();

        //just connecting the newnodes pointer parent to the parent given in the method
        NewNode.parent = ParentNode;

        return NewNode;
    }

    public TreeNode CreateTree (TreeNode Node, String substring){
        int simpleOpIn=0;//if this flag is one then dont bother to check for the rest priorities
        int secondOpIn=0;//if this is one then don tbnother to check for the ^ operator
        int lengthBreak=0;//keep the position of the leastpriority operator
        int flagParenthesis=0, position=1; //this flags helps with the position of the parenthesis whether their is any
        char holdOp=0; //keep the 

        for (int i=0; i < substring.length() ; i++){

            //the plan to avoid the parenthesis if you find any is to wait untill the flagparenthesis is 0 
            //only if it has changed its value to 1 first and you avoid anything that happens between this time
            if (substring.charAt(i) == '(' ){

                if (flagParenthesis == 0){
                    position = i;
                }

                flagParenthesis += 1;
                continue;
            }
            else if (substring.charAt(i) == ')'){
                flagParenthesis -= 1;
            }

            //if the expression is covered by the same parenthesis then just destroy it and continue with new string
            if (flagParenthesis == 0){

                if (position == 0 && i == substring.length()-1 && substring.charAt(i) == ')'){
                    String sub = substring.substring(1, substring.length()-1);
                    substring = sub;
                    position = 1;
                    i = -1;
                    continue;
                }
            }
            else if (flagParenthesis != 0){
                continue;
            }

            if (substring.charAt(i) == '+'){
                simpleOpIn = 1;
                holdOp = substring.charAt(i);
                lengthBreak = i;
            }
            else if (substring.charAt(i) == '-'){
                
                simpleOpIn = 1;
                holdOp = substring.charAt(i);
                lengthBreak = i;
            }
            else if (simpleOpIn == 0 && (substring.charAt(i) == '*' || substring.charAt(i) == 'x')){
                secondOpIn = 1;
                holdOp = substring.charAt(i);
                lengthBreak = i;
            }
            else if (simpleOpIn == 0 && substring.charAt(i) == '/'){
                secondOpIn = 1;
                holdOp = substring.charAt(i);
                lengthBreak = i;
            }
            else if (secondOpIn == 0 && simpleOpIn == 0 && substring.charAt(i) == '^'){
                holdOp = substring.charAt(i);
                lengthBreak = i;
            }
        }


        if (lengthBreak != 0){
            //if its an operator then do the approriate anathesis in the new node
            TreeNode newNode = CreateNewNode(Node);

            newNode.decide = 2;
            newNode.operator = holdOp;
            
            if (Node != null){
                if (Node.left == null){
                    Node.left = newNode;
                }
                else {
                    Node.right = newNode;
                }
            }

            //create the new string using string builder to adjust the ends we need in each case
            StringBuilder str = new StringBuilder(substring);

            CreateTree (newNode, str.substring(0,lengthBreak));

            CreateTree (newNode, str.substring(lengthBreak+1,substring.length()));

            return newNode;
        }
        else if (substring.length() >= 1){
            //now we have a number so decide wether its a double or integer
            TreeNode newNode = CreateNewNode(Node);

            Pattern p = Pattern.compile("\\.");
            Matcher m = p.matcher(substring);

            if (m.find()){
                //ita a double number
                newNode.decide = 0; //it is a double number
                newNode.dNum = Double.parseDouble(substring);
            }
            else {
                //ita a int number
                newNode.decide = 1; //it is a int number
                newNode.intNum = Integer.parseInt(substring);
            }

            if (Node != null){
                if (Node.left == null){
                    Node.left = newNode;
                }
                else {
                    Node.right = newNode;
                }
            }

            return newNode;
        }
        else {
            return null;
        }
        
    }

    public String HelpPostFix (TreeNode Node, StringBuffer tostr){

        if (Node == null){
            return tostr.toString();
        }

        if (Node.left != null){
            HelpPostFix(Node.left, tostr);
        }
        
        if (Node.right != null){
            HelpPostFix(Node.right, tostr);
        }

        //in postorder implementation you print after right before the recursion returns
        //so the root value goes last
        if (Node.decide == 0){
            //double number
            tostr.append(Node.dNum+" ");
        }
        else if (Node.decide == 1){
            //intnum
            tostr.append(Node.intNum+" ");
        }
        else {
            //operator
            tostr.append(Node.operator+" ");
        }

        return tostr.toString();
    }

    public String toString (){
        //in order to not have pedia in this method we create a stringbuffer 
        //and call another method that has the appropriate pedia 
        String temp;

        StringBuffer str = new StringBuffer();

        temp = HelpPostFix (root, str);

        return temp;
    }

    public double calculate (TreeNode Node){

        if (Node.left == null && Node.right == null){
            if (Node.decide == 0){
                return Node.dNum;
            }
            else {
                return Node.intNum;
            }
        }

        double leftValue = calculate (Node.left);

        double rightValue = calculate (Node.right);

        //withn recursio now you have left and right child value and use the parent operator to calculate and "save" the value
        //continue untill you reach the root and all the time return the valuye as a double

        if (Node.operator == '+'){
            return (leftValue + rightValue);
        }
        else if (Node.operator == '-'){
            return (leftValue - rightValue);
        }
        else if (Node.operator == '*' || Node.operator == 'x'){
            return (leftValue * rightValue);
        }
        else if (Node.operator == '/'){
            return (leftValue / rightValue);
        }
        else{
            //Only if you have power '^'
            return Math.pow(leftValue ,rightValue);
        }
        
    }

    public String toDotString (TreeNode Node, StringBuffer tostr){
        //with the append method in an str i stack that string with th emessage that the dot file should have
        //following the excersises instractions
        if (Node == null){
            return tostr.toString();
        }

        if (Node.decide == 0){
            //double number
            tostr.append("\t" + Node.hashCode() + " [label=\"" + Node.dNum + "\"]\n");
        }
        else if (Node.decide == 1){
            //intnum
            tostr.append("\t" + Node.hashCode() + " [label=\"" + Node.intNum + "\"]\n");
        }
        else {
            //operator
            tostr.append("\t" + Node.hashCode() + " [label=\"" + Node.operator + "\"]\n");
        }

        if (Node.left != null){

            tostr.append ("\t" + Node.hashCode() + " -- " + Node.left.hashCode() + "\n");

            toDotString(Node.left, tostr);
        }
        
        if (Node.right != null){

            tostr.append ("\t" + Node.hashCode() + " -- " + Node.right.hashCode() + "\n");

            toDotString(Node.right, tostr);
        }

        return tostr.toString();

    }

    public static void main(String []args) {

        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Expression: ");
        String line = sc.nextLine();

        ArithmeticCalculator tree = new ArithmeticCalculator();

        CheckResults(line);
        
        String strNoWhitspace = line.replaceAll(" ", "");

        tree.Hwk1ArithmeticalCalculator(strNoWhitspace);

        //java.util.Scanner scan = new java.util.Scanner(System.in);
        line = sc.nextLine();
        
        
        Pattern p = Pattern.compile("[sdc]");  // d -> numbers, s-> whitespace
        Matcher m = p.matcher(line);

        if (m.find()){
            if (Objects.equals(line, "-s")){
                
                System.out.println("\nPostfix: " +tree);
            }
            else if (Objects.equals(line, "-c")){
                Double result = tree.calculate(root);

                System.out.printf("\nResult: %.6f\n", result);
            }
            else if (Objects.equals(line, "-d")){
                StringBuffer DotString = new StringBuffer();

                System.out.println ("graph ArithmeticExpressionTree {");

                tree.toDotString(root, DotString);

                System.out.println (DotString + "}");
            }
        }
        
        sc.close();
    }
}
