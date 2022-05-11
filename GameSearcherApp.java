import java.util.List;

public class GameSearcherApp {

    public static void main(String[] args) throws Exception {
        IGameLoader loader = new GameLoader(); //new ShowLoader();
        List<Game> gameList = loader.loadGames("steam_games_final.xml");
        GameSearcherBackend backend = new GameSearcherBackend(); //new ShowSearcherBackend();
        for(Game game : gameList) {
            backend.addGame(game);
        }
        IGameSearcherFrontend frontend = new GameSearcherFrontend(backend); //new ShowSearcherFrontend(backend);
        frontend.runCommandLoop();
    }
}
