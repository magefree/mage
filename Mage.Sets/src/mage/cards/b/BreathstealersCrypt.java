
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class BreathstealersCrypt extends CardImpl {

    public BreathstealersCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");

        // If a player would draw a card, instead he or she draws a card and reveals it. If it's a creature card, that player discards it unless he or she pays 3 life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BreathstealersCryptEffect()));

    }

    public BreathstealersCrypt(final BreathstealersCrypt card) {
        super(card);
    }

    @Override
    public BreathstealersCrypt copy() {
        return new BreathstealersCrypt(this);
    }
}

class BreathstealersCryptEffect extends ReplacementEffectImpl {

    public BreathstealersCryptEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseLife);
        staticText = "If a player would draw a card, instead he or she draws a card and reveals it. If it's a creature card, that player discards it unless he or she pays 3 life";
    }

    public BreathstealersCryptEffect(final BreathstealersCryptEffect effect) {
        super(effect);
    }

    @Override
    public BreathstealersCryptEffect copy() {
        return new BreathstealersCryptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            Cards oldHand = player.getHand().copy();
            if (player.drawCards(1, game, event.getAppliedEffects()) > 0) {
                Cards drawnCards = player.getHand().copy();
                drawnCards.removeAll(oldHand);
                player.revealCards(source, "The card drawn from " + player.getName() + "'s library.", drawnCards, game);
                for (Card cardDrawn : drawnCards.getCards(game)) {
                    if (cardDrawn.isCreature()) {
                        game.informPlayers("The card drawn by " + player.getName() + " is a creature card.  He/she must pay 3 life or that card gets discarded.");
                        PayLifeCost cost = new PayLifeCost(3);
                        if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                                && player.chooseUse(outcome, "Do you wish to pay 3 life to keep the card " + cardDrawn.getIdName() + "?  If not, you discard it.", source, game)) {
                            cost.pay(source, game, source.getSourceId(), player.getId(), true, cost);
                        } else {
                            game.informPlayers("The cost of 3 life was not paid by " + player.getName() + ", so " + cardDrawn.getIdName() + " will be discarded.");
                            player.discard(cardDrawn, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
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
