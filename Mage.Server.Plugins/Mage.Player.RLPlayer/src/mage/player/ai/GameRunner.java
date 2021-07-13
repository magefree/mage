package mage.player.ai;

import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.constants.ColoredManaSymbol;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.util.RandomUtil;
import org.junit.Ignore;
import org.junit.Test;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.player.ai.RLPlayer;
import mage.player.ai.RLAgent.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.io.*;
import mage.player.ai.RandomPlayer;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

/**
 * @author ayratn
 * Modified by Elchanan Haas
 */

public class  GameRunner{

    private static final List<String> colorChoices = new ArrayList<>(Arrays.asList("bu", "bg", "br", "bw", "ug", "ur", "uw", "gr", "gw", "rw", "bur", "buw", "bug", "brg", "brw", "bgw", "wur", "wug", "wrg", "rgu"));
    private static final int DECK_SIZE = 40;
    private static final Logger logger = Logger.getLogger(GameRunner.class);
    public DJLAgent agent;
    public GameRunner(){
        agent=new DJLAgent();
    }
    public int playOneGame(String deck1loc,String deck2loc,boolean player2random) throws GameException, FileNotFoundException, IllegalArgumentException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);

        //String deckLoc="/home/elchanan/java/mage/Mage.Tests/RBTestAggro.dck";
        
        Deck deck1=loadDeck(deck1loc);
        Deck deck2=loadDeck(deck2loc);
        
        Player player1 = createAgentPlayer("Player1",agent);
        if (deck1.getCards().size() < DECK_SIZE) {
            throw new IllegalArgumentException("Couldn't load deck, deck size = " + deck1.getCards().size() + ", but must be " + DECK_SIZE);
        }
        game.addPlayer(player1, deck1);
        game.loadCards(deck1.getCards(), player1.getId());
        Player player2;
        if(player2random){
            player2=new RandomNonTappingPlayer("Player2");
        }
        else{
            player2=createAgentPlayer("Player2",agent);
        }
        //Deck deck2 = generateRandomDeck();
        if (deck2.getCards().size() < DECK_SIZE) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck2.getCards().size() + ", but must be " + DECK_SIZE);
        }
        game.addPlayer(player2, deck2);
        game.loadCards(deck2.getCards(), player2.getId());

        long t1 = System.nanoTime();
        GameOptions options = new GameOptions();
        options.stopOnTurn=100;
        options.testMode = false;
        game.setGameOptions(options);
        logger.info("starting game");
        game.start(player1.getId());
        long t2 = System.nanoTime();
        //learner.endGame(computerA,game.getWinner());
        logger.info("Winner: " + game.getWinner());
        logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
        int reward=getReward(player1, game.getWinner());
        //return learner.getCurrentGame().getValue();
        return reward;
    }
    private Player createAgentPlayer(String name, DJLAgent agent){
        return new RLPlayer(name,agent);
    }
    private Deck generateRandomDeck() {
        String selectedColors = colorChoices.get(RandomUtil.nextInt(colorChoices.size())).toUpperCase(Locale.ENGLISH);
        List<ColoredManaSymbol> allowedColors = new ArrayList<>();
        logger.info("Building deck with colors: " + selectedColors);
        for (int i = 0; i < selectedColors.length(); i++) {
            char c = selectedColors.charAt(i);
            allowedColors.add(ColoredManaSymbol.lookup(c));
        }
        List<Card> cardPool = Sets.generateRandomCardPool(45, allowedColors);
        return ComputerPlayer.buildDeck(DECK_SIZE, cardPool, allowedColors);
    }
    private Deck loadDeck(String name){
        DeckCardLists list;
        StringBuilder errormsg= new StringBuilder(); 
        list=DeckImporter.importDeckFromFile(name, errormsg,true);
        if(errormsg.length()>0) logger.info(errormsg.toString());
        //for(int i=0;i<list.getCards().size();i++){
        //    logger.info(list.getCards().get(i).getCardName());
        //}
        Deck deck;
        try{
            deck = Deck.load(list, false, false);
        }
        catch(GameException e){
            logger.info("load failure");
            deck=null;
        }
        return deck;
    }
    private int getReward(Player player,String winner){ 
        String winLine="Player "+player.getName()+" is the winner";
        if(winner.equals(winLine)){
            return 1;
        }
        else if(winner.equals("Game is a draw")){
            return 0;
        }
        else{
            return -1;
        }
    }
}