// --== CS400 File Header Information ==--
// Name: Dennis Leung
// Email: dhleung@wisc.edu
// Team: BO
// TA: Sujitha
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.*;

/**
 * the backend obj
 */
public class GameSearcherBackend implements IGameSearcherBackend{

    /**
     * the year filter
     */
    private Integer yearFilter;
    /**
     * the rating filter
     */
    private Integer ratingFilter;

    /**
     * keeping track of the ids in the wishlist
     */
    private List<Integer> wishlistID;


    /**
     * the hashmap that assigns a title to a list of ids, hardcoded, using java inbuilt for testing purposes
     */
    private HashTableSortedSets<String, Integer> gameTable;

    /**
     * main tree
     */
    private RBListSearcher treeSearcher;

    /**
     * wishlist
     */
    private RBListSearcher userWishList;

    /**
     * store results of searchByTitle
     */
    private ArrayList<Game> searchResults;
    /**
     * set 0 when calling constructor
     */
    private int filter;

    /**
     * constructor
     */
    public GameSearcherBackend(){
        this.gameTable = new HashTableSortedSets<>();
        this.treeSearcher = new RBListSearcher();
        this.userWishList = new RBListSearcher();
        this.wishlistID = new ArrayList<>();
    }

    /**
     * add game
     * @param game game to be added
     */
    @Override
    public void addGame(Game game) {

        String title = game.getTitle().toLowerCase();
        Scanner scnr = new Scanner(title);
        while (scnr.hasNext()) {
            String titleWord = scnr.next().trim();
            this.gameTable.add(titleWord, game.getID());
        }
        scnr.close();
        this.treeSearcher.insert(game);
    }

    /**
     * @return number of games in the main tree of games
     */
    @Override
    public int getNumberOfGames() {
        return this.treeSearcher.size;
    }

    /**
     * adds game to wishlist
     * @param index the index of the wishlist
     */
    @Override
    public void addGameWishlist(int index) {
        Game tobeAdded = this.searchResults.get(index);
        this.userWishList.insert(tobeAdded);
        this.wishlistID.add(tobeAdded.getID());
    }

    /**
     * get wishlist in a list of strings
     * @return a list of strings representing the games in the wishlist
     */
    @Override
    public List<Game> getWishlist() {
        List<Game> retVal = new ArrayList<>();
        Iterator<Game> iterator = this.userWishList.iterator();
        while(iterator.hasNext()){
            retVal.add(iterator.next());
        }
        return retVal;
    }

    /**
     * removes game to wishlist
     * @param index the index of the wishlist
     */
    @Override
    public void removeGameWishList(int index) {
        this.userWishList.remove(this.wishlistID.get(index));
        this.wishlistID.remove(index);
    }

    /**
     * search the main tree for games, applying filters afterwards
     * @param word the title
     * @return a list of games
     */
    @Override
    public List<Game> searchByTitleWord(String word) {
        List<Integer> idList = this.gameTable.get(word);
        // getting the games as arraylist to use the removeIf method for filters
        ArrayList<Game> retList = new ArrayList<>();
        retList.addAll(this.treeSearcher.search(idList));
        // sorting by ID with a lambda expression returning a comparator
        Collections.sort(retList, Comparator.comparing(o -> (o.getID())));

        // apply the filters, if any

        if (this.ratingFilter != null){
            retList.removeIf(n -> n.getRating() < this.ratingFilter);
        }

        this.searchResults = retList;
        return retList;
    }


    /**
     * getter
     * @return the yearFilter
     */
    @Override
    public int getYearFilter() {
        return this.yearFilter;
    }

    /**
     * setter
     * @param year the yearFilter
     */
    @Override
    public void setYearFilter(int year) {
        this.yearFilter = year;
    }

    /**
     * getter
     * @return the ratingFilter
     */
    public int getRatingFilter(){
        return this.ratingFilter;
    }

    /**
     * setter
     * @return the ratingFilter
     */
    public void setRatingFilter(int rating){
        this.ratingFilter = rating;
    }
}
