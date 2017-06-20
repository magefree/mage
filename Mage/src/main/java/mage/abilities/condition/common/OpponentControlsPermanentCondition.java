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

import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 * Checks if one opponent (each opponent is checked on its own) fulfills
 * the defined condition of controlling the defined number of permanents.
 *
 * @author LevelX2
 */

public class OpponentControlsPermanentCondition implements Condition {


    private FilterPermanent filter;
    private ComparisonType type;
    private int count;

    /**
     * @param filter
     */
    public OpponentControlsPermanentCondition(FilterPermanent filter) {
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
    public OpponentControlsPermanentCondition(FilterPermanent filter, ComparisonType type, int count) {
        this.filter = filter;
        this.type = type;
        this.count = count;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            FilterPermanent localFilter = filter.copy();
            localFilter.add(new ControllerIdPredicate(opponentId));
            if (ComparisonType.compare(game.getBattlefield().count(localFilter, source.getSourceId(), source.getControllerId(), game), type, this.count)) {
                conditionApplies = true;
                break;
            }


        }
        return conditionApplies;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }
}
