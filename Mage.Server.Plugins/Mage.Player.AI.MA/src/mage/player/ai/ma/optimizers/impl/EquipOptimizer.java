package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.abilities.keyword.EquipAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * Make sure that AI won't equip the same creature equip already attached to.
 *
 * @author ayratn
 */
public class EquipOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions) {
        for (Ability ability : actions) {
            if (ability instanceof EquipAbility) {
                Permanent permanent = game.getPermanent(ability.getFirstTarget());
                if (permanent != null) {
                    // check that equipment is not already attached to {this}
                    if (permanent.getAttachments().contains(ability.getSourceId())) {
                        removeAbility(ability);
                    }
                }
            }
        }
    }
}
