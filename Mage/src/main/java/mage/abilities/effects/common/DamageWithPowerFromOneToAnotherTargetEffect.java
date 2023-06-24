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

    private final String firstTargetName;
    private final int multiplier;

    public DamageWithPowerFromOneToAnotherTargetEffect() {
        this("");
    }

    public DamageWithPowerFromOneToAnotherTargetEffect(String firstTargetName) {
        this(firstTargetName, 1);
    }

    public DamageWithPowerFromOneToAnotherTargetEffect(String firstTargetName, int multiplier) {
        super(Outcome.Damage);
        this.firstTargetName = firstTargetName;
        this.multiplier = multiplier;
    }

    public DamageWithPowerFromOneToAnotherTargetEffect(final DamageWithPowerFromOneToAnotherTargetEffect effect) {
        super(effect);
        this.firstTargetName = effect.firstTargetName;
        this.multiplier = effect.multiplier;
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

        return (firstTargetName.isEmpty() ? mode.getTargets().get(0).getDescription() : firstTargetName) +
                " deals damage equal to" + (multiplier == 2 ? " twice" : "") +
                " its power to " + mode.getTargets().get(1).getDescription();
    }
}
