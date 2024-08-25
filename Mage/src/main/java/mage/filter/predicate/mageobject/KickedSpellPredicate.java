package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.keyword.KickerAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * Find spell's kicked stats.
 * <p>
 * Warning, must be used for SPELL_CAST events only
 *
 * @author TheElk801
 */
public enum KickedSpellPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        if (input instanceof Spell) {
            return KickerAbility.getKickedCounter(game, ((Spell) input).getSpellAbility()) > 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Kicked";
    }
}
