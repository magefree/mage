package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public final class BreathstealersCrypt extends CardImpl {

    public BreathstealersCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");

        // If a player would draw a card, instead they draw a card and reveals it. If it's a creature card, that player discards it unless they pay 3 life.
        this.addAbility(new SimpleStaticAbility(new BreathstealersCryptEffect()));
    }

    private BreathstealersCrypt(final BreathstealersCrypt card) {
        super(card);
    }

    @Override
    public BreathstealersCrypt copy() {
        return new BreathstealersCrypt(this);
    }
}

class BreathstealersCryptEffect extends ReplacementEffectImpl {

    BreathstealersCryptEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseLife);
        staticText = "If a player would draw a card, instead they draw a card and reveal it. " +
                "If it's a creature card, that player discards it unless they pay 3 life";
    }

    private BreathstealersCryptEffect(final BreathstealersCryptEffect effect) {
        super(effect);
    }

    @Override
    public BreathstealersCryptEffect copy() {
        return new BreathstealersCryptEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return false;
        }
        Card cardDrawn = player.getLibrary().getFromTop(game);
        // Gatherer ruling (2007-02-01)
        // If the draw is replaced by another effect, none of the rest of Fa’adiyah Seer’s ability applies,
        // even if the draw is replaced by another draw (such as with Enduring Renewal).
        if (cardDrawn == null || player.drawCards(1, source, game, event) != 1) {
            return true;
        }
        player.revealCards(source, new CardsImpl(cardDrawn), game);
        if (!cardDrawn.isCreature(game)) {
            return true;
        }
        game.informPlayers("The card drawn by " + player.getName() + " is a creature card. They discard that card unless they pay 3 life.");
        PayLifeCost cost = new PayLifeCost(3);
        if (!cost.canPay(source, source, player.getId(), game)
                || !player.chooseUse(outcome, "Pay 3 life or discard " + cardDrawn.getIdName() + "?", null, "Pay 3 life", "Discard", source, game)
                || !cost.pay(source, game, source, player.getId(), true, cost)) {
            player.discard(cardDrawn, false, source, game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
