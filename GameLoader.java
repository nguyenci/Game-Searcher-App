// --== CS400 Project Two File Header ==--
// Name: Trevor Johnson
// CSL Username: trevorj
// Email: tmjohnson32@wisc.edu
// Lecture #: 002
// Notes to Grader: <N/A>

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLoader<list> implements IGameLoader {

    /**
     * Loads a lists of games from an XML source file.
     * The following xml contents are used to load the game attributes:
     * Title: the complete title for this game
     * Rating: The steam rating for this game
     * ID: the steam ID for this game
     * Price: the price of this game on steam
     *
     * @param filepath is the path to the file relative to the user's system directory
     * @return the game(s) requested by the user
     * @author Trevor Johnson
     */
    @Override
    public ArrayList<Game> loadGames(String filepath) throws FileNotFoundException {

        ArrayList<Game> list = new ArrayList<>(); // create game arrayList to add all games to use in search UI
        Scanner scnr = new Scanner(new File(filepath)); // initialize scanner and line string
        String line = scnr.nextLine();  // enter XML file
        int gameID;
        String url; // url of the game that contains the ID
        String title; // title of the game
        String ratingString; // line that contains the rating in a percentage
        String genre = ""; // line that contains the game's genre(s)
        String priceString = ""; // line that contains the games price with a "$"
        int rating = 0; // rating in int form
        double price = 0.0; // price in double form
        IGame game = null; // initialize game object

        while (scnr.hasNextLine()) {

            ArrayList<String> gameList = new ArrayList<>();
            String[] gameArr = line.split(",");

            for (int i = 0; i < gameArr.length; i++) {
                gameList.add(gameArr[i]);

                scnr.nextLine();
                scnr.nextLine();
                // reach line with url that contains gameID
                url = scnr.nextLine().trim();
                // use IDhelper to extract substring that contains gameID
                gameID = IDhelper(url);
                scnr.nextLine();
                scnr.nextLine();
                // reach line with title on it
                title = scnr.nextLine().trim();
                // System.out.println(title);
                scnr.nextLine();
                scnr.nextLine();
                // reach line with string that contains rating percentage
                ratingString = scnr.nextLine();
                // use ratingHelper to get an int from the string
                rating = ratingHelper(ratingString);
                scnr.nextLine();
                scnr.nextLine();
                scnr.nextLine();
                scnr.nextLine();
                scnr.nextLine();
                // reach line with string that contains all genres
                genre = scnr.nextLine().trim();
                // System.out.println(genre);
                // use genreHelper to retrieve only the first genre
                scnr.nextLine();
                scnr.nextLine();
                // reach line with string that contains price
                priceString = scnr.nextLine().trim();
                // use priceHelper to get a double from the string
                price = priceHelper(priceString);
                scnr.nextLine();


                //String title, int ID, int price, int rating, String genre, int year

                // add a single game's attributes to list
                list.add(new Game(title, gameID, price, rating, genre));

                // check if scnr has reached the end of the xml file
                if (scnr.hasNextLine()) {
                    // end of one iteration of the loop
                    scnr.nextLine();
                } else {
                    break;
                }
            }
        }
        // System.out.println(list);
        return list;
    }

    /**
     * Helper method to extract gameID
     *
     * @param gameID string representation of the line that contains gameID
     * @return int to be used in gameLoader
     */
    private int IDhelper(String gameID) {
        int intID = 0;

        gameID = gameID.trim();
        // reduce string to ID and name
        gameID = gameID.substring(35, gameID.lastIndexOf("/"));
        // extract the ID from the previous string
        gameID = gameID.substring(0, gameID.indexOf("/"));
        // convert the string to an integer
        intID = Integer.parseInt(gameID);
        // System.out.println(intID);

        return intID;
    }

    /**
     * Helper method to extract rating
     *
     * @param ratingString representation of the line that contains gameID
     * @return int to be used in gameLoader
     */
    private int ratingHelper(String ratingString) {
        int rating = 0;

        ratingString = ratingString.trim();
        // extract the rating the entire line
        ratingString = ratingString.substring(ratingString.lastIndexOf(")") + 4,
                ratingString.indexOf("%"));
        // convert the string to an integer
        rating = Integer.parseInt(ratingString);
        // System.out.println(rating);

        return rating;
    }

    /**
     * Helper method to extract price
     *
     * @param priceString string representation of the line that contains price
     * @return double to be used in gameLoader
     */
    private Double priceHelper(String priceString) {
        double price = 0;

        // remove "$" from priceString
        priceString = priceString.replace("$", "");
        // convert the string to a double
        price = Double.parseDouble(priceString);
        // System.out.println(price);

        return price;
    }
}
