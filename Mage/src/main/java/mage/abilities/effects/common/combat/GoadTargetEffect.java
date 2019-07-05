package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class GoadTargetEffect extends OneShotEffect {

    /**
     * 701.36. Goad
     * <p>
     * 701.36a Certain spells and abilities can goad a creature. Until the next
     * turn of the controller of that spell or ability, that creature attacks
     * each combat if able and attacks a player other than that player if able.
     */
    public GoadTargetEffect() {
        super(Outcome.Detriment);
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
            // TODO: impoves goad to allows to target controller, current AttacksIfAbleTargetEffect is not support it
            // https://github.com/magefree/mage/issues/5283
            /*
            If the creature doesn’t meet any of the above exceptions and can attack, it must attack a player other than
            the controller of the spell or ability that goaded it if able. It the creature can’t attack any of those
            players but could otherwise attack, it must attack an opposing planeswalker (controlled by any opponent)
            or the player that goaded it. (2016-08-23)
             */
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        return "goad target " + (!mode.getTargets().isEmpty() ? mode.getTargets().get(0).getTargetName() : " creature")
                + ". <i>(Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)</i>";
    }
}
