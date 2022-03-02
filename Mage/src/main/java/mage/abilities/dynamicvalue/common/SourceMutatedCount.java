package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801, NinthWorld
 */
public enum SourceMutatedCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null) {
            Permanent mutatedOver =
                    sourcePermanent.isMutatedUnder() ? sourcePermanent.getMutatedUnder() :
                    sourcePermanent.isMutateOver() ? sourcePermanent :
                    null;
            if (mutatedOver != null) {
                return sourcePermanent.getMutatedOverList().size();
            }
        }
        return 0;
    }

    @Override
    public SourceMutatedCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of times this creature has mutated";
    }
}
