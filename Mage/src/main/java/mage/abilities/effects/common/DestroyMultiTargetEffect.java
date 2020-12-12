
package mage.abilities.effects.common;

import java.util.Iterator;
import java.util.UUID;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DestroyMultiTargetEffect extends OneShotEffect {
    protected boolean noRegen;

    public DestroyMultiTargetEffect() {
        this(false);
    }

    public DestroyMultiTargetEffect(boolean noRegen) {
        super(Outcome.DestroyPermanent);
        this.noRegen = noRegen;
    }

    public DestroyMultiTargetEffect(final DestroyMultiTargetEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
    }

    @Override
    public DestroyMultiTargetEffect copy() {
        return new DestroyMultiTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (Target target: source.getTargets()) {
            for (UUID permanentId: target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    permanent.destroy(source, game, noRegen);
                    affectedTargets++;
                }
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Destroy ");

        Iterator<Target> iterator = mode.getTargets().iterator();
        while (iterator.hasNext()) {
            Target target = iterator.next();
            if (target.getNumberOfTargets() > 1) {
                sb.append(target.getNumberOfTargets()).append(' ');
            }
            sb.append("target ").append(target.getTargetName());
            if (iterator.hasNext()) {
                sb.append(" and ");
            }
        }
        return sb.toString();
    }
}
