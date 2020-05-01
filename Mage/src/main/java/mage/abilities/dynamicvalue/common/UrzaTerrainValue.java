package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;

public enum UrzaTerrainValue implements DynamicValue {
    MINE(2, SubType.MINE),
    TOWER(3, SubType.TOWER),
    POWER_PLANT(2, SubType.POWER_PLANT);

    private final int value;
    private final SubType subType;

    private static final FilterPermanent mineFilter = new FilterPermanent(SubType.MINE, "");
    private static final FilterPermanent towerFilter = new FilterPermanent(SubType.MINE, "");
    private static final FilterPermanent powerPlantFilter = new FilterPermanent(SubType.POWER_PLANT, "");

    static {
        mineFilter.add(SubType.URZAS.getPredicate());
        towerFilter.add(SubType.URZAS.getPredicate());
        powerPlantFilter.add(SubType.URZAS.getPredicate());
    }

    UrzaTerrainValue(int value, SubType subType) {
        this.value = value;
        this.subType = subType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (subType == SubType.MINE || game.getBattlefield().countAll(
                mineFilter, sourceAbility.getControllerId(), game
        ) < 1) {
            return 1;
        }
        if (subType == SubType.TOWER || game.getBattlefield().countAll(
                towerFilter, sourceAbility.getControllerId(), game
        ) < 1) {
            return 1;
        }
        if (subType == SubType.POWER_PLANT || game.getBattlefield().countAll(
                powerPlantFilter, sourceAbility.getControllerId(), game
        ) < 1) {
            return 1;
        }
        return value;
    }

    @Override
    public UrzaTerrainValue copy() {
        return this;
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
