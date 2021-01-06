import mage.player.ai.GameRunner;

import mage.game.GameException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws GameException, FileNotFoundException, IllegalArgumentException {
        GameRunner runner=new GameRunner();
        for(int i=0;i<100;i++){
            runner.playOneGame();
        }
    }
}
