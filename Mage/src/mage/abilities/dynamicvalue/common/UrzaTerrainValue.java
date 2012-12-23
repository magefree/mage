package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;

public class UrzaTerrainValue implements DynamicValue {
    private final int v;

    public UrzaTerrainValue(int val) {
        v = val;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        FilterControlledPermanent pp = new FilterControlledPermanent("Urza's Power Plant");
        pp.add(new NamePredicate("Urza's Power Plant"));
        PermanentsOnBattlefieldCount ppP = new PermanentsOnBattlefieldCount(pp);
        if (ppP.calculate(game, sourceAbility) < 1)
            return 1;

        FilterControlledPermanent to = new FilterControlledPermanent("Urza's Tower");
        to.add(new NamePredicate("Urza's Tower"));
        PermanentsOnBattlefieldCount toP = new PermanentsOnBattlefieldCount(to);
        if (toP.calculate(game, sourceAbility) < 1)
            return 1;

        FilterControlledPermanent mi = new FilterControlledPermanent("Urza's Mine");
        mi.add(new NamePredicate("Urza's Mine"));
        PermanentsOnBattlefieldCount miP = new PermanentsOnBattlefieldCount(mi);
        if (miP.calculate(game, sourceAbility) < 1)
            return 1;

        return v;
    }

    @Override
    public DynamicValue copy() {
        return new UrzaTerrainValue(v);
    }

    @Override
    public String toString() {
        return "1 or " + v;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
