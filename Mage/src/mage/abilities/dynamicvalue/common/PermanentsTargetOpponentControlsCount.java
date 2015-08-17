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
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public class PermanentsTargetOpponentControlsCount implements DynamicValue {

    private FilterPermanent filter;
    private Integer multiplier;

    public PermanentsTargetOpponentControlsCount() {
        this(new FilterPermanent(), 1);
    }

    public PermanentsTargetOpponentControlsCount(FilterPermanent filter) {
        this(filter, 1);
    }

    public PermanentsTargetOpponentControlsCount(FilterPermanent filter, Integer multiplier) {
        this.filter = filter.copy();
        this.multiplier = multiplier;
    }

    public PermanentsTargetOpponentControlsCount(final PermanentsTargetOpponentControlsCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public PermanentsTargetOpponentControlsCount copy() {
        return new PermanentsTargetOpponentControlsCount(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getFirstTarget() != null) {
            filter.add(new ControllerIdPredicate(sourceAbility.getFirstTarget()));
            int value = game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
            return multiplier * value;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if (multiplier != null) {
            return multiplier.toString();
        }
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " target opponent controls";
    }
}
