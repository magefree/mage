
package mage.cards;

/**
 * @author stravant@gmail.com
 * 
 * Enum listing the possible card faces for a card
 * 
 * Because of Time Spiral block's shifted cards it is 
 * not sufficient to just look at a card's edition to
 * determine what the card face should be.
 */
public enum CardBorder {
    /* Old border card frames. ALPHA -> 8th ED */
    OLD,
    
    /* Future Sight frames. FUT futureshifted */
    FUT,
    
    /* Planar Chaos frames. PLC planeshifted */
    PLC,
    
    /* Modern card frames. 8th ED -> M15 */
    MOD,

    /* New border cards, M15 -> current */
    M15
}
