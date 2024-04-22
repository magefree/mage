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
public class DamageWithPowerFromSourceToAnotherTargetEffect extends OneShotEffect {

    String sourceTargetName;

    public DamageWithPowerFromSourceToAnotherTargetEffect(String sourceTargetName) {
        super(Outcome.Damage);
        this.sourceTargetName = sourceTargetName;
    }

    protected DamageWithPowerFromSourceToAnotherTargetEffect(final DamageWithPowerFromSourceToAnotherTargetEffect effect) {
        super(effect);
        this.sourceTargetName = effect.sourceTargetName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() != 1) {
            throw new IllegalStateException("It must have one target, but found " + source.getTargets().size());
        }

        Permanent myPermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent anotherPermanent = game.getPermanent(source.getFirstTarget());
        Player anotherPlayer = game.getPlayer(source.getFirstTarget());

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
    public DamageWithPowerFromSourceToAnotherTargetEffect copy() {
        return new DamageWithPowerFromSourceToAnotherTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        if (mode.getTargets().size() != 1) {
            throw new IllegalStateException("It must have one targets, but found " + mode.getTargets().size());
        }

        // It deals damage equal to its power to target creature you don't control
        return sourceTargetName + " deals damage equal to its power to "
                + getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
