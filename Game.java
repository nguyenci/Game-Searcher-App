// --== CS400 Project Two File Header ==--
// Name: Trevor Johnson
// CSL Username: trevorj
// Email: tmjohnson32@wisc.edu
// Lecture #: 002
// Notes to Grader: <N/A>

/**
 * Represents a single game object that can be stored, sorted, and searched for
 * based on the accessors below
 */
public class Game implements IGame, Comparable<Game>{

    private String title;
    private int rating;
    private int ID;
    private double price;
    private String genre;

    /**
     * Initializes all the getter methods and the compareTo method.
     * @param title
     * @param rating
     * @param ID
     * @param genre
     */
    public Game(String title, int ID, double price, int rating, String genre){
        this.title = title;
        this.rating = rating;
        this.ID = ID;
        this.price = price;
        this.genre = genre;
    }

    /**
     * Returns title of game.
     *
     * @return game's title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Returns rating of game.
     *
     * @return game's rating
     */
    @Override
    public int getRating() {
        return rating;
    }

    /**
     * Returns ID of game.
     *
     * @return game's ID
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Returns price of game.
     *
     * @return game's price
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Returns genre of game.
     *
     * @return game's genre
     */
    @Override
    public String getGenre() {
        return genre;
    }

    /**
     * Compares the rating of two game objects.
     *
     * @param otherGame game to compare
     * @return game's rating subtracting the other game's rating
     */
    @Override
    public int compareTo(Game otherGame) {
        return this.ID - otherGame.ID;
    }

    /**
     * Returns a string of each game attribute to be used in gameSearcherFrontend
     *
     * @return toString of a game
     */
    public String toString() {
        return title + " (" + ID + ")\n" + "Genre: " + genre + "\n" + "User Reviews: " + rating +
            "% Positive \n" + "Price: $" + price;
    }

}
