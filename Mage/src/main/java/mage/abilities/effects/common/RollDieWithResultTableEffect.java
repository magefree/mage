package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class RollDieWithResultTableEffect extends OneShotEffect {

    protected final int sides;
    private final String prefixText;
    private final List<TableEntry> resultsTable = new ArrayList<>();
    private final DynamicValue modifier;
    private final int toIgnore;

    public RollDieWithResultTableEffect() {
        this(20);
    }

    public RollDieWithResultTableEffect(int sides) {
        this(sides, "roll a d" + sides);
    }

    public RollDieWithResultTableEffect(int sides, String prefixText) {
        this(sides, prefixText, StaticValue.get(0), 0);
    }

    public RollDieWithResultTableEffect(int sides, String prefixText, DynamicValue modifier, int toIgnore) {
        super(Outcome.Benefit);
        this.sides = sides;
        this.prefixText = prefixText;
        this.modifier = modifier;
        this.toIgnore = toIgnore;
    }

    protected RollDieWithResultTableEffect(final RollDieWithResultTableEffect effect) {
        super(effect);
        this.sides = effect.sides;
        this.prefixText = effect.prefixText;
        for (TableEntry tableEntry : effect.resultsTable) {
            this.resultsTable.add(tableEntry.copy());
        }
        this.modifier = effect.modifier.copy();
        this.toIgnore = effect.toIgnore;
    }

    @Override
    public RollDieWithResultTableEffect copy() {
        return new RollDieWithResultTableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(
                outcome, source, game, sides, 1 + toIgnore, toIgnore
        ).get(0) + modifier.calculate(game, source, this);
        this.applyResult(result, game, source);
        return true;
    }

    protected void applyResult(int result, Game game, Ability source) {
        for (TableEntry tableEntry : this.resultsTable) {
            if (tableEntry.matches(result)) {
                tableEntry.apply(game, source);
                return;
            }
        }
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefixText).append('.');
        for (TableEntry tableEntry : this.resultsTable) {
            sb.append("<br>");
            if (tableEntry.max == Integer.MAX_VALUE) {
                sb.append(tableEntry.min);
                sb.append('+');
            } else {
                if (tableEntry.min != tableEntry.max) {
                    sb.append(tableEntry.min);
                    sb.append('-');
                }
                sb.append(tableEntry.max);
            }
            sb.append(" | ");
            sb.append(CardUtil.getTextWithFirstCharUpperCase(tableEntry.effects.getText(mode)));
        }
        return sb.toString();
    }

    private static final class TableEntry implements Serializable {
        private final int min;
        private final int max;
        private final Effects effects;

        private TableEntry(int min, int max, Effect... effects) {
            this.min = min;
            this.max = max;
            this.effects = new Effects(effects);
        }

        private TableEntry(final TableEntry tableEntry) {
            this.min = tableEntry.min;
            this.max = tableEntry.max;
            this.effects = tableEntry.effects.copy();
        }

        private TableEntry copy() {
            return new TableEntry(this);
        }

        private boolean matches(int result) {
            return result >= min && result <= max;
        }

        private void apply(Game game, Ability source) {
            for (Effect effect : effects) {
                if (effect instanceof OneShotEffect) {
                    effect.apply(game, source);
                } else if (effect instanceof ContinuousEffect) {
                    game.addEffect(((ContinuousEffect) effect), source);
                }
            }
        }
    }

    public void addTableEntry(int min, int max, Effect... effects) {
        this.resultsTable.add(new TableEntry(min, max, effects));
    }

    @Override
    public Effect setTargetPointer(TargetPointer targetPointer) {
        for (TableEntry tableEntry : resultsTable) {
            tableEntry.effects.setTargetPointer(targetPointer);
        }
        return super.setTargetPointer(targetPointer);
    }
}

