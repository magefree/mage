package mage.abilities.effects;

import mage.cards.Card;
import mage.game.Game;

import java.util.UUID;

public interface PerpetuallyEffect extends ContinuousEffect {
    boolean affectsCard(Card card, Game game);

    void removeTarget(Card card, Game game);

    void addTarget(Card card, Game game);
}
