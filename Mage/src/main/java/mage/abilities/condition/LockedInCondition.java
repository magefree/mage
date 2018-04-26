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
package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * The use of this class must be handled carefully because conditions don't
 * have a copy method, the condition state is kept when the effect or ability
 * is copied that uses the condition.
 * So if you use this class, you have to do it like in ConditionalContinuousEffect,
 * where always a new FixedCondition(condition.apply(...)) is used if a
 * LockedInCondition is given.
 *
 * Needs probably some redesign, don't like it the way it's done now.
 *
 * @author LevelX2
 */
public class LockedInCondition implements Condition {

    private boolean conditionChecked = false;
    private boolean result;
    private final Condition condition;

    public LockedInCondition ( Condition condition ) {
        this.condition = condition;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public boolean apply(Game game, Ability source) {
        if(!conditionChecked) {
            result = condition.apply(game, source);
            conditionChecked = true;
        }
        return result;
    }

    public Condition getBaseCondition() {
        return condition;
    }

}
