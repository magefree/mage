package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Quercitron
 */
public class SourceHasSubtypeCondition implements Condition {

    private final List<SubType> subtypes = new ArrayList<>();

    public SourceHasSubtypeCondition(SubType... subTypes) {
        this.subtypes.addAll(Arrays.asList(subTypes));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        for (SubType subtype : subtypes) {
            if (permanent.hasSubtype(subtype, game)) {
                return true;
            }
        }
        return false;
    }
}
