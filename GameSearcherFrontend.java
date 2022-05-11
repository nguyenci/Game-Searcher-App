//--== CS400 Project Two File Header ==--
//Name: Cinthya Nguyen
//CSL Username: cinthya
//Email: cnguyen37@wisc.edu
//Lecture #: 002 @1:00pm
//Notes to Grader: N/A

import java.util.*;

public class GameSearcherFrontend implements IGameSearcherFrontend {

    private final GameSearcherBackend backend;
    private String input;
    private int num;
    private final Scanner scnr;
    private List<Game> searchResults;
    private int rating;

    public GameSearcherFrontend(GameSearcherBackend backend) {
        this.backend = backend;
        scnr = new Scanner(System.in);
        searchResults = new ArrayList<>();
    }

    /**
     * This method drives the entire read, eval, print loop for the app. This loop will continue to run until the user
     * explicitly enters the quit command.
     */
    @Override public void runCommandLoop() {
        System.out.println("Welcome to the Game Searcher App!\n=================================");
        boolean running = true;
        do {
            displayCommandMenu();
            switch (scnr.next().toLowerCase()) {
                case "s":
                case "1":
                    try {
                        search();
                    } catch (Exception e) {
                        System.out.println("Search failed. Try again.");
                        search();
                    }
                    break;
                case "a":
                case "2":
                    try {
                        add();
                    } catch (Exception e) {
                        System.out.println("Could not add game to your wishlist.");
                    }
                    break;
                case "r":
                case "3":
                    try {
                        remove();
                    } catch (Exception e) {
                        System.out.println("Wishlist empty.");
                    }
                    break;
                case "p":
                case "4":
                    try {
                        printWishlist();
                    } catch (Exception e) {
                        System.out.println("Error printing wishlist.");
                    }
                    break;
                case "q":
                case "5":
                    System.out.println("Thanks for using our app!");
                    running = false;
                    break;
                default:
                    displayCommandMenu();
                    break;
            }
        } while (running);

    }

    /**
     * Prints command options.
     */
    @Override public void displayCommandMenu() {
        System.out.println("Command Menu:\n1) [S]earch\n2) [A]dd Game to WishList\n"
                + "3) [R]emove Game from Wishlist\n4) [P]rint my Wishlist (level-order)\n5) [Q]uit");
    }

    /**
     * Finds and prints all video games that has a title containing user-inputted substring and a rating above the specified threshold.
     */
    @Override public void search() {
        System.out.println("Choose a game that you would like to search for:");
        searchHelper();
        System.out.println("Found " + searchResults.size() + "/" + backend.getNumberOfGames() + " matches containing '" + input
            + "' with a rating above " + rating + ".");

        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i + 1) + ". " + searchResults.get(i));
        }

        searchResults = new ArrayList<>();

    }

    private void searchHelper() {

        input = scnr.next().toLowerCase();

        System.out.println("Choose a rating threshold:");

        do { // make sure user enters a number for rating
            if (!scnr.hasNextInt()) {
                System.out.println("Not a number. Try again.");
                scnr.next();
            }

            if (scnr.hasNextInt()) {
                rating = scnr.nextInt();
                backend.setRatingFilter(rating);
                break;
            }
        } while (true);

        searchResults = backend.searchByTitleWord(input);
    }

    /**
     * Adds video game to user's wishlist.
     */
    @Override public void add() {
        System.out.println("Search for a game that you would like to add to your wishlist:");
        searchHelper();
        System.out.println("Found " + searchResults.size() + "/" + backend.getNumberOfGames() + " matches containing '" + input
                + "' with a rating above " + rating + ".");
        addHelper(searchResults);
    }

    private void addHelper(List<Game> gameList) {

        do {

            System.out.println("Enter a [#] to add a game to your wishlist or [q]uit:");

            for (int i = 0; i < gameList.size(); i++) {
                System.out.println((i + 1) + ". " + gameList.get(i));
            }

            if (scnr.hasNext("q")) {
                scnr.next();
                return;
            }

            do { // makes sure user enters a number
                if (!scnr.hasNextInt()) {
                    System.out.println("Not a number. Try again.");
                    scnr.next();
                }

                if (scnr.hasNextInt()) {
                    num = scnr.nextInt();
                    backend.setRatingFilter(rating);
                    break;
                }
            } while (true);

            if (num > 0 && num <= gameList.size()) {
                backend.addGameWishlist(num - 1);
                System.out.println(searchResults.get(num - 1).getTitle() + " successfully added to game wishlist.");
                searchResults.remove(num - 1);

                if (searchResults.size() == 0) {
                    System.out.println("All results added to wishlist.");
                    return;
                }
            } else {
                System.out.println("Choose a number between (inclusive) 1" + " and " + gameList.size() + ".");
            }

        } while (true);

    }

    /**
     * Removes video game from user's wishlist.
     */
    @Override public void remove() {
        System.out.println("Remove a game from your wishlist:");

        do {
            List<Game> wishList = backend.getWishlist();

            if (wishList.size() == 0) {
                System.out.println("Wishlist empty; no more games left to remove.");
                return;
            }

            System.out.println("Choose a [#] to remove a game from your wishlist or [q]uit:");

            for (int i = 0; i < wishList.size(); i++) { // prints out games currently in wishlist
                System.out.println((i + 1) + ". " + wishList.get(i));
            }

            if (scnr.hasNext("q")) { // quit
                scnr.next();
                return;
            }

            do { // make sure user enters a number for the game to be removed
                if (!scnr.hasNextInt()) {
                    System.out.println("Not a number. Try again.");
                    scnr.next();
                }

                if (scnr.hasNextInt()) {
                    num = scnr.nextInt();
                    break;
                }
            } while (true);

            if (num > 0 && num <= wishList.size()) { // remove game from wishlist
                System.out.println(wishList.get(num - 1).getTitle() + " removed from your wishlist.");
                backend.removeGameWishList(num - 1);
            } else {
                System.out.println("Choose a number between (inclusive) 1" + " and " + wishList.size() + ".");
            }

        } while (true);

    }

    /**
     * Prints out level-order string of user's current wishlist.
     */
    @Override public void printWishlist(){
        List<Game> wishList = backend.getWishlist();

        if (wishList.size() == 0) {
            System.out.println("Wishlist is empty. Add games.");
            return;
        }

        for (int i = 0; i < wishList.size(); i++) {
            if (i < wishList.size() - 1) {
                System.out.print(wishList.get(i).getTitle() + ", ");
            } else {
                System.out.print(wishList.get(i).getTitle() + "\n");
            }
        }

    }
}
