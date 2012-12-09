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

import mage.abilities.costs.mana.ManaCostsImpl;

/**
 *
 * @author LevelX2
 */


public class OptionalAdditionalCostImpl <T extends OptionalAdditionalCostImpl<T>> extends CostsImpl<Cost> implements OptionalAdditionalCost{


    protected String name;
    protected String reminderText;
    protected boolean activated;
    protected int activatedCounter;
    protected boolean repeatable;

    public OptionalAdditionalCostImpl(String name, String reminderText, Cost cost) {
        this.activated = false;
        this.name = name;
        this.reminderText = reminderText;
        this.activatedCounter = 0;
        this.add((Cost) cost);
    }

    public OptionalAdditionalCostImpl(final OptionalAdditionalCostImpl cost) {
        super(cost);
        this.name = cost.name;
        this.reminderText = cost.reminderText;
        this.activated = cost.activated;
        this.activatedCounter = cost.activatedCounter;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the complete text for the addional coast or if onlyCost is true
     * only the pure text fore the included native cost
     *
     * @param onlyCost
     * @return
     */
    @Override
    public String getText(boolean onlyCost) {
        if (onlyCost) {
            return getText();
        } else {
            return name + " " + getText();
        }
    }

    /**
     * Returns a reminder text, if the cost has one
     *
     * @return
     */
    @Override
    public String getReminderText() {
        String replace = "";
        if (reminderText != null && !reminderText.isEmpty()) {
            replace = reminderText.replace("{cost}", this.getText(true));
        }
        return replace;
    }

    /**
     * Returns a text suffix for the game log, that can be added to
     * the cast message.
     *
     * @param position - if there are multiple costs, it's the postion the cost is set (starting with 0)
     * @return
     */
    @Override
    public String getCastSuffixMessage(int position) {
        return " with " + name;
    }


    /**
     * If the player intends to pay the cost, the cost will be activated
     *
     * @param activated
     */
    @Override
    public void activate() {
        activated = true;
        ++activatedCounter;
    };

    /**
     * Reset the activate and count information
     *
     */
    @Override
    public void reset() {
        activated = false;
        activatedCounter = 0;
    }

    /**
     * Can the cost be multiple times activated
     *
     * @return
     */
    @Override
    public boolean isRepeatable() {
        return repeatable;
    };

    /**
     * Returns if the cost was activated
     *
     * @return
     */
    @Override
    public boolean isActivated(){
        return activated;
    };

    /**
     * Returns the number of times the cost was activated
     * @return
     */
    @Override
    public int getActivateCount(){
        return activatedCounter;
    };

    
    @Override
    public OptionalAdditionalCostImpl copy() {
        return new OptionalAdditionalCostImpl(this);
    }
}