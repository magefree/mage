import mage.player.ai.GameRunner;
import mage.player.ai.RLPlayer;
import mage.game.GameException;
import java.io.*;
import java.util.*;
import mage.cards.repository.CardScanner;
import mage.constants.RangeOfInfluence;


public class Main {
    public static void main(String[] args) throws GameException, FileNotFoundException, IllegalArgumentException {
        //RLPlayer player= new RLPlayer("bob", RangeOfInfluence.ALL, 0); //Test loading
        ArrayList<String> errorsList = new ArrayList<>();
        CardScanner.scan(errorsList);
        GameRunner runner=new GameRunner();
        int netWins=0;
        int count=0;
        while(!runner.agent.done){
            netWins+=runner.playOneGame();
            count+=1;
            System.out.println("netWins is "+netWins+" at timestep "+count);
        }
        System.out.println("Shutting down");
    }
}
