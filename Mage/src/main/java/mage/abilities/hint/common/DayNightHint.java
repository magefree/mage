package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author TheElk801
 */
public enum DayNightHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (!game.hasDayNight()) {
            return "It's neither day nor night.";
        }
        boolean isDay = game.checkDayNight(true);
        int spellsThisTurn = game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getActivePlayerThisTurnCount();
        StringBuilder sb = new StringBuilder("It's currently ");
        sb.append(isDay ? "day" : "night");
        sb.append(", active player has cast ");
        sb.append(spellsThisTurn);
        sb.append(" spells this turn. It will ");
        sb.append((isDay ? spellsThisTurn == 0 : spellsThisTurn >= 2) ? "" : "not");
        sb.append(" become ");
        sb.append(isDay ? "night" : "day");
        sb.append(" next turn.");
        return sb.toString();
    }

    @Override
    public DayNightHint copy() {
        return this;
    }
}
