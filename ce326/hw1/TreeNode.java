package ce326.hw1;

public class TreeNode {
    TreeNode left;//left pointer
    TreeNode right;//right pointer
    TreeNode parent;//parent pointer
    int intNum;//integer num
    double dNum;//double num
    int decide;// 0 ->double num 1 ->integer num 2->operator
    char operator;//operator

    public TreeNode (){

    }

    public TreeNode CreateNode (){

        TreeNode NewNode = new TreeNode();

        return NewNode;
    }
}
