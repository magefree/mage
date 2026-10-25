package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author weirddan455
 */
public class ReturnSourceFromGraveyardToBattlefieldWithCounterEffect extends ReturnSourceFromGraveyardToBattlefieldEffect {

    private final Counter counter;

    public ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(Counter counter, boolean tapped) {
        this(counter, tapped, false, false, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(Counter counter, boolean tapped, boolean ownerControl, boolean haste, boolean attacking) {
        super(tapped, ownerControl, haste, attacking);
        this.counter = counter;
        this.staticText += " with " + counter.getDescription() + " on it";
    }

    protected ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(final ReturnSourceFromGraveyardToBattlefieldWithCounterEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public ReturnSourceFromGraveyardToBattlefieldWithCounterEffect copy() {
        return new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(this);
    }

    @Override
    protected void putOntoBattlefield(Player player, Card card, Ability source, Game game) {
        Counters counters = new Counters();
        counters.addCounter(counter.copy());
        game.setEnterWithCounters(card.getId(), counters);
        super.putOntoBattlefield(player, card, source, game);
    }
}
