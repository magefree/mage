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
import org.mage.test.player.RLPlayer;
import org.mage.test.player.RLagent.RLLearner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
        for (int i = 1; i < 10; i++) {
            logger.info("Playing game: " + i);
            
            playOneGame(learner);
        }
    }

    private void playOneGame(RLLearner learner) throws GameException, FileNotFoundException, IllegalArgumentException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        learner.newGame();
        String deckLoc="RB Aggro.dck";
        Player computerA = createRLPlayer("RLPlayer",learner);
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
        game.start(computerA.getId());
        long t2 = System.nanoTime();
        learner.endGame(game.getWinner());
        logger.info("Winner: " + game.getWinner());
        logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
        logger.info(learner.getCurrent().getValue(computerA));
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
        //logger.info(list.getCards());
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
