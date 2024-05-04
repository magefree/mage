package mage.constants;

/**
 * Groups together the most usual ways a card's payment is adjusted
 * by card effects that allow play or cast.
 * <p>
 * Effects should attempt to support those for all the various ways
 * to play/cast cards/spells in Effects
 *
 * @author Susucr
 */
public enum CastManaAdjustment {
    /**
     * No adjustment to play/cast
     */
    NONE,
    /**
     * Mana can be used as any mana type to pay for the mana cost
     */
    AS_THOUGH_ANY_MANA_TYPE,
    /**
     * Mana can be used as any mana color to pay for the mana cost
     */
    AS_THOUGH_ANY_MANA_COLOR,
    /**
     * The card is play/cast without paying for its mana cost
     */
    WITHOUT_PAYING_MANA_COST,
}