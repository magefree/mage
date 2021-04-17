package mage.cards;

/**
 *
 * @author phulin
 */
public interface AdventureCardSpell extends Card {

    @Override
    AdventureCardSpell copy();

    void setParentCard(AdventureCard card);

    AdventureCard getParentCard();
}
