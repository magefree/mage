
package mage.cards.p;

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
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class PrimitiveEtchings extends CardImpl {

    public PrimitiveEtchings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // Reveal the first card you draw each turn. Whenever you reveal a creature card this way, draw a card.
        this.addAbility(new PrimitiveEtchingsAbility());
    }

    private PrimitiveEtchings(final PrimitiveEtchings card) {
        super(card);
    }

    @Override
    public PrimitiveEtchings copy() {
        return new PrimitiveEtchings(this);
    }
}

class PrimitiveEtchingsAbility extends DrawNthCardTriggeredAbility {

    PrimitiveEtchingsAbility() {
        super(new DrawCardSourceControllerEffect(1), false, 1);
    }

    private PrimitiveEtchingsAbility(final PrimitiveEtchingsAbility ability) {
        super(ability);
    }

    @Override
    public PrimitiveEtchingsAbility copy() {
        return new PrimitiveEtchingsAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        // reveal the first card as it's drawn, then trigger only for creature card
        Card card = game.getCard(event.getTargetId());
        Player controller = game.getPlayer(this.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (card == null || controller == null || sourcePermanent == null) {
            return false;
        }
        controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
        return card.isCreature(game);
    }

    @Override
    public String getRule() {
        return "Reveal the first card you draw each turn. Whenever you reveal a creature card this way, draw a card.";
    }
}
