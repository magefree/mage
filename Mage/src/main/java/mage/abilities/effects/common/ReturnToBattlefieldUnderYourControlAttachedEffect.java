
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
 * @author noxx
 */
public class ReturnToBattlefieldUnderYourControlAttachedEffect extends OneShotEffect {

    public ReturnToBattlefieldUnderYourControlAttachedEffect() {
        this("that card");
    }

    /**
     * @param objectText text for the object to return (default: "that card") if
     *                   you use constructor without this param
     */
    public ReturnToBattlefieldUnderYourControlAttachedEffect(String objectText) {
        super(Outcome.Benefit);
        staticText = "return " + objectText + " to the battlefield under your control";
    }

    protected ReturnToBattlefieldUnderYourControlAttachedEffect(final ReturnToBattlefieldUnderYourControlAttachedEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToBattlefieldUnderYourControlAttachedEffect copy() {
        return new ReturnToBattlefieldUnderYourControlAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = getValue("attachedTo");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object instanceof Permanent) {
            Card card = game.getCard(((Permanent) object).getId());
            // Move the card only, if it is still in the next zone after the battlefield
            if (card != null && card.getZoneChangeCounter(game) == ((Permanent) object).getZoneChangeCounter(game) + 1) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                return true;
            }
        }

        return false;
    }

}
