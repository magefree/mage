package mage.client.plugins;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.JComponent;

import mage.cards.Card;
import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.client.cards.BigCard;
import mage.view.CardView;
import mage.view.PermanentView;

public interface MagePlugins {
	void loadPlugins();
	void shutdown();
	void updateGamePanel(Map<String, JComponent> ui);
	JComponent updateTablePanel(Map<String, JComponent> ui);
	MagePermanent getMagePermanent(PermanentView card, BigCard bigCard, CardDimensions dimension, UUID gameId);
	MageCard getMageCard(CardView card, BigCard bigCard, CardDimensions dimension, UUID gameId);
	boolean isThemePluginLoaded();
	boolean isCardPluginLoaded();
	boolean isCounterPluginLoaded();
	void sortPermanents(Map<String, JComponent> ui, Collection<MagePermanent> permanents);
	void downloadImage(Set<Card> allCards);
	int getGamesPlayed();
	void addGamesPlayed();
}
