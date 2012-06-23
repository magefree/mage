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
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 * Battlefield checking condition.  This condition can decorate other conditions
 * as well as be used standalone.
 *
 * @see #Controls(mage.filter.Filter)
 * @see #Controls(mage.filter.Filter, mage.abilities.condition.Condition)
 *
 * @author nantuko
 * @author maurer.it_at_gmail.com
 */
public class ControlsPermanentCondition implements Condition {

    public static enum CountType { MORE_THAN, FEWER_THAN, EQUAL_TO };
    private FilterPermanent filter;
    private Condition condition;
    private CountType type;
    private int count;

    /**
     * Applies a filter and delegates creation to
     * {@link #ControlsPermanent(mage.filter.FilterPermanent, mage.abilities.condition.common.ControlsPermanent.CountType, int)}
     * with {@link CountType#MORE_THAN}, and 0.
     * 
     * @param filter
     */
    public ControlsPermanentCondition(FilterPermanent filter) {
        this(filter, CountType.MORE_THAN, 0);
    }

    /**
     * Applies a filter, a {@link CountType}, and count to permanents on the
     * battlefield when checking the condition during the
     * {@link #apply(mage.game.Game, mage.abilities.Ability) apply} method invocation.
     *
     * @param filter
     */
    public ControlsPermanentCondition ( FilterPermanent filter, CountType type, int count ) {
        this.filter = filter;
        this.type = type;
        this.count = count;
    }

    /**
     * Applies a filter, a {@link CountType}, and count to permanents on the
     * battlefield and calls the decorated condition to see if it
     * {@link #apply(mage.game.Game, mage.abilities.Ability) applies}
     * as well.  This will force both conditions to apply for this to be true.
     *
     * @param filter
     * @param conditionToDecorate
     */
    public ControlsPermanentCondition ( FilterPermanent filter, CountType type, int count, Condition conditionToDecorate ) {
        this(filter, type, count);
        this.condition = conditionToDecorate;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;

        switch ( this.type ) {
            case FEWER_THAN:
                conditionApplies = game.getBattlefield().countAll(filter, source.getControllerId(), game) < this.count;
                break;
            case MORE_THAN:
                conditionApplies = game.getBattlefield().countAll(filter, source.getControllerId(), game) > this.count;
                break;
            case EQUAL_TO:
                conditionApplies = game.getBattlefield().countAll(filter, source.getControllerId(), game) == this.count;
                break;
        }

        //If a decorated condition exists, check it as well and apply them together.
        if ( this.condition != null ) {
            conditionApplies = conditionApplies && this.condition.apply(game, source);
        }

        return conditionApplies;
    }
}
