package org.mage.plugins.rating;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JFrame;

import mage.Constants.CardType;
import mage.cards.Card;
import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.view.CardView;

import org.mage.plugins.card.CardPluginImpl;
import org.mage.plugins.rating.cards.CardsStorage;
import org.mage.plugins.rating.results.Rating;
import org.mage.plugins.rating.results.ResultHandler;
import org.mage.plugins.rating.ui.BigCard;

public class RateThread extends Thread {

	private static RateThread fInstance = new RateThread();
	private CardPluginImpl impl = new CardPluginImpl();
	public static CardDimensions dimensions = new CardDimensions(0.4);
	public static CardDimensions bigCardDimension = new CardDimensions(0.8);
	private JFrame frame;
	private MageCard mageCard1;
	private MageCard mageCard2;
	private BigCard bigCard;
	private boolean stop = false;
	
	private static List<Rating> results = new ArrayList<Rating>();
	
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
				
				mageCard1 = impl.getMageCard(new CardView(card1), dimensions, UUID.randomUUID(), new RateCallback(card1, card2, this, bigCard));
				mageCard1.setCardBounds(bigCardDimension.frameWidth + 80, 10, dimensions.frameWidth, dimensions.frameHeight);
				frame.add(mageCard1);
				
				mageCard2 = impl.getMageCard(new CardView(card2), dimensions, UUID.randomUUID(), new RateCallback(card2, card1, this, bigCard));
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
		int count = CardsStorage.getAllCards().size();
		Card card1 = CardsStorage.getAllCards().get((int)(Math.random()*count));
		while (card1.getCardType().contains(CardType.LAND) || card1.getName().equals(previousCard)) {
			card1 = CardsStorage.getAllCards().get((int)(Math.random()*count));
		}
		return card1;
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
