package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author jeff
 */
public class ReturnToHandAttachedEffect extends OneShotEffect {

    public ReturnToHandAttachedEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return that card to its owner's hand";
    }

    public ReturnToHandAttachedEffect(final ReturnToHandAttachedEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandAttachedEffect copy() {
        return new ReturnToHandAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("attachedTo");
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        // TODO: Once MDFC ZCC increments are fixed properly, can remove this special case. For now must allow so effect works.
        if (permanent.getZoneChangeCounter(game) + 1 != card.getZoneChangeCounter(game) && !(card instanceof ModalDoubleFacedCard)) {
            return false;
        }
        return player.moveCards(card, Zone.HAND, source, game);
    }
}
