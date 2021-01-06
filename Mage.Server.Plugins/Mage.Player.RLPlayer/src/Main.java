import mage.player.ai.GameRunner;

import mage.game.GameException;
import java.io.*;
import java.util.*;
import mage.cards.repository.CardScanner;

public class Main {
    public static void main(String[] args) throws GameException, FileNotFoundException, IllegalArgumentException {
        ArrayList<String> errorsList = new ArrayList<>();
        CardScanner.scan(errorsList);
        GameRunner runner=new GameRunner();
        for(int i=0;i<100;i++){
            runner.playOneGame();
        }
    }
}
