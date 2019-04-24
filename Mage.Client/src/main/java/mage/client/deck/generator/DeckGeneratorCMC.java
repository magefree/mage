
package mage.client.deck.generator;

import java.util.ArrayList;

public enum DeckGeneratorCMC {

    Low(
    new ArrayList<CMC>() {{
        add(new CMC(0, 2, 0.60f));
        add(new CMC(3, 4, 0.30f));
        add(new CMC(5, 6, 0.10f));
    }},
    new ArrayList<CMC>() {{
        add(new CMC(0, 2, 0.65f));
        add(new CMC(3, 4, 0.30f));
        add(new CMC(5, 5, 0.05f));
    }}),
    Default(
    new ArrayList<CMC>() {{
        add(new CMC(0, 2, 0.20f));
        add(new CMC(3, 5, 0.50f));
        add(new CMC(6, 7, 0.25f));
        add(new CMC(8, 100, 0.05f));
    }},
    new ArrayList<CMC>() {{
        add(new CMC(0, 2, 0.30f));
        add(new CMC(3, 4, 0.45f));
        add(new CMC(5, 6, 0.20f));
        add(new CMC(7, 100, 0.05f));
    }}),
    High(
    new ArrayList<CMC>() {{
        add(new CMC(0, 2, 0.05f));
        add(new CMC(3, 5, 0.35f));
        add(new CMC(6, 7, 0.40f));
        add(new CMC(8, 100, 0.15f));
    }},
    new ArrayList<CMC>() {{
        add(new CMC(0, 2, 0.10f));
        add(new CMC(3, 4, 0.30f));
        add(new CMC(5, 6, 0.45f));
        add(new CMC(7, 100, 0.15f));
    }});

    private final ArrayList<CMC> poolCMCs60;
    private final ArrayList<CMC> poolCMCs40;

    DeckGeneratorCMC(ArrayList<CMC> CMCs60, ArrayList<CMC> CMCs40) {
        this.poolCMCs60 = CMCs60;
        this.poolCMCs40 = CMCs40;
    }

    public ArrayList<CMC> get40CardPoolCMC() {
        return this.poolCMCs40;
    }

    public ArrayList<CMC> get60CardPoolCMC() {
        return this.poolCMCs60;
    }

    static class CMC
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
        CMC(int min, int max, float percentage)
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

}

