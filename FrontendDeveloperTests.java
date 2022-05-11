//--== CS400 Project Two File Header ==--
//Name: Cinthya Nguyen
//CSL Username: cinthya
//Email: cnguyen37@wisc.edu
//Lecture #: 002 @1:00pm
//Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FrontendDeveloperTests {

    /**
     * Tests the starting menu and [q]uit command.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test public void test1() throws FileNotFoundException {
        TextUITester tester = new TextUITester("q\n");
        GameSearcherBackend backend = new GameSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend);

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("Thanks for using our app!"));
        assertTrue(output.contains("1) [S]earch\n2) [A]dd Game to WishList\n"));
        assertEquals(
            "Welcome to the Game Searcher App!\n=================================\nCommand Menu:\n"
                + "1) [S]earch\n2) [A]dd Game to WishList\n3) [R]emove Game from Wishlist\n"
                + "4) [P]rint my Wishlist (level-order)\n5) [Q]uit\nThanks for using our app!\n",
            output);

    }

    /**
     * Tests the command 4, printing wishlist.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test public void test2() throws FileNotFoundException {
        TextUITester tester = new TextUITester("2\ndoom\n90\n1\nq\nq\n");
        GameSearcherBackend backend = new GameSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend);

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        for (Game game : gameList) {
            backend.addGame(game);
        }

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("Ultimate Doom"));

    }

    /**
     * Tests command 1, searching database.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test public void test3() throws FileNotFoundException {
        TextUITester tester = new TextUITester("1\ndoom\n2\nq\n");
        GameSearcherBackend backend = new GameSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend);

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        for (Game game : gameList) {
            backend.addGame(game);
        }

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("1. Ultimate Doom (2280)"));
        assertTrue(output.contains("2. DOOM II (2300)"));
        assertTrue(output.contains("3. DOOM 3 (9050)"));
        assertTrue(output.contains("User Reviews: 85% Positive "));
        assertTrue(output.contains("Found 7/899 matches containing 'doom' with a rating above 2."));

    }

    /**
     * Tests command 2, adding a game to the wishlist.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test public void test4() throws FileNotFoundException {
        TextUITester tester = new TextUITester("2\ndoom\n90\n1\n1\n1\nq\n");

        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend); //new ShowSearcherFrontend(backend);

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        for (Game game : gameList) {
            backend.addGame(game);
        }

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("Enter a [#] to add a game to your wishlist or [q]uit:"));
        assertTrue(output.contains("Found 3/899 matches containing 'doom' with a rating above 90."));
        assertTrue(output.contains("Ultimate Doom successfully added to game wishlist."));
        assertTrue(output.contains("DOOM II successfully added to game wishlist."));
        assertTrue(output.contains("All results added to wishlist."));

    }

    /**
     * Tests command 3, removing a game from the wishlist.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test public void test5() throws FileNotFoundException {
        TextUITester tester = new TextUITester("2\ndoom\n90\n1\n1\n1\nr\n1\nq\n4\nq\n");
        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend); //new ShowSearcherFrontend(backend);

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        for (Game game : gameList) {
            backend.addGame(game);
        }

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("Remove a game from your wishlist:"));
        assertTrue(output.contains("Ultimate Doom removed from your wishlist."));
        assertTrue(output.contains("DOOM II, DOOM"));

    }

    /**
     * Integration test: incorporates Backend, Data Wrangler, Algorithm Engineer and Frontend code.
     * Uses Frontend interface to create a List of search results in Backend/Algo Engineer obtained
     * from List of games from Data Wrangler code.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test
    public void testIntegration1() throws FileNotFoundException {

        // Searches for a game and adds it to a wishlist.
        TextUITester tester = new TextUITester("2\nbioshock\n80\n1\nq\n");
        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend); //new ShowSearcherFrontend(backend);

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        for (Game game : gameList) {
            backend.addGame(game);
        }

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("Enter a [#] to add a game to your wishlist or [q]uit:"));
        assertTrue(output.contains("Found 1/899 matches containing 'bioshock' with a rating above 80."));
        assertTrue(output.contains("All results added to wishlist."));

    }

    /**
     * Integration test: incorporates Backend, Data Wrangler, Algorithm Engineer and Frontend code.
     * Uses Frontend interface to create a List of search results in Backend/Algo Engineer obtained
     * from List of games from Data Wrangler code, adds that to a wishlist in Backend, then removes it from backend.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test
    public void testIntegration2() throws FileNotFoundException {
        TextUITester tester = new TextUITester("2\nbioshock\n80\n1\n3\n1\nq\n");
        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend); //new ShowSearcherFrontend(backend);

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        for (Game game : gameList) {
            backend.addGame(game);
        }

        frontend.runCommandLoop();
        String output = tester.checkOutput();

        assertTrue(output.startsWith(
            "Welcome to the Game Searcher App!\n================================="));
        assertTrue(output.contains("Choose a [#] to remove a game from your wishlist or [q]uit:"));
        assertTrue(output.contains("BioShock Infinite removed from your wishlist."));
        assertTrue(output.contains("Wishlist empty; no more games left to remove."));

    }

    /**
     * Tests searchByTitleWord() method in Backend.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test
    public void testBackend1() throws FileNotFoundException {

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        for(Game game : gameList) {
            backend.addGame(game);
        }

        List<Game> searchResults = backend.searchByTitleWord("doom");
        List<String> expected = new ArrayList<>();
        expected.add("Ultimate Doom");
        expected.add("DOOM II");
        expected.add("DOOM 3");
        expected.add("Doom 3: BFG Edition");
        expected.add("DOOM");
        expected.add("DOOM VFR");
        expected.add("DOOM Eternal");

        assertEquals(7, searchResults.size());
        for (int i = 0; i < searchResults.size(); i++) {
            assertEquals(expected.get(i), searchResults.get(i).getTitle());
        }

    }

    /**
     * Tests Backend code loads all games from XML file into the Red-Black-Tree database.
     *
     * @throws FileNotFoundException throws exception if xml file is not found
     */
    @Test
    public void testBackend2() throws FileNotFoundException {

        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        for(Game game : gameList) {
            backend.addGame(game);
        }

        assertEquals(899,backend.getNumberOfGames());


    }





}
