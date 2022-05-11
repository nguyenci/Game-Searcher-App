import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class contains methods for removing nodes from red black trees, as well as methods for searching for a list of IDs
 * and an in order traversal. Implements the IRBListSearcher interface and extends RedBlackTree obviously
 * @author Cy Schuelter
 *
 */
public class RBListSearcher extends RedBlackTree<Game> implements IRBListSearcher<Game> {

    /**
     * This method takes in a list of video game IDs and searches the tree for these IDs. It returns a list of all the 
     * games in the tree corresponding to these IDs
     */
    @Override
    public List<Game> search(List<Integer> IDList) {
        ArrayList<Game> gameList = new ArrayList<Game>();
        Node<Game> currGame;
        for (int i=0; i < IDList.size(); ++i) {
            currGame = this.BSTSearch(IDList.get(i));
            gameList.add(currGame.data);
        }

        return gameList;
    }

    /**
     * This method removes a game with a certain ID from the tree. It basically just calls a helper method becuase it
     * made more sense to do this taking a node as input than an id
     */
    @Override
    public Game remove(int id) {
        Node<Game> rNode = this.BSTSearch(id);
        if(rNode.parent == null && rNode.leftChild == null && rNode.rightChild == null){
            this.root =null;
            this.size--;
            return rNode.data;
        }
        Game rGame = remove(rNode);


        return rGame;
    }

    /**
     * This method removes a certain node containing a certain game from the tree. It hands 3 different cases, based off
     * how many children the node-to-be-removed has
     * @param rNode the node we want to remove
     * @return the game removed from the tree
     */
    public Game remove(Node<Game> rNode) {
        Node<Game> returnNode = rNode;
        // first check for case0 where child is a red node, and then where child is a black node
        if (rNode.rightChild == null && rNode.leftChild == null) {
            if (rNode.blackHeight == 0) {
                // take it out with regular BST remove, no potential violations caused
                this.BSTRemove(rNode);
                returnNode = rNode;
                root.blackHeight = 1;
            } else {
                Game game = case0bHelper(rNode);
                root.blackHeight = 1;
                this.BSTRemove(rNode); //the violations should have been solved by the helper method
                return game;
            }

            // take care of case 1: rNode has 1 child
        } else if (hasOneChild(rNode)) {

            // set the child of rNode to black
            if (rNode.leftChild == null) {
                rNode.rightChild.blackHeight = 1;
            } else {
                rNode.leftChild.blackHeight = 1;
            }

            // then remove rNode and return its game
            root.blackHeight = 1;
            returnNode = rNode;
            this.BSTRemove(rNode);

            // lastly if rNode has 2 children, shift it to a case 1 or 2
        } else {
            case2Shifter(rNode);
        }

        return (Game) returnNode.data;
    }

