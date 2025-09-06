package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Used for [target creatures] deal damage equal to their power to [target creature]
 * <br> set targets using tags <i>.setTargetTag(1)</i>
 * <br> set the first target tag for creatures dealing damage
 * <br> set the second target tag for additional creatures, not required (Friendly Rivalry)
 * <br> set the third target tag for creatures receiving damage
 *
 * @author Jmlundeen
 */
public class TargetsDamageTargetsEffect extends OneShotEffect {

    private final boolean describeDamagingTargets;

    public TargetsDamageTargetsEffect(boolean describeDamagingTargets) {
        super(Outcome.Benefit);
        this.describeDamagingTargets = describeDamagingTargets;
    }

    private TargetsDamageTargetsEffect(final TargetsDamageTargetsEffect effect) {
        super(effect);
        this.describeDamagingTargets = effect.describeDamagingTargets;
    }

    @Override
    public TargetsDamageTargetsEffect copy() {
        return new TargetsDamageTargetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() < 2) {
            return false;
        }

        Target damageTarget = source.getTargets().getByTag(1);
        Target additionalDamageTarget = source.getTargets().getByTag(2);
        Target destTarget = source.getTargets().getByTag(3);

        List<Permanent> damagingPermanents = new ArrayList<>();
        List<Permanent> receivingPermanents = new ArrayList<>();
        for (UUID id : damageTarget.getTargets()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                damagingPermanents.add(permanent);
            }
        }
        if (additionalDamageTarget != null) {
            for (UUID id : additionalDamageTarget.getTargets()) {
                Permanent permanent = game.getPermanent(id);
                if (permanent != null) {
                    damagingPermanents.add(permanent);
                }
            }
        }
        for (UUID id : destTarget.getTargets()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                receivingPermanents.add(permanent);
            }
        }

        if (receivingPermanents.isEmpty() || damagingPermanents.isEmpty()) {
            return false;
        }
        for (Permanent receivingPermanent : receivingPermanents) {
            for (Permanent damagingPermanent: damagingPermanents) {
                receivingPermanent.damage(damagingPermanent.getPower().getValue(), damagingPermanent.getId(), source, game, false, true);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (describeDamagingTargets) {
            sb.append(mode.getTargets().getByTag(1).getDescription());
            if (mode.getTargets().getByTag(2) != null) {
                sb.append(" and ").append(mode.getTargets().getByTag(2).getDescription());
            }
        } else {
            sb.append("they");
        }
        sb.append(" each deal damage equal to their power to ");
        sb.append(mode.getTargets().getByTag(3).getDescription());
        return sb.toString();
    }
}