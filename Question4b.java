public class Question4b {

        static class Tree {
            int val;
            Tree left, right;
    
            Tree(int val) {
                this.val = val;
                left = right = null;
            }
        }
    
        static void inorder(Tree root, double target, int[] closestValues, double[] minDiff, int x) {
            if (root == null) return;
    
            inorder(root.left, target, closestValues, minDiff, x);  // recursion happens till the root is null i.e reaching the leftmost part of tree 
            
            // Calculate the absolute difference between the current node's value and the target
            double diff = Math.abs(root.val - target);
            
            // Check if the current node's value is closer to the target than the current closest values
            for (int i = 0; i < x; i++) {
                if (diff < minDiff[i]) {        //Math.abs(-1.8)>Math.abs(1.8)

                    // Shift elements to the right to make space for the new closest value
                    //this loop runs till j>i i.e as soon as the loop goes to the i=1 this loop stops and the loop will only acess the first position of array of mindiff and closestvalue

                    for (int j = x - 1; j > i; j--) {   
                        closestValues[j] = closestValues[j - 1];   //j=1 i.e j=(x-1)=(2-1), in position 1 the node of postion 0 will be stored by replacing the previous value of position 1
                        System.out.println("value changed from value of pos 1 to 2");
                        minDiff[j] = minDiff[j - 1];         // similarly update the minimum difference of position 1 with the value of position 0 so that the position 0 value can be updated with new value
                    }
                    closestValues[i] = root.val;    //updating the closesValue node array with new value 
                    minDiff[i] = diff;          //updating the minDiff array with new value 
                    break;
                }
            }
    
            inorder(root.right, target, closestValues, minDiff, x);  // 
        }
    
     
    
        public static void main(String[] args) {
            // Create the binary search tree
            Tree root = new Tree(4);
            root.left = new Tree(2);
            root.left.left = new Tree(1);
            root.left.right = new Tree(3);
            root.right = new Tree(5);

            double target = 3.8;
            int x = 2;

            int[] closestValues = new int[x];     // initializing the array with the size of x
            double[] minDiff = new double[x];
                
            for (int i = 0; i < x; i++) {
                minDiff[i] = Double.MAX_VALUE;     //initializing default value inside the minDiff array which is the maximum value that a double data type can have
            }

            inorder(root, target, closestValues, minDiff, x);    //running the function

            System.out.print("Output: ");
            for (int value : closestValues) { 
                System.out.print(value + " ");   //printing the closest value array
            }
        }
    }
    


    

