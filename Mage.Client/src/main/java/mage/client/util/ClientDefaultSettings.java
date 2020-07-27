package mage.client.util;

import mage.cards.CardDimensions;

/**
 * @author JayDi85
 */
public final class ClientDefaultSettings {

    public static final String serverName;
    public static final int port;
    public static final double cardScalingFactor;
    public static final double cardScalingFactorEnlarged;
    public static final double handScalingFactor;
    public static final CardDimensions dimensions;
    public static final CardDimensions dimensionsEnlarged;

    public static final String deckPath;
    public static final String otherPlayerIndex;
    public static final String computerName;

    static {
        // default values
        serverName = "localhost";
        port = 17171;
        cardScalingFactor = 0.4;
        cardScalingFactorEnlarged = 0.5;
        handScalingFactor = 1.3;
        deckPath = "";
        otherPlayerIndex = "1";  // combobox default, example: 0: Human, 1: Computer - mad, 2: Computer - Draft Bot
        computerName = "Computer";
        dimensions = new CardDimensions(cardScalingFactor);
        dimensionsEnlarged = new CardDimensions(cardScalingFactorEnlarged);
    }
}
