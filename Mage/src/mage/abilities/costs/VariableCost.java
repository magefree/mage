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

package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface VariableCost {
    /**
     * Returns the variable amount if alreaady set
     * 
     * @return
     */
    int getAmount();
    /**
     * Sets the variable amount
     *
     * @param amount
     */
    void setAmount(int amount);

    /**
     * returns the action text (e.g. "creature cards to exile from your hand", "life to pay")
     *
     * @return
     */
    String getActionText();
    /**
     * Return a min value to announce
     *
     * @param source
     * @param game
     * @return
     */
    int getMinValue(Ability source, Game game);
    /**
     * Returns a max value to announce
     *
     * @param source
     * @param game
     * @return
     */
    int getMaxValue(Ability source, Game game);
    /**
     * Asks the controller to announce the variable value
     * @param source
     * @param game
     * @return
     */
    int announceXValue(Ability source, Game game);
    /**
     * Returns a fixed cost with the announced variabke value
     *
     * @param xValue
     * @return
     */
    Cost getFixedCostsFromAnnouncedValue(int xValue);
}
