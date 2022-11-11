package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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

    private final ArrayList<ColoredManaSymbol> devotionColors = new ArrayList<>();
    private final String message;
    private final String reminder;
    private final Hint hint;

    DevotionCount(ColoredManaSymbol... devotionColor) {
        this.devotionColors.addAll(Arrays.asList(devotionColor));
        this.message = "your devotion to "
                + String.join(" and ", devotionColors.stream()
                        .map(ColoredManaSymbol::getColorName)
                        .collect(Collectors.toList()));

        this.reminder = "<i>(Each "
                + String.join(" and ", devotionColors.stream()
                        .map(s -> "{" + s + "}")
                        .collect(Collectors.toList()))
                + " in the mana costs of permanents you control counts toward "
                + this.message
                + ".)</i>";

        this.hint = new ValueHint(this.message.replace("your d", "D"), this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int devotion = game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .map(MageObject::getManaCost)
                .flatMap(Collection::stream)
                .mapToInt(this::checkCost)
                .sum();
        int countIncrease = game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .map(permanent -> permanent.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(IncreaseDevotionAbility.class::isInstance)
                .mapToInt(x -> 1)
                .sum();
        return devotion + countIncrease;
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
        return message;
    }

    public String getReminder() {
        return reminder;
    }

    public Hint getHint() {
        return hint;
    }

    public static final class IncreaseDevotionAbility extends SimpleStaticAbility {

        public IncreaseDevotionAbility() {
            super(new InfoEffect("Your devotion to each color and each combination of colors is increased by one."));
        }

        private IncreaseDevotionAbility(final IncreaseDevotionAbility ability) {
            super(ability);
        }

        @Override
        public IncreaseDevotionAbility copy() {
            return new IncreaseDevotionAbility(this);
        }
    }
}
