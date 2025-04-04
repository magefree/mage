package mage.cards;

public interface SingleFaceSplitCardSpell extends SubCard<SingleFaceSplitCard> {

    @Override
    SingleFaceSplitCardSpell copy();

    void finalizeSpell();

    String getSpellType();
}
