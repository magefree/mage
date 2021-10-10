package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author Loki
 */
public class ReturnToHandSourceEffect extends OneShotEffect {

    boolean fromBattlefieldOnly;
    boolean returnFromNextZone;

    public ReturnToHandSourceEffect() {
        this(false);
    }

    public ReturnToHandSourceEffect(boolean fromBattlefieldOnly) {
        this(fromBattlefieldOnly, false);
    }

    /**
     *
     * @param fromBattlefieldOnly the object is only returned if it is on the
     * battlefield as the effect resolves
     * @param returnFromNextZone the object is only returned, if it has changed
     * the zone one time after the source ability triggered or was activated
     * (e.g. Angelic Destiny)
     */
    public ReturnToHandSourceEffect(boolean fromBattlefieldOnly, boolean returnFromNextZone) {
        super(Outcome.ReturnToHand);
        this.fromBattlefieldOnly = fromBattlefieldOnly;
        this.returnFromNextZone = returnFromNextZone;
        staticText = "return {this} to its owner's hand";
    }

    public ReturnToHandSourceEffect(final ReturnToHandSourceEffect effect) {
        super(effect);
        this.fromBattlefieldOnly = effect.fromBattlefieldOnly;
        this.returnFromNextZone = effect.returnFromNextZone;
    }

    @Override
    public ReturnToHandSourceEffect copy() {
        return new ReturnToHandSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject mageObject;
        if (returnFromNextZone
                && game.getState().getZoneChangeCounter(source.getSourceId()) == source.getSourceObjectZoneChangeCounter() + 1) {
            mageObject = game.getObject(source.getSourceId());
        } else {
            mageObject = source.getSourceObjectIfItStillExists(game);
        }
        if (mageObject == null) {
            return true;
        }
        switch (game.getState().getZone(mageObject.getId())) {
            case BATTLEFIELD:
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null
                        && permanent.isPhasedIn()) {
                    return controller.moveCards(permanent, Zone.HAND, source, game);
                }
                break;
            case GRAVEYARD:
                Card card = (Card) mageObject;
                return !fromBattlefieldOnly && controller.moveCards(card, Zone.HAND, source, game);
            case STACK:
                Spell spell = game.getSpell(source.getSourceId());
                return !fromBattlefieldOnly
                        && spell != null
                        && controller.moveCards(spell.getMainCard(), Zone.HAND, source, game);
        }
        return true;
    }
}
