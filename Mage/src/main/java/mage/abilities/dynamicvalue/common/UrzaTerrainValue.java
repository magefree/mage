package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

public class UrzaTerrainValue implements DynamicValue {

    private final int value;

    public UrzaTerrainValue(int value) {
        this.value = value;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        FilterControlledPermanent pp = new FilterControlledPermanent("Urza's Power Plant");
        pp.add(SubType.URZAS.getPredicate());
        pp.add(SubType.POWER_PLANT.getPredicate());
        PermanentsOnBattlefieldCount ppP = new PermanentsOnBattlefieldCount(pp);
        if (ppP.calculate(game, sourceAbility, effect) < 1) {
            return 1;
        }

        FilterControlledPermanent to = new FilterControlledPermanent("Urza's Tower");
        to.add(SubType.URZAS.getPredicate());
        to.add(SubType.TOWER.getPredicate());
        PermanentsOnBattlefieldCount toP = new PermanentsOnBattlefieldCount(to);
        if (toP.calculate(game, sourceAbility, effect) < 1) {
            return 1;
        }

        FilterControlledPermanent mi = new FilterControlledPermanent("Urza's Mine");
        mi.add(SubType.URZAS.getPredicate());
        mi.add(SubType.MINE.getPredicate());
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