    /**
     * This method handles the many confusing cases when a black node with no children is removed from the tree.
     * @param rNode the node we want to remove
     * @return the game removed from the tree
     */
    private Game case0bHelper(Node<Game> rNode) {
        Game returnGame = null;
        //first handle case 3: sibling of double black is red
        //if either on of rNode's parents children are red, we are in case 3
        if (rNode.parent.rightChild.blackHeight == 0 || rNode.parent.leftChild.blackHeight == 0) {
            if (!rNode.isLeftChild()) {
                colorSwap(rNode.parent);
                colorSwap(rNode.parent.leftChild);
                rotate(rNode.parent.leftChild, rNode.parent);
                root.blackHeight = 1;
                returnGame = case0bHelper(rNode);
            } else {
                colorSwap(rNode.parent);
                colorSwap(rNode.parent.rightChild);
                rotate(rNode.parent.rightChild, rNode.parent);
                root.blackHeight = 1;
                returnGame = case0bHelper(rNode);
            }
        }

        //next handle case 1 (for left child): DB's sib is black w 1 or more red children
        else if (rNode.isLeftChild() && rNode.parent.rightChild.blackHeight ==1 &&
                hasRedChild(rNode.parent.rightChild)) {

            rNode.blackHeight = 2;
            //make sure the red child of DB's sibling is on its right side
            if (rNode.parent.rightChild.rightChild == null || rNode.parent.rightChild.rightChild.blackHeight != 0) {
                colorSwap(rNode.parent.rightChild);
                colorSwap(rNode.parent.rightChild.leftChild);
                rotate(rNode.parent.rightChild.leftChild, rNode.parent.rightChild);
                root.blackHeight = 1;
            }

            //color swap parent and sibling of DB
            colorSwap(rNode.parent);
            colorSwap(rNode.parent.rightChild);
            rotate(rNode.parent.rightChild, rNode.parent);
            root.blackHeight = 1;
            //make DB regular black and red child of its sibling black
            rNode.blackHeight = 1;
            rNode.parent.parent.rightChild.blackHeight = 1;
            rNode.parent.parent.blackHeight = 1;             //NOTE: these two lines weren't described in notes
            rNode.parent.blackHeight = 1;                    //NOTE: these two were added to keep black heights as expected
            returnGame = (Game)rNode.data;
            //  BSTRemove(rNode);

            //then handle the other case 1 where the node to be removed is a right child
        } else if (!rNode.isLeftChild() && rNode.parent.leftChild.blackHeight == 1 &&
                hasRedChild(rNode.parent.leftChild)) {
            rNode.blackHeight = 2;

            //make sure red child of DB's sibling is on opposite side as DB
            if (rNode.parent.leftChild.leftChild == null || rNode.parent.leftChild.leftChild.blackHeight != 0) {
                colorSwap(rNode.parent.leftChild);
                colorSwap(rNode.parent.leftChild.rightChild);
                rotate(rNode.parent.leftChild.rightChild, rNode.parent.leftChild);
                root.blackHeight = 1;

            }

            //color swap parent and sibling of DB
            colorSwap(rNode.parent);
            colorSwap(rNode.parent.leftChild);
            rotate(rNode.parent.leftChild, rNode.parent);
            root.blackHeight = 1;
            //make DB regular black and red child of its sibling black
            rNode.blackHeight = 1;
            rNode.parent.parent.leftChild.blackHeight = 1;
            rNode.parent.parent.blackHeight = 1;  //NOTE: not sure about this line
            rNode.parent.blackHeight = 1;         //these were added to resolve erros but kinda violated your notes
            returnGame = (Game) rNode.data;
            //      BSTRemove(rNode);

            //finally handle case 2 where DB's sibling is black w no red children
        } else {
            if (rNode.isLeftChild()) {
                rNode.parent.rightChild.blackHeight = 0;
                rNode.parent.blackHeight++;
                root.blackHeight = 1;
                if (rNode.parent.blackHeight == 2) {
                    case0bHelper(rNode.parent);
                } else {
                    returnGame = (Game) rNode.data;
                }

            } else {
                rNode.parent.leftChild.blackHeight = 0;
                rNode.parent.blackHeight++;
                root.blackHeight = 1;
                if (rNode.parent.blackHeight == 2) {
                    case0bHelper(rNode.parent);
                } else {
                    returnGame = (Game) rNode.data;
                }
            }
        }

        return returnGame;
    }

    /**
     * This method handles case 2 of the larger remove algorithm. It simply shifts the tree so that either the case 1 or 
     * case 2 scenario will occur
     * @param rNode the node we want removed
     */
    private void case2Shifter(Node<Game> rNode) {
        boolean left = false;
        Node<Game> leastSuccessor = findLeastSuccessor(rNode);
        Node<Game> temp = leastSuccessor.parent;
        //decide if leastSuccessor is a right or left child
        if (leastSuccessor.isLeftChild()) {
            left = true;
        }

        //copy least successsor's data into the node you want removed
        rNode.data = leastSuccessor.data;

        //now call remove on the least successor, this will give a case 1 or 2
        if (left) {
            this.remove(temp.leftChild);
        } else {
            this.remove(temp.rightChild);
        }

        root.blackHeight = 1;
        return;
    }

    /**
     * Method to find the least successor of a certain node. The least successor is the one we would replace rNode with
     * during BST removes
     * @param node the node whose least successor we want to find
     * @return the node that is the least successor
     */
    public Node<Game> findLeastSuccessor(Node<Game> node) {
        Node<Game> currNode = node.rightChild;
        while (currNode.leftChild != null) {
            currNode = currNode.leftChild;
        }
        return currNode;
    }

    /**
     * Tells if a certain node has exactly one child
     * @param node the node whos children are in question
     * @return true if the node has exactly one child, false otherwise
     */
    private boolean hasOneChild(Node<Game> node) {
        if (node.leftChild == null && node.rightChild != null) {
            return true;
        }
        if (node.leftChild != null && node.rightChild == null) {
            return true;
        }
        return false;
    }

    /**
     * This method swaps the color of a certain node from red to black or black to red
     * @param node the node whose color will be swapped
     */
    private void colorSwap(Node<Game> node) {
        if (node.blackHeight == 1) {
            node.blackHeight = 0;
        } else {
            node.blackHeight = 1;
        }
    }

