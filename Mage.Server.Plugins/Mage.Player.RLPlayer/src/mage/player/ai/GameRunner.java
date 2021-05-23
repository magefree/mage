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

/**
 * @author ayratn
 * Modified by Elchanan Haas
 */

public class  GameRunner{

    private static final List<String> colorChoices = new ArrayList<>(Arrays.asList("bu", "bg", "br", "bw", "ug", "ur", "uw", "gr", "gw", "rw", "bur", "buw", "bug", "brg", "brw", "bgw", "wur", "wug", "wrg", "rgu"));
    private static final int DECK_SIZE = 40;
    private static final Logger logger = Logger.getLogger(GameRunner.class);
    PyConnection conn;
    public RLPyAgent agent;
    public GameRunner(){
        int port=5002;
        conn=new PyConnection(port);
        agent=new RLPyAgent(conn);
    }
    
    public int playOneGame() throws GameException, FileNotFoundException, IllegalArgumentException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        String deckLoc="/home/elchanan/java/mage/Mage.Tests/RBTestAggro.dck";
        Player computerA = createAgentPlayer("RLPlayer",agent);
        String mode="load";
        Deck decka;
        Deck deckb;
        
        if(mode=="random"){
            decka= generateRandomDeck();
            deckb= generateRandomDeck();
        }else{
            decka=loadDeck(deckLoc);
            deckb=loadDeck(deckLoc);
        }
        
        if (decka.getCards().size() < DECK_SIZE) {
            throw new IllegalArgumentException("Couldn't load deck, deck size = " + decka.getCards().size() + ", but must be " + DECK_SIZE);
        }
        game.addPlayer(computerA, decka);
        game.loadCards(decka.getCards(), computerA.getId());

        Player computerB = new RandomNonTappingPlayer("RandomPlayer");
        //Deck deck2 = generateRandomDeck();
        if (deckb.getCards().size() < DECK_SIZE) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deckb.getCards().size() + ", but must be " + DECK_SIZE);
        }
        game.addPlayer(computerB, deckb);
        game.loadCards(deckb.getCards(), computerB.getId());

        long t1 = System.nanoTime();
        GameOptions options = new GameOptions();
        options.testMode = false;
        game.setGameOptions(options);
        logger.info("starting game");
        game.start(computerA.getId());
        long t2 = System.nanoTime();
        //learner.endGame(computerA,game.getWinner());
        logger.info("Winner: " + game.getWinner());
        logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
        int reward=getReward(computerA, game.getWinner());
        agent.sendGame(game,computerA,new ArrayList<RLAction>());
        //return learner.getCurrentGame().getValue();
        return reward;
    }
    private Player createAgentPlayer(String name, RLAgent agent){
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