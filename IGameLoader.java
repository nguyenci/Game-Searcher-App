import java.io.FileNotFoundException;
import java.util.List;

public interface IGameLoader {

    List<Game> loadGames(String filepath) throws FileNotFoundException;

}
