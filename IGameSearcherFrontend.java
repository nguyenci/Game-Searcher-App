//--== CS400 Project Two File Header ==--
//Name: Cinthya Nguyen
//CSL Username: cinthya
//Email: 37cnguyen@wisc.edu
//Lecture #: 002 @1:00pm
//Notes to Grader: N/A

public interface IGameSearcherFrontend {

    /**
     * This method drives the entire read, eval, print loop for the app. This loop will continue to run until the user
     * explicitly enters the quit command.
     */
    void runCommandLoop();

    /**
     * Prints command options.
     */
    void displayCommandMenu();

    /**
     * Finds and prints all video games that has a title containing user-inputted substring and a rating above the specified threshold.
     */
    void search();

    /**
     * Adds video game to user's wishlist.
     */
    void add();

    /**
     * Removes video game from user's wishlist.
     */
    void remove();

    /**
     * Prints out level-order string of user's current wishlist.
     */
    void printWishlist();

}
