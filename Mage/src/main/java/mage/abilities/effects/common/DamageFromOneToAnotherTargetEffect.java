package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

// Based on DamageWithPowerFromOneToAnotherTargetEffect
public class DamageFromOneToAnotherTargetEffect extends OneShotEffect {

    private final String firstTargetName;
    private final int damage;

    public DamageFromOneToAnotherTargetEffect(int damage) {
        this("", damage);
    }

    public DamageFromOneToAnotherTargetEffect(String firstTargetName, int damage) {
        super(Outcome.Damage);
        this.firstTargetName = firstTargetName;
        this.damage = damage;
    }

    protected DamageFromOneToAnotherTargetEffect(final DamageFromOneToAnotherTargetEffect effect) {
        super(effect);
        this.firstTargetName = effect.firstTargetName;
        this.damage = effect.damage;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + source.getTargets().size());
        }
        Permanent myPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (myPermanent == null) {
            return false;
        }
        Permanent anotherPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        Player anotherPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (anotherPermanent != null) {
            anotherPermanent.damage(damage, myPermanent.getId(), source, game, false, true);
            return true;
        } else if (anotherPlayer != null) {
            anotherPlayer.damage(damage, myPermanent.getId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public DamageFromOneToAnotherTargetEffect copy() {
        return new DamageFromOneToAnotherTargetEffect(this);
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
                " deals " + damage + " damage to " + mode.getTargets().get(1).getDescription();
    }
}
