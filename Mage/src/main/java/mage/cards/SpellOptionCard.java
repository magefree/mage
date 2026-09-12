package mage.cards;

import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nandmp
 */
public interface SpellOptionCard extends SubCard<CardWithSpellOption> {

    @Override
    SpellOptionCard copy();

    /**
     * Adds the final shared ability to the card. e.g. Adventure exile effect / Omen shuffle effect
     */
    void finalizeSpell();

    /**
     * Returns rules for the inset frame. The combined card uses the spell
     * ability's full rule, while the inset already supplies its own name, type,
     * and mana cost.
     */
    default List<String> getInsetRules(Game game) {
        List<String> rules = new ArrayList<>(getRules(game));
        if (!(getSpellAbility() instanceof SpellOptionCardSpellAbility)) {
            return rules;
        }

        SpellOptionCardSpellAbility spellAbility = (SpellOptionCardSpellAbility) getSpellAbility();
        // Keep additional-cost rules and dynamic game information intact; only
        // substitute the rule contributed by the inset spell ability itself.
        String fullRule = spellAbility.getRule();
        for (int i = 0; i < rules.size(); i++) {
            if (fullRule.equals(rules.get(i))) {
                rules.set(i, spellAbility.getInsetRule());
                break;
            }
        }
        return rules;
    }

    /**
     * Used to get the card type text such as Adventure. Currently only used in {@link mage.game.stack.Spell#getSpellCastText Spell} for logging the spell
     * being cast as part of the two part card.
     */
    String getSpellType();
}
