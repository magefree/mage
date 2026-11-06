
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
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

class RowenAbility extends DrawNthCardTriggeredAbility {

    RowenAbility() {
        super(new DrawCardSourceControllerEffect(1), false, 1);
    }

    private RowenAbility(final RowenAbility ability) {
        super(ability);
    }

    @Override
    public RowenAbility copy() {
        return new RowenAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        // reveal the first card as it's drawn, then trigger only for basic land card
        Card card = game.getCard(event.getTargetId());
        Player controller = game.getPlayer(this.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (card == null || controller == null || sourcePermanent == null) {
            return false;
        }
        controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
        return card.isLand(game) && card.isBasic(game);
    }

    @Override
    public String getRule() {
        return "Reveal the first card you draw each turn. Whenever you reveal a basic land card this way, draw a card.";
    }
}
