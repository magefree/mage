

package mage.abilities.condition.common;

import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;


/**
 * Describes condition when a card is suspended
 * 702.60b A card is "suspended" if it's in the exile zone, has suspend, and has a time counter on it.
 *
 * @author LevelX2
 * 
 */

public enum SuspendedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            boolean found = card.getAbilities(game).containsClass(SuspendAbility.class);

            if (!found) {
                found = game.getState().getAllOtherAbilities(source.getSourceId()).containsClass(SuspendAbility.class);

            }
            if (found) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED &&
                        card.getCounters(game).getCount(CounterType.TIME) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
