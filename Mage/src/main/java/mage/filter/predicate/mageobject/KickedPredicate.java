package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.KickerAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author TheElk801
 */
public enum KickedPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        Spell spell = game.getSpell(input.getId());
        if (spell == null) {
            return false;
        }
        for (Ability ability : spell.getAbilities()) {
            if (ability instanceof KickerAbility
                    && ((KickerAbility) ability).getKickedCounter(game, spell.getSpellAbility()) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Kicked";
    }
}
