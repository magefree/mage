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
public class DamageTargetAndTargetControllerEffect extends OneShotEffect {

    private final int firstAmount;
    private final int secondAmount;

    /**
     * Deals simultaneous damage to the target and to the controller of the target
     */
    public DamageTargetAndTargetControllerEffect(int firstAmount, int secondAmount) {
        super(Outcome.Damage);
        this.firstAmount = firstAmount;
        this.secondAmount = secondAmount;
    }

    protected DamageTargetAndTargetControllerEffect(final DamageTargetAndTargetControllerEffect effect) {
        super(effect);
        this.firstAmount = effect.firstAmount;
        this.secondAmount = effect.secondAmount;
    }

    @Override
    public DamageTargetAndTargetControllerEffect copy() {
        return new DamageTargetAndTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.damage(firstAmount, source.getSourceId(), source, game);
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    player.damage(secondAmount, source.getSourceId(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} deals " + firstAmount + "damage to " +
                getTargetPointer().describeTargets(mode.getTargets(), "that creature") +
                " and " + secondAmount + "damage to that creature's controller";
    }
}
