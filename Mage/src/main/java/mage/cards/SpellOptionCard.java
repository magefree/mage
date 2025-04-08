package mage.cards;

public interface SpellOptionCard extends SubCard<CardWithSpellOption> {

    @Override
    SpellOptionCard copy();

    /**
     * Adds the final shared ability to the card. e.g. Adventure exile effect / Omen shuffle effect
     */
    void finalizeSpell();

    /**
     * Used to get the card type text such as Adventure. Currently only used in {@link mage.game.stack.Spell#getSpellCastText Spell} for logging the spell
     * being cast as part of the two part card.
     */
    String getSpellType();
}
