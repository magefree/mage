package mage.abilities.condition.common;

import java.util.List;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class SourceHasSubtypeCondition implements Condition {

    private final List<String> subtypes;

    public SourceHasSubtypeCondition(List<String> subtypes) {
        this.subtypes = subtypes;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            for (String subtype : subtypes) {
                if (permanent.hasSubtype(subtype)) {
                    return true;
                }
            }
        }
        return false;
    }
}
