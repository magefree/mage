package org.mage.plugins.rating;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.swing.JFrame;

import mage.Constants.CardType;
import mage.cards.Card;
import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.view.CardView;

import org.mage.plugins.card.CardPluginImpl;
import org.mage.plugins.rating.results.Rating;
import org.mage.plugins.rating.results.ResultHandler;
import org.mage.plugins.rating.ui.BigCard;

public class RateThread extends Thread {

    private static RateThread fInstance = new RateThread();
    private CardPluginImpl impl = new CardPluginImpl();
    public static CardDimensions dimensions = new CardDimensions(0.4);
    public static Dimension cardDimension = new Dimension(dimensions.frameWidth, dimensions.frameHeight);
    public static CardDimensions bigCardDimension = new CardDimensions(0.8);
    private JFrame frame;
    private MageCard mageCard1;
    private MageCard mageCard2;
    private BigCard bigCard;
    private boolean stop = false;
    private Random random = new Random();

    private static List<Rating> results = new ArrayList<Rating>();
    private static final List<CardInfo> cards = new ArrayList<CardInfo>();

    static {
        CardScanner.scan();
        CardCriteria criteria = new CardCriteria();
        criteria.notTypes(CardType.LAND);
        List<CardInfo> allCards = CardRepository.instance.findCards(criteria);
        List<String> names = new ArrayList<String>();
        for (CardInfo card : allCards) {
            if (!names.contains(card.getName())) {
                names.add(card.getName());
                cards.add(card);
            }
        }
    }

    public RateThread() {
        setDaemon(true);
        start();
    }

    public static RateThread getInstance() {
        return fInstance;
    }

    @Override
    public synchronized void run() {
        while (!stop) {
            try {    
                Card card1 = getRandomUniqueNonLandCard(null);
                Card card2 = getRandomUniqueNonLandCard(card1);

                mageCard1 = impl.getMageCard(new CardView(card1), cardDimension, UUID.randomUUID(), new RateCallback(card1, card2, this, bigCard), false, true);
                mageCard1.setCardBounds(bigCardDimension.frameWidth + 80, 10, dimensions.frameWidth, dimensions.frameHeight);
                frame.add(mageCard1);

                mageCard2 = impl.getMageCard(new CardView(card2), cardDimension, UUID.randomUUID(), new RateCallback(card2, card1, this, bigCard), false, true);
                mageCard2.setCardBounds(bigCardDimension.frameWidth + 80 + dimensions.frameWidth + 30, 10, dimensions.frameWidth, dimensions.frameHeight);
                frame.add(mageCard2);

                frame.validate();

                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected Card getRandomUniqueNonLandCard(Card previousCard) {
        int count = cards.size();
        Card card = cards.get(random.nextInt(count)).getCard();
        while (previousCard != null && card.getName().equals(previousCard.getName())) {
            card = cards.get(random.nextInt(count)).getCard();
        }
        return card;
    }

    public void start(JFrame frame, BigCard bigCard) {
        this.frame = frame;
        this.bigCard = bigCard;
    }

    protected synchronized void generateNext() {
        notify();
    }

    public void reportResult(Card card1, Card card2) {
        results.add(new Rating(card1.getName(), card2.getName()));
        removeCard(mageCard1);
        removeCard(mageCard2);
        frame.validate();
        if (results.size() == 10) {
            ResultHandler.getInstance().save(results);
            results.clear();
        }
        generateNext();
    }

    public void forceSave() {
        if (results.size() > 0) {
            ResultHandler.getInstance().save(results);
            results.clear();
        }
    }

    private void removeCard(Component component) {
        if (component != null) {
            frame.remove(component);
        }
    }

    public synchronized void stopRating() {
        this.stop = true;
        notify();
    }
}
