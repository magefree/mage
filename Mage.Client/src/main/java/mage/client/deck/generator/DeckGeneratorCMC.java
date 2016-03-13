/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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

    private ArrayList<CMC> poolCMCs60, poolCMCs40;

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

