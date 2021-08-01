import mage.player.ai.GameRunner;
import mage.game.GameException;
import java.io.*;


public class Main {
    public static void main(String[] args) throws GameException, FileNotFoundException, IllegalArgumentException {
        GameRunner runner=new GameRunner();
        runner.playGames(3000);
        System.out.println("Exiting");
    }
}
