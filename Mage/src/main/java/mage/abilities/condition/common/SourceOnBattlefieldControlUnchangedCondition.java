
package mage.abilities.condition.common;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This condition remembers controller on the first apply.
 * As long as this controller keeps unchanged and the source is
 * on the battlefield, the condition is true.
 *
 * @author LevelX2
 */
public class SourceOnBattlefieldControlUnchangedCondition implements Condition {
    
    private UUID controllerId;

    @Override
    public boolean apply(Game game, Ability source) {
        if (controllerId == null) {
            controllerId = source.getControllerId();
        }
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        return (permanent != null && Objects.equals(controllerId, source.getControllerId()));
    }

}
