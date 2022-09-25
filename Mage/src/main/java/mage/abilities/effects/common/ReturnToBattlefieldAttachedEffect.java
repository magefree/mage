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
 * Used for "Return {this} to the battlefield attached to that creature at the beginning of the next end step" abilities.
 * E.g.g Gift of Immortality and Next of Kin.
 *
 * @author LevelX2, Alex-Vasile
 */
public class ReturnToBattlefieldAttachedEffect extends OneShotEffect {

    public ReturnToBattlefieldAttachedEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return {this} to the battlefield attached to that creature at the beginning of the next end step";
    }

    public ReturnToBattlefieldAttachedEffect(final ReturnToBattlefieldAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Card aura = game.getCard(source.getSourceId());
        if (controller == null
                || creature == null
                || aura == null || game.getState().getZone(aura.getId()) != Zone.GRAVEYARD) {
            return false;
        }

        game.getState().setValue("attachTo:" + aura.getId(), creature);
        controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
        return creature.addAttachment(aura.getId(), source, game);
    }

    @Override
    public ReturnToBattlefieldAttachedEffect copy() {
        return new ReturnToBattlefieldAttachedEffect(this);
    }
}