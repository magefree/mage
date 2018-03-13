package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

public class UrzaTerrainValue implements DynamicValue {

    private final int value;

    public UrzaTerrainValue(int value) {
        this.value = value;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        FilterControlledPermanent pp = new FilterControlledPermanent("Urza's Power Plant");
        pp.add(new SubtypePredicate(SubType.URZAS));
        pp.add(new SubtypePredicate(SubType.POWER_PLANT));
        PermanentsOnBattlefieldCount ppP = new PermanentsOnBattlefieldCount(pp);
        if (ppP.calculate(game, sourceAbility, effect) < 1) {
            return 1;
        }

        FilterControlledPermanent to = new FilterControlledPermanent("Urza's Tower");
        to.add(new SubtypePredicate(SubType.URZAS));
        to.add(new SubtypePredicate(SubType.TOWER));
        PermanentsOnBattlefieldCount toP = new PermanentsOnBattlefieldCount(to);
        if (toP.calculate(game, sourceAbility, effect) < 1) {
            return 1;
        }

        FilterControlledPermanent mi = new FilterControlledPermanent("Urza's Mine");
        mi.add(new SubtypePredicate(SubType.URZAS));
        mi.add(new SubtypePredicate(SubType.MINE));
        PermanentsOnBattlefieldCount miP = new PermanentsOnBattlefieldCount(mi);
        if (miP.calculate(game, sourceAbility, effect) < 1) {
            return 1;
        }

        return value;
    }

    @Override
    public UrzaTerrainValue copy() {
        return new UrzaTerrainValue(value);
    }

    @Override
    public String toString() {
        return "1 or " + value;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
