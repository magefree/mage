package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author xenohedron
 */
public class DamageTargetAndTargetEffect extends OneShotEffect {

    private final int firstAmount;
    private final int secondAmount;

    /**
     * Deals simultaneous damage to two targets. Must set target tag 1 and 2
     */
    public DamageTargetAndTargetEffect(int firstAmount, int secondAmount) {
        super(Outcome.Damage);
        this.firstAmount = firstAmount;
        this.secondAmount = secondAmount;
    }

    protected DamageTargetAndTargetEffect(final DamageTargetAndTargetEffect effect) {
        super(effect);
        this.firstAmount = effect.firstAmount;
        this.secondAmount = effect.secondAmount;
    }

    @Override
    public DamageTargetAndTargetEffect copy() {
        return new DamageTargetAndTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets().getTargetsByTag(1).forEach(uuid -> damageTarget(uuid, firstAmount, source, game));
        source.getTargets().getTargetsByTag(2).forEach(uuid -> damageTarget(uuid, secondAmount, source, game));
        return true;
    }

    private void damageTarget(UUID targetId, int amount, Ability source, Game game) {
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(amount, source.getSourceId(), source, game) ;
        } else {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                player.damage(amount, source.getSourceId(), source, game);
            }
        }
    }

    @Override
    public String getText(Mode mode) {
        // verify check that target tags are properly setup
        if (mode.getTargets().getByTag(1) == null || mode.getTargets().getByTag(2) == null) {
            throw new IllegalArgumentException("Wrong code usage: need to add tags to targets");
        }
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} deals " + firstAmount + " damage to " + mode.getTargets().getByTag(1).getDescription() +
                " and " + secondAmount + " damage to " + mode.getTargets().getByTag(2).getDescription();
    }
}
