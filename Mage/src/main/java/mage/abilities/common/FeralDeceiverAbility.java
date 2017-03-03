package mage.abilities.common;

import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * Created by Eric on 9/24/2016.
 */
public class FeralDeceiverAbility extends LimitedTimesPerTurnActivatedAbility {

    public FeralDeceiverAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
    }

    public FeralDeceiverAbility(FeralDeceiverAbility ability) {
        super(ability);
    }

    @Override
    public FeralDeceiverAbility copy() {
        return new FeralDeceiverAbility(this);
    }

    @Override
    public boolean checkIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl();
            Card card = player.getLibrary().getFromTop(game);
            cards.add(card);
            player.revealCards("Feral Deceiver", cards, game);
            if (card != null && card.isLand()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "{2}: Reveal the top card of your library. If it's a land card, {this} gets +2/+2 and gains trample until end of turn. Activate this ability only once each turn.";
    }
}
