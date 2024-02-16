
package mage.abilities.condition.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Condition for:
 *   - if you control the creature with the greatest toughness or tied for the greatest toughness
 *
 * @author LevelX2
 */
public enum ControlsCreatureGreatestToughnessCondition implements Condition {

    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> controllers = new HashSet<>();
        Integer maxToughness = null;

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            if (permanent == null) {
                continue;
            }

            int toughness = permanent.getToughness().getValue();
            if (maxToughness == null || toughness > maxToughness) {
                maxToughness = permanent.getToughness().getValue();
                controllers.clear();
            }
            if (toughness == maxToughness) {
                controllers.add(permanent.getControllerId());
            }
        }
        return controllers.contains(source.getControllerId());
    }

    @Override
    public String toString() {
        return "you control the creature with the greatest toughness or tied for the greatest toughness";
    }

}