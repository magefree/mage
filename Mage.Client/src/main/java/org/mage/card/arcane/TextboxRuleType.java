package org.mage.card.arcane;

/**
 * @author stravant@gmail.com
 */
public enum TextboxRuleType {
    /* Normal abilities, just rendered as lines of text with embedded symbols
     * replaced to the relevant images. */
    NORMAL,
    
    /* Keyword ability. To be displayed in the comma separated list at the 
     * very top of the rules box */
    SIMPLE_KEYWORD,
    
    /* Loyalty abilities on planeswalkers */
    LOYALTY,

    /* Basic mana ability */
    BASIC_MANA,
    
    /* Levelup creature - static ability at a given level */
    LEVEL
}
