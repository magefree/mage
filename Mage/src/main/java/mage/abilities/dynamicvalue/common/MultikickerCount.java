
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum MultikickerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getKickedCounter(game, source);
                }
            }
        }
        return count;
    }

    @Override
    public MultikickerCount copy() {
        return MultikickerCount.instance;
    }

    @Override
    public String toString() {
        return "a";
    }

    @Override
    public String getMessage() {
        return "time it was kicked";
    }
}
