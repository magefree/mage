package mage.constants;

// Below are all the phrasings that different Effects employing the DynamicValue interface use in oracle text.
// The only impact these have on getMessage() is the plurality of the returned value.
// However, Effects will often need to cater to multiple phrasings to produce oracle text accurate to WOTC's whims.
public enum ValuePhrasing {
    // Plural
    // <do effects> equal to [the number of] <values>
    // i.e. Draw cards equal to the number of cards in your hand.
    // OR, with a modifier:
    // Haunting Apparition’s power is equal to 1 plus the number of green creature cards in the chosen player’s graveyard.
    EQUAL_TO,

    // Plural
    // <do effect with X>, where X is [the number of] <values>
    // i.e. draw X cards, where X is the number of spite counters on Curse of Vengeance.
    // OR, with a modifier:
    // Look at the top X cards of your library, where X is twice the number of lands you control.
    X_IS,

    // Plural
    // Same as X_IS, except the value explanation is skipped (cases where a subsequent effect or other context explains what X is)
    // <do effect with X>
    // i.e. you gain X life
    // getMessage() behavior for this should ALWAYS be to return an empty string
    X_HIDDEN,

    // Singular
    // <do effect> for each <value>
    // i.e. Equipped creature gets +1/+0 for each artifact you control.
    // This phrasing can be used with multipliers, including being negative, but addition and subtraction don't work here.
    // example with multiplier of 3:
    // You gain 3 life for each creature attacking you.
    FOR_EACH,

    // TODO: remove once all DynamicValue implementations have been converted
    // Legacy phrasing
    // This indicates that an effect should use its original phrasing implementation for this DynamicValue.
    // DO NOT check for this in getMessage(). it's a "dont care".
    LEGACY
}
