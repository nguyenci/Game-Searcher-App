import java.util.List;

/**
 * An instance of a class that implements the following interface can be used
 * to search and retrieve the database of shows within the ShowSearcher app.
 */
public interface IGameSearcherBackend {

    // also need a hashtable
    // private IRBListSearcher<Game> treeSearcher; main tree
    // private IRBListSearcher<Game> userWishList; wishlist
    // private List<Game> searchResults; store results of searchByTitle
    // private int filter; set 0 when calling constructor
    // instance variable a rb tree with all games
    // an arraylist of games currently searched

    /**
     * Adds game to backend database.
     *
     * @param game game to be added
     */
    void addGame(Game game);

    /**
     * Retrieve number of games in database.
     *
     * @return number of games in database
     */
    int getNumberOfGames();

    /**
     * Frontend can pass in the index of the game to be added to the wishlist from the returned list
     * the method will add the game title to an array
     * @param index
     */
    void addGameWishlist(int index);

    /**
     * Return list of games in wishlist.
     * @return
     */
    List<Game> getWishlist();

    /**
     * Remove a game from the wishlist.
     * @param index
     */
    void removeGameWishList(int index);

    /**
     * Search the main tree for games, applying rating filter afterwards. Also saves a separate list
     * of currentSearchedGames as an instance variable for the wishlist function.
     * @param word
     * @return
     */
    List<Game> searchByTitleWord(String word);

    int getYearFilter();

    void setYearFilter(int year);

}