    /**
     * Tells if a certain node has at least one red child
     * @param node the node whos children are in question
     * @return true if the node has at least one red child, false otherwise
     */
    public boolean hasRedChild(Node<Game> node) {
        if (node.rightChild != null && node.rightChild.blackHeight == 0) {
            return true;
        }
        if (node.leftChild != null && node.leftChild.blackHeight == 0) {
            return true;
        }


        return false;
    }

    /**
     * Returns an in-oder traversal of the tree. Uses a recursive helper method to do so
     */
    @Override
    public String traversal() {
        String treeString = "";
        treeString = traversalRecursive(this.root, treeString);

        return treeString;
    }

    /**
     * Recursive helper for traversal method
     * @param subRoot the root of the current subtree being traversed
     * @param treeString the total string representation of the tree so far
     * @return string representation of the tree in order
     */
    public String traversalRecursive(Node<Game> subRoot, String treeString) {
        if (subRoot.leftChild != null) {
            treeString = traversalRecursive(subRoot.leftChild, treeString);
        }
        treeString = treeString + subRoot.data.toString() + ", ";
        if (subRoot.rightChild != null) {
            treeString = traversalRecursive(subRoot.rightChild, treeString);
        }


        return treeString;
    }

    /**
     * Performs a regular removal operation from a BST. Does not take into account any RB properties
     * @param removeNode
     */
    public void BSTRemove(Node<Game> removeNode) {
        Node<Game> replaceNode;

        if (removeNode.parent == null && removeNode.rightChild == null && removeNode.leftChild == null) {
            root = null;
            size--;
            return;
        }

        //first take care of the case where the node to be removed has no children
        if (removeNode.rightChild == null && removeNode.leftChild == null) {
            if (removeNode.isLeftChild()) {
                removeNode.parent.leftChild = null;
                this.size--;
            } else {
                removeNode.parent.rightChild = null;
                this.size--;
            }

            //next handle case where node to be removed has 1 child
        } else if (hasOneChild(removeNode)) {

            //rightChild is null, so we need to replace removeNode with its greatest predecessor
            if (removeNode.rightChild == null) {
                replaceNode = removeNode.leftChild;
                int s=0;
                while (replaceNode.rightChild != null) {
                    replaceNode = replaceNode.rightChild;
                    ++s;
                }
                removeNode.data = replaceNode.data;
                if (s == 0) {
                    replaceNode.parent.leftChild = null;
                    this.size--;
                } else {
                    replaceNode.parent.rightChild = null;
                    this.size--;
                }

            } else { //left child is null, so find least successor and replace
                replaceNode = removeNode.rightChild;
                int s=0;
                while (replaceNode.leftChild != null) {
                    replaceNode = replaceNode.leftChild;
                    ++s;
                }
                removeNode.data = replaceNode.data;
                if (s == 0) {
                    replaceNode.parent.rightChild = null;
                    this.size--;
                } else {
                    replaceNode.parent.leftChild = null;
                    this.size--;
                }
            }

            //this last case is where the node to be removed has 2 children. we can handle in the same way as either case1 scenario
        } else {
            replaceNode = removeNode.rightChild;
            int s=0;
            while (replaceNode.leftChild != null) {
                replaceNode = replaceNode.leftChild;
                ++s;
            }
            if (s == 0) {
                replaceNode.parent.rightChild = null;
                this.size--;
            } else {
                replaceNode.parent.leftChild = null;
                this.size--;
            }
            removeNode.data = replaceNode.data;
        }
    }

    /**
     * Searches a BST for a node with a certain id and returns it
     * @param id the id of the node we're looking for
     * @return the node we were looking for
     * @throws NoSuchElementException if desired node isn't in tree
     */
    public Node<Game> BSTSearch(int id) throws NoSuchElementException {
        //first check if the desired id is held at the root
        //this.root.data = (Game)this.root.data;
        if ( this.root.data.getID() == id) {
            return this.root;
        }
        //if not, set currNode to be the correct child of root
        Node<Game> currNode;
        if (id > this.root.data.getID()) {
            currNode = root.rightChild;
        } else {
            currNode = root.leftChild;
        }

        //compare ID to currentNode's id and decide where to go until you find it or currNode is null
        while (true) {
            if (currNode == null) {
                throw new NoSuchElementException();
            }
            if (currNode.data.getID() == id) {
                return currNode;
            } else if (id > currNode.data.getID()) {
                currNode = currNode.rightChild;
            } else {
                currNode = currNode.leftChild;
            }
        }
    }

    public static void main(String[] args) {

    }

}
