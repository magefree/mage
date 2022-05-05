package mage.abilities.condition.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ControlsPermanentGreatestCMCCondition implements Condition {

    private final FilterPermanent filter;

    public ControlsPermanentGreatestCMCCondition() {
        this(new FilterPermanent());
    }

    public ControlsPermanentGreatestCMCCondition(FilterPermanent filter) {
        super();
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> controllers = new HashSet<>();
        Integer maxCMC = null;

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            int cmc = permanent.getManaCost().manaValue();
            if (maxCMC == null || cmc > maxCMC) {
                maxCMC = cmc;
                controllers.clear();
            }
            if (cmc == maxCMC) {
                controllers.add(permanent.getControllerId());
            }
        }
        return controllers.contains(source.getControllerId());
    }

    @Override
    public String toString() {
        return "you control the " + filter.getMessage() + " with the highest mana value or tied for the highest mana value";
    }

}
