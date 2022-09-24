package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author JayDi85
 */
public class DamageWithPowerFromOneToAnotherTargetEffect extends OneShotEffect {

    String firstTargetName;

    public DamageWithPowerFromOneToAnotherTargetEffect() {
        this("");
    }

    public DamageWithPowerFromOneToAnotherTargetEffect(String firstTargetName) {
        super(Outcome.Damage);
        this.firstTargetName = firstTargetName;
    }

    public DamageWithPowerFromOneToAnotherTargetEffect(final DamageWithPowerFromOneToAnotherTargetEffect effect) {
        super(effect);
        this.firstTargetName = effect.firstTargetName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + source.getTargets().size());
        }

        Permanent myPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent anotherPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        Player anotherPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());

        if (myPermanent != null && anotherPermanent != null) {
            anotherPermanent.damage(myPermanent.getPower().getValue(), myPermanent.getId(), source, game, false, true);
            return true;
        } else if (myPermanent != null && anotherPlayer != null) {
            anotherPlayer.damage(myPermanent.getPower().getValue(), myPermanent.getId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public DamageWithPowerFromOneToAnotherTargetEffect copy() {
        return new DamageWithPowerFromOneToAnotherTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        if (mode.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + mode.getTargets().size());
        }

        return (firstTargetName.isEmpty() ? mode.getTargets().get(0).getDescription() : firstTargetName) + " deals damage equal to its power to " + mode.getTargets().get(1).getDescription();
    }
}
