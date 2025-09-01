
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Quercitron
 */
public class ReturnToBattlefieldUnderOwnerControlAttachedEffect extends OneShotEffect {

    private final boolean tapped;

    public ReturnToBattlefieldUnderOwnerControlAttachedEffect() {
        this("that card", false);
    }

    public ReturnToBattlefieldUnderOwnerControlAttachedEffect(String textCard, boolean tapped) {
        super(Outcome.Neutral);
        this.tapped = tapped;
        staticText = "return " + textCard + " to the battlefield "
                + (tapped ? "tapped " : "")
                + "under its owner's control";
    }

    protected ReturnToBattlefieldUnderOwnerControlAttachedEffect(final ReturnToBattlefieldUnderOwnerControlAttachedEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlAttachedEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Object attached = getValue("attachedTo");
        Object zcc = getValue("zcc");
        if (attached instanceof Permanent && zcc instanceof Integer) {
            Card card = game.getCard(((Permanent) attached).getId());
            if (card != null && (int) zcc == card.getZoneChangeCounter(game)) {
                return controller.moveCards(card, Zone.BATTLEFIELD, source, game, this.tapped, false, true, null);
            }
        }
        return false;
    }
}
