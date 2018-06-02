
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

/**
 * Describes condition when equipped permanent has subType
 *
 * @author nantuko
 */
public class EquippedHasSubtypeCondition implements Condition {

    private SubTypeList subTypes; // scope = Any

    public EquippedHasSubtypeCondition(SubTypeList subType) {
        this.subTypes = subType;
    }


    public EquippedHasSubtypeCondition(SubType subType){
        subTypes = new SubTypeList();
        subTypes.add(subType);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
            if (attachedTo == null) {
                attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (attachedTo != null) {

                for (SubType s : subTypes) {
                    if (attachedTo.hasSubtype(s, game)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
