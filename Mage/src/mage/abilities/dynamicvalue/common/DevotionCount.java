/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.dynamicvalue.common;

import java.util.ArrayList;
import java.util.Arrays;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Each colored mana symbol (e.g. {U}) in the mana costs of permanents you control counts toward your devotion to that color.
 *
 * @author LevelX2
 */
public class DevotionCount implements DynamicValue {

    private ArrayList<ColoredManaSymbol> devotionColors = new ArrayList();

    public DevotionCount(ColoredManaSymbol... devotionColor) {
        this.devotionColors.addAll(Arrays.asList(devotionColor));
    }

    public DevotionCount(final DevotionCount dynamicValue) {
        this.devotionColors = dynamicValue.devotionColors;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int devotion = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId())) {
            for(ManaCost manaCost :permanent.getManaCost()) {
                for(ColoredManaSymbol coloredManaSymbol: devotionColors) {
                    if (manaCost.containsColor(coloredManaSymbol)) {
                        devotion++;
                        break; // count each manaCost maximum of one time (Hybrid don't count for multiple colors of devotion)
                    }
                }
            }
        }
        return devotion;
    }

    @Override
    public DynamicValue copy() {
        return new DevotionCount(this);
    }

    @Override
    public String toString() {
        return "put a number of";
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("devotion to ");
        int count = 0;
        for (ColoredManaSymbol coloredManaSymbol:devotionColors) {
            if (count > 0) {
                sb.append(" and ");
            }
            sb.append(coloredManaSymbol.getColorName());
            count++;
        }
        return sb.toString();
    }
}
