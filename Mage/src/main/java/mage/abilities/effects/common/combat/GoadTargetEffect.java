
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class GoadTargetEffect extends OneShotEffect {

    /**
     * 701.36. Goad
     *
     * 701.36a Certain spells and abilities can goad a creature. Until the next
     * turn of the controller of that spell or ability, that creature attacks
     * each combat if able and attacks a player other than that player if able.
     */
    public GoadTargetEffect() {
        super(Outcome.Benefit);
    }

    public GoadTargetEffect(final GoadTargetEffect effect) {
        super(effect);
    }

    @Override
    public GoadTargetEffect copy() {
        return new GoadTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetCreature != null && controller != null) {
            ContinuousEffect effect = new AttacksIfAbleTargetEffect(Duration.UntilYourNextTurn);
            effect.setTargetPointer(new FixedTarget(getTargetPointer().getFirst(game, source)));
            game.addEffect(effect, source);
            effect = new CantAttackYouEffect(Duration.UntilYourNextTurn);
            effect.setTargetPointer(new FixedTarget(getTargetPointer().getFirst(game, source)));
            game.addEffect(effect, source);
            game.informPlayers(controller.getLogName() + " is goading " + targetCreature.getLogName());
        }
        return true;
    }
}
