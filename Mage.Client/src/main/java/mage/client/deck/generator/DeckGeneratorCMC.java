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
