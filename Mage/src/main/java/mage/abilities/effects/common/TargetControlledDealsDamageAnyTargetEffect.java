package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * Used for "target CREATURE TYPE you control deals damage equal to its power to any target.
 *
 * @author Alex-Vasile
 */
public class TargetControlledDealsDamageAnyTargetEffect extends OneShotEffect {

    public TargetControlledDealsDamageAnyTargetEffect() {
        this("creature");
    }

    public TargetControlledDealsDamageAnyTargetEffect(String damagingPermanentDescription) {
        super(Outcome.Damage);
        this.staticText = "target " + damagingPermanentDescription + " deals damage equal to its power to any target.";
    }

    TargetControlledDealsDamageAnyTargetEffect(final TargetControlledDealsDamageAnyTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent;
        if (game.getPermanent(source.getTargets().get(0).getFirstTarget()) == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        } else {
            sourcePermanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        }
        if (sourcePermanent == null) {
            return false;
        }

        int damage = sourcePermanent.getPower().getValue();

        UUID targetId = source.getTargets().get(1).getFirstTarget();

        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(damage, sourcePermanent.getId(), source, game, false, true);
            return true;
        }

        Player player = game.getPlayer(targetId);
        if (player != null) {
            player.damage(damage, sourcePermanent.getId(), source, game);
            return true;
        }

        return false;
    }

    @Override
    public TargetControlledDealsDamageAnyTargetEffect copy() {
        return new TargetControlledDealsDamageAnyTargetEffect(this);
    }
}
