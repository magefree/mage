package org.mage.test.serverside;

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
import org.mage.test.serverside.base.MageTestBase;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.player.ai.RLPlayer;
import mage.player.ai.RLAgent.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.deeplearning4j.optimize.listeners.CollectScoresIterationListener;
import java.io.*;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.optimize.listeners.CollectScoresIterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
/**
 * @author ayratn
 * Modified by Elchanan Haas
 */

public class TrainRLPlayer extends MageTestBase {

    private static final List<String> colorChoices = new ArrayList<>(Arrays.asList("bu", "bg", "br", "bw", "ug", "ur", "uw", "gr", "gw", "rw", "bur", "buw", "bug", "brg", "brw", "bgw", "wur", "wug", "wrg", "rgu"));
    private static final int DECK_SIZE = 40;

    @Test
    //@Ignore
    public void playGames() throws GameException, FileNotFoundException {
        RLLearner learner=new RLLearner();
        int netwins=0;
        String loc="learnerModel";
        
        //learner=loadLearner(loc);
        for (int i = 0; i < 3000; i++) {
            logger.info("Playing game: " + i);
            netwins+=playOneGame(learner);
            for(int j=0;j<5;j++){
                learner.trainBatch(64);
                double[] scores= learner.losses.getScoreVsIter().getEffectiveScores();
                logger.info(scores[scores.length-1]); 
            }
            logger.info(netwins);
            if(i==1000){
                learner.setEpsilon(.3f);
            }
            if(i==2000){
                learner.setEpsilon(.1f);
            }
        }
        saveLearner(learner, loc);
        //logger.info(learner.actionToIndex);
        
    }
    String getDataLoc(String fileName){
        return "models/"+fileName+"-model.ser";
    }
    String getModelLoc(String fileName){
        return "models/"+fileName+"-model.zip";
    }
    void saveLearner(RLLearner learner,String loc){
        try {
            FileOutputStream fileOut =
            new FileOutputStream(getDataLoc(loc));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(learner);
            out.close();
            fileOut.close();
            File locationToSaveGraph = new File(getModelLoc(loc));
            learner.model.save(locationToSaveGraph, true);
            System.out.printf("Serialized data is saved in "+loc+"\n");
         } catch (IOException i) {
            i.printStackTrace();
         }
    }
    RLLearner loadLearner(String loc){
        try {
            FileInputStream fileIn = new FileInputStream(getDataLoc(loc));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object read=in.readObject();
            RLLearner learner = (RLLearner) read;
            in.close();
            fileIn.close();
            learner.model=ComputationGraph.load(new File(getModelLoc(loc)),true);
            CollectScoresIterationListener listener=new CollectScoresIterationListener(1);
            learner.model.setListeners(listener);
            learner.losses=listener;
            return learner;
         } catch (IOException i) {
            i.printStackTrace();
            return null;
         } catch (ClassNotFoundException c) {
            System.out.println("RLLearner class not found");
            c.printStackTrace();
            return null;
         }
    }
    private int playOneGame(RLLearner learner) throws GameException, FileNotFoundException, IllegalArgumentException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        String deckLoc="RBTestAggro.dck";
        Player computerA = createRLPlayer("RLPlayer",learner);
        learner.newGame(computerA);
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

        Player computerB = createRandomPlayer("RandomPlayer");
        //Deck deck2 = generateRandomDeck();
        if (deckb.getCards().size() < DECK_SIZE) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deckb.getCards().size() + ", but must be " + DECK_SIZE);
        }
        game.addPlayer(computerB, deckb);
        game.loadCards(deckb.getCards(), computerB.getId());

        long t1 = System.nanoTime();
        GameOptions options = new GameOptions();
        options.testMode = true;
        game.setGameOptions(options);
        logger.info("starting game");
        game.start(computerA.getId());
        long t2 = System.nanoTime();
        learner.endGame(computerA,game.getWinner());
        logger.info("Winner: " + game.getWinner());
        logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
        return learner.getCurrentGame().getValue();
    }
    private Player createRLPlayer(String name,RLLearner learner){
        return new RLPlayer(name,learner);
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
}
