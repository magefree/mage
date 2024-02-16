
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LoneFox
 */
public class DamageEachOtherEffect extends OneShotEffect {

    public DamageEachOtherEffect() {
        super(Outcome.Damage);
    }

    protected DamageEachOtherEffect(final DamageEachOtherEffect effect) {
        super(effect);
    }

    @Override
    public DamageEachOtherEffect copy() {
        return new DamageEachOtherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean sourceOnBattlefield = true;
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (sourceCreature == null) {
            sourceCreature = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            sourceOnBattlefield = false;
        }

        if (sourceCreature != null && targetCreature != null
                && sourceCreature.isCreature(game)
                && targetCreature.isCreature(game)) {
            targetCreature.damage(sourceCreature.getPower().getValue(), sourceCreature.getId(), source, game, false, true);
            if (sourceOnBattlefield) {
                sourceCreature.damage(targetCreature.getPower().getValue(), targetCreature.getId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} deals damage equal to its power to target creature. That creature deals damage equal to its power to {this}";
    }

}
