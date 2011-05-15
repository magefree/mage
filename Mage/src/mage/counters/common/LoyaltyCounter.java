/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.counters.common;

import mage.counters.Counter;

/**
 * Loyalty counter.
 * 
 * 208. Loyalty
 *
 *   208.1. Each planeswalker card has a loyalty number printed in its lower right corner. This indicates its loyalty while it's not on the battlefield, and it also indicates that the planeswalker enters the battlefield with that many loyalty counters on it.
 *
 *   208.2. An activated ability with a loyalty symbol in its cost is a loyalty ability. Loyalty abilities follow special rules: A player may activate a loyalty ability of a permanent he or she controls any time he or she has priority and the stack is empty during a main phase of his or her turn, but only if none of that permanent's loyalty abilities have been activated that turn. See rule 606, "Loyalty Abilities."
 *
 * @author nantuko
 */
public class LoyaltyCounter extends Counter<LoyaltyCounter> {

    public LoyaltyCounter() {
        super("Loyalty");
        this.count = 1;
    }

    public LoyaltyCounter(int amount) {
        super("Loyalty");
        this.count = amount;
    }
}
