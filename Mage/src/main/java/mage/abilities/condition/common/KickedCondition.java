package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.KickerAbility;
import mage.game.Game;

/**
 * Describes condition when spell was kicked.
 *
 * @author LevelX2
 */
public enum KickedCondition implements Condition {
    ONCE(1, ""),
    TWICE(2, "twice");

    private final int kickedCount;
    private final String text;

    KickedCondition(int kickedCount, String text) {
        this.kickedCount = kickedCount;
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return KickerAbility.getSourceObjectKickedCount(game, source) >= kickedCount;
    }

    @Override
    public String toString() {
        return "{this} was kicked" + (text.isEmpty() ? "" : " " + text);
    }
}
