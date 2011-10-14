package mage.abilities.dynamicvalue.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Loki
 */
public class DomainValue implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int havePlains = 0;
        int haveIslands = 0;
        int haveMountains = 0;
        int haveSwamps = 0;
        int haveForests = 0;
        for (Permanent p : game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId())) {
            if (p.getCardType().contains(Constants.CardType.LAND)) {
                if (havePlains == 0 && p.getSubtype().contains("Plains"))
                    havePlains = 1;
                if (haveIslands == 0 && p.getSubtype().contains("Island"))
                    haveIslands = 1;
                if (haveMountains == 0 && p.getSubtype().contains("Mountain"))
                    haveMountains = 1;
                if (haveSwamps == 0 && p.getSubtype().contains("Swamp"))
                    haveSwamps = 1;
                if (haveForests == 0 && p.getSubtype().contains("Forest"))
                    haveForests = 1;
            }
        }
        return haveForests + haveIslands + haveMountains + havePlains + haveSwamps;
    }

    @Override
    public DynamicValue clone() {
        return new DomainValue();
    }

    @Override
    public String toString() {
        return "1";
    }


    @Override
    public String getMessage() {
        return "each basic land type among lands you control";
    }
}
