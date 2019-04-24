
package mage.abilities.dynamicvalue.common;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class PermanentsYouOwnThatOpponentsControlCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Set<UUID> opponentIds = game.getOpponents(sourceAbility.getControllerId());
        int count = 0;

        for (Permanent permanent : game.getBattlefield().getActivePermanents(sourceAbility.getControllerId(), game)) {
            if (!permanent.isOwnedBy(permanent.getControllerId()) && permanent.isOwnedBy(sourceAbility.getControllerId())) {
                if (opponentIds.contains(permanent.getControllerId())) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public PermanentsYouOwnThatOpponentsControlCount copy() {
        return new PermanentsYouOwnThatOpponentsControlCount();
    }

    @Override
    public String getMessage() {
        return "number of permanents you own that your opponents control";
    }

    @Override
    public String toString() {
        return "1";
    }
}
