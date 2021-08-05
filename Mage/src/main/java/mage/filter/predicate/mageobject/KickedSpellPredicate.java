package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.keyword.KickerAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * Find spell's kicked stats.
 * <p>
 * Warning, must be used for SPELL_CAST events only
 * (if you need kicked stats in ETB effects then search object's abilities instead spell,
 * see MultikickerCount as example)
 *
 * @author TheElk801
 */
public enum KickedSpellPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return KickerAbility.getSpellKickedCount(game, input.getId()) > 0;
    }

    @Override
    public String toString() {
        return "Kicked";
    }
}
