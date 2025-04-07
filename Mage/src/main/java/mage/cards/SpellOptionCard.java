package mage.cards;

public interface SpellOptionCard extends SubCard<CardWithSpellOption> {

    @Override
    SpellOptionCard copy();

    void finalizeSpell();

    String getSpellType();
}
