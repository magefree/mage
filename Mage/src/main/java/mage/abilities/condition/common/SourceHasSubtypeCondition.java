package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

/**
 *
 * @author Quercitron
 */
public class SourceHasSubtypeCondition implements Condition {

    private final SubTypeList subtypes;

    public SourceHasSubtypeCondition(SubTypeList subtypes) {
        this.subtypes = subtypes;
    }

    public SourceHasSubtypeCondition(SubType subType){
        subtypes = new SubTypeList();
        subtypes.add(subType);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            for (SubType subtype : subtypes) {
                if (permanent.hasSubtype(subtype, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
