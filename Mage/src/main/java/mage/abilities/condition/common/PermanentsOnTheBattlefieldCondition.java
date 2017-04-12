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
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 * Battlefield checking condition.  This condition can decorate other conditions
 * as well as be used standalone.
 *
 * @author nantuko
 * @author maurer.it_at_gmail.com
 * @see #Controls(mage.filter.Filter)
 * @see #Controls(mage.filter.Filter, mage.abilities.condition.Condition)
 */
public class PermanentsOnTheBattlefieldCondition implements Condition {

    private FilterPermanent filter;
    private Condition condition;
    private ComparisonType type;
    private int count;
    private boolean onlyControlled;

    /**
     * Applies a filter and delegates creation to
     * {@link #ControlsPermanent(mage.filter.FilterPermanent, mage.abilities.condition.common.ControlsPermanent.CountType, int)}
     * with {@link ComparisonType#MORE_THAN}, and 0.
     *
     * @param filter
     */
    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter) {
        this(filter, ComparisonType.MORE_THAN, 0);
    }

    /**
     * Applies a filter, a {@link ComparisonType}, and count to permanents on the
     * battlefield when checking the condition during the
     * {@link #apply(mage.game.Game, mage.abilities.Ability) apply} method invocation.
     *
     * @param filter
     * @param type
     * @param count
     */
    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, ComparisonType type, int count) {
        this(filter, type, count, true);
    }

    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, ComparisonType type, int count, boolean onlyControlled) {
        this.filter = filter;
        this.type = type;
        this.count = count;
        this.onlyControlled = onlyControlled;
    }

    /**
     * Applies a filter, a {@link ComparisonType}, and count to permanents on the
     * battlefield and calls the decorated condition to see if it
     * {@link #apply(mage.game.Game, mage.abilities.Ability) applies}
     * as well.  This will force both conditions to apply for this to be true.
     *
     * @param filter
     * @param type
     * @param count
     * @param conditionToDecorate
     */
    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, ComparisonType type, int count, Condition conditionToDecorate) {
        this(filter, type, count);
        this.condition = conditionToDecorate;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;

        FilterPermanent localFilter = filter.copy();
        if (onlyControlled) {
            localFilter.add(new ControllerIdPredicate(source.getControllerId()));
        }

        int permanentsOnBattlefield = game.getBattlefield().count(localFilter, source.getSourceId(), source.getControllerId(), game);
        conditionApplies = ComparisonType.compare(permanentsOnBattlefield, type, count);

        //If a decorated condition exists, check it as well and apply them together.
        if (this.condition != null) {
            conditionApplies = conditionApplies && this.condition.apply(game, source);
        }

        return conditionApplies;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }
}
