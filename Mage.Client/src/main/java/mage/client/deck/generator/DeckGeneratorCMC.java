package mage.client.deck.generator;

/**
 * Stores a range of converted mana costs (CMC) for use in deck generation.
 */
public class DeckGeneratorCMC
{
    public final int min;
    public final int max;
    public final float percentage;
    private int amount = 0;

    /**
     * Constructs a CMC range given a minimum and maximum, and the percentage of cards that are in this range.
     * @param min the minimum CMC a card in this range can be.
     * @param max the maximum CMC a card in this range can be.
     * @param percentage the percentage of cards in the range (min, max)
     */
    DeckGeneratorCMC(int min, int max, float percentage)
    {
        this.min = min;
        this.max = max;
        this.percentage = percentage;
    }

    /**
     * Sets the amount of cards needed in this CMC range.
     * @param amount the number of cards needed.
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    /**
     * Gets the number of cards needed in this CMC range.
     * @return the number of cards needed in this CMC range.
     */
    public int getAmount()
    {
        return this.amount;
    }
}
