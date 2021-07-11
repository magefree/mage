
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class Rowen extends CardImpl {

    public Rowen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // Reveal the first card you draw each turn. Whenever you reveal a basic land card this way, draw a card.
        this.addAbility(new RowenAbility());
    }

    private Rowen(final Rowen card) {
        super(card);
    }

    @Override
    public Rowen copy() {
        return new Rowen(this);
    }
}

class RowenAbility extends TriggeredAbilityImpl {

    private int lastTriggeredTurn;

    RowenAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect(""), false);
    }

    RowenAbility(final RowenAbility ability) {
        super(ability);
    }

    @Override
    public RowenAbility copy() {
        return new RowenAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            if (game.isActivePlayer(this.getControllerId()) && this.lastTriggeredTurn != game.getTurnNum()) {
                Card card = game.getCard(event.getTargetId());
                Player controller = game.getPlayer(this.getControllerId());
                Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
                if (card != null && controller != null && sourcePermanent != null) {
                    lastTriggeredTurn = game.getTurnNum();
                    controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
                    this.getEffects().clear();
                    if (card.isLand(game) && card.isBasic()) {
                        this.addEffect(new DrawCardSourceControllerEffect(1));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Reveal the first card you draw each turn. Whenever you reveal a basic land card this way, draw a card.";
    }    
}
