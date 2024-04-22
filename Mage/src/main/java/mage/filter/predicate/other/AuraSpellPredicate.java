package mage.filter.predicate.other;

import mage.abilities.SpellAbility;
import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 * Needed for "becomes the target of an Aura spell" to work correctly with e.g. Disturb, Bestow
 *
 * @author xenohedron
 */
public enum AuraSpellPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (!(input instanceof Spell)) {
            return false;
        }
        SpellAbility spellAbility = ((Spell) input).getSpellAbility();
        return spellAbility != null && spellAbility.getCharacteristics(game).hasSubtype(SubType.AURA, game);
    }

    @Override
    public String toString() {
        return "an Aura spell";
    }
}
