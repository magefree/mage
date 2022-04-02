package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author North
 */
public class LoseLifeTargetControllerEffect extends OneShotEffect {

    private final DynamicValue amount;

    public LoseLifeTargetControllerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeTargetControllerEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "Its controller loses " + amount + " life";
    }

    public LoseLifeTargetControllerEffect(final LoseLifeTargetControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifeTargetControllerEffect copy() {
        return new LoseLifeTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject targetCard = targetPointer.getFirstTargetPermanentOrLKI(game, source);

        // if target is a countered spell
        if ( targetCard == null ) {
            targetCard = game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
        }

        if ( targetCard != null ) {
            Player controller = null;

            //Handles interaction with permanents that were on the battlefield.
            if ( targetCard instanceof Permanent ) {
                Permanent targetPermanent = (Permanent)targetCard;
                controller = game.getPlayer(targetPermanent.getControllerId());
            }
            //Handles interaction with spells that were on the stack.
            else if ( targetCard instanceof Spell ) {
                Spell targetSpell = (Spell)targetCard;
                controller = game.getPlayer(targetSpell.getControllerId());
            }

            if ( controller != null ) {
                controller.loseLife(amount.calculate(game, source, this), game, source, false);
                return true;
            }
        }
        return false;
    }
}
