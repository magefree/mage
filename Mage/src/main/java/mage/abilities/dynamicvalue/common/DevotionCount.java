package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Each colored mana symbol (e.g. {U}) in the mana costs of permanents you
 * control counts toward your devotion to that color.
 *
 * @author LevelX2
 */
public enum DevotionCount implements DynamicValue {
    W(ColoredManaSymbol.W),
    U(ColoredManaSymbol.U),
    B(ColoredManaSymbol.B),
    R(ColoredManaSymbol.R),
    G(ColoredManaSymbol.G),
    WU(ColoredManaSymbol.W, ColoredManaSymbol.U),
    WB(ColoredManaSymbol.W, ColoredManaSymbol.B),
    UB(ColoredManaSymbol.U, ColoredManaSymbol.B),
    UR(ColoredManaSymbol.U, ColoredManaSymbol.R),
    BR(ColoredManaSymbol.B, ColoredManaSymbol.R),
    BG(ColoredManaSymbol.B, ColoredManaSymbol.G),
    RG(ColoredManaSymbol.R, ColoredManaSymbol.G),
    RW(ColoredManaSymbol.R, ColoredManaSymbol.W),
    GW(ColoredManaSymbol.G, ColoredManaSymbol.W),
    GU(ColoredManaSymbol.G, ColoredManaSymbol.U);

    private ArrayList<ColoredManaSymbol> devotionColors = new ArrayList<>();

    DevotionCount(ColoredManaSymbol... devotionColor) {
        this.devotionColors.addAll(Arrays.asList(devotionColor));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .map(MageObject::getManaCost)
                .flatMap(Collection::stream)
                .mapToInt(this::checkCost)
                .sum();
    }

    private int checkCost(ManaCost manaCost) {
        return devotionColors.stream().anyMatch(manaCost::containsColor) ? 1 : 0;
    }

    @Override
    public DevotionCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("your devotion to ");
        int count = 0;
        for (ColoredManaSymbol coloredManaSymbol : devotionColors) {
            if (count > 0) {
                sb.append(" and ");
            }
            sb.append(coloredManaSymbol.getColorName());
            count++;
        }
        return sb.toString();
    }
}
