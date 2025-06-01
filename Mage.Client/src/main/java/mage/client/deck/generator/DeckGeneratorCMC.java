package mage.client.deck.generator;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Mana value distribution between cards in diff deck sizes
 */
public enum DeckGeneratorCMC {

    Low(
            // 100
            ImmutableList.<CMC>builder()
                    .add(new CMC(0, 2, 0.55f))
                    .add(new CMC(3, 4, 0.30f))
                    .add(new CMC(5, 6, 0.15f)).build(),
            // 60
            ImmutableList.<CMC>builder()
                    .add(new CMC(0, 2, 0.60f))
                    .add(new CMC(3, 4, 0.30f))
                    .add(new CMC(5, 6, 0.10f)).build(),
            // 40
            ImmutableList.<CMC>builder()
                    .add(new CMC(0, 2, 0.65f))
                    .add(new CMC(3, 4, 0.30f))
                    .add(new CMC(5, 5, 0.05f)).build()),
    Default(
            // 100
            ImmutableList.<CMC>builder()
                    .add(new CMC(0, 2, 0.15f))
                    .add(new CMC(3, 5, 0.50f))
                    .add(new CMC(6, 7, 0.30f))
                    .add(new CMC(8, 100, 0.05f)).build(),
            // 60
            ImmutableList.<CMC>builder()
                    .add(new CMC(0, 2, 0.20f))
                    .add(new CMC(3, 5, 0.50f))
                    .add(new CMC(6, 7, 0.25f))
                    .add(new CMC(8, 100, 0.05f)).build(),
            // 40
            ImmutableList.<CMC>builder()
                    .add(new CMC(0, 2, 0.30f))
                    .add(new CMC(3, 4, 0.45f))
                    .add(new CMC(5, 6, 0.20f))
                    .add(new CMC(7, 100, 0.05f)).build()),

    High(
            // 100
            ImmutableList.<CMC>builder().
                    add(new CMC(0, 2, 0.05f))
                    .add(new CMC(3, 5, 0.40f))
                    .add(new CMC(6, 7, 0.40f))
                    .add(new CMC(8, 100, 0.15f)).build(),
            // 60
            ImmutableList.<CMC>builder().
                    add(new CMC(0, 2, 0.05f))
                    .add(new CMC(3, 5, 0.35f))
                    .add(new CMC(6, 7, 0.40f))
                    .add(new CMC(8, 100, 0.15f)).build(),
            // 40
            ImmutableList.<CMC>builder().
                    add(new CMC(0, 2, 0.10f))
                    .add(new CMC(3, 4, 0.30f))
                    .add(new CMC(5, 6, 0.45f))
                    .add(new CMC(7, 100, 0.15f)).build());

    private final List<CMC> poolCMCs100;
    private final List<CMC> poolCMCs60;
    private final List<CMC> poolCMCs40;

    DeckGeneratorCMC(List<CMC> CMCs100, List<CMC> CMCs60, List<CMC> CMCs40) {
        this.poolCMCs100 = CMCs100;
        this.poolCMCs60 = CMCs60;
        this.poolCMCs40 = CMCs40;
    }

    public List<CMC> get40CardPoolCMC() {
        return this.poolCMCs40;
    }

    public List<CMC> get60CardPoolCMC() {
        return this.poolCMCs60;
    }

    public List<CMC> get100CardPoolCMC() {
        return this.poolCMCs100;
    }

    static class CMC {
        public final int min;
        public final int max;
        public final float percentage;
        private int amount = 0;

        /**
         * Constructs a CMC range given a minimum and maximum, and the percentage of cards that are in this range.
         *
         * @param min        the minimum CMC a card in this range can be.
         * @param max        the maximum CMC a card in this range can be.
         * @param percentage the percentage of cards in the range (min, max)
         */
        CMC(int min, int max, float percentage) {
            this.min = min;
            this.max = max;
            this.percentage = percentage;
        }

        /**
         * Sets the amount of cards needed in this CMC range.
         *
         * @param amount the number of cards needed.
         */
        public void setAmount(int amount) {
            this.amount = amount;
        }

        /**
         * Gets the number of cards needed in this CMC range.
         *
         * @return the number of cards needed in this CMC range.
         */
        public int getAmount() {
            return this.amount;
        }
    }

}

