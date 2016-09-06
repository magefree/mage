/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.StaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChancellorAbility extends StaticAbility {

    public ChancellorAbility(DelayedTriggeredAbility ability, String text) {
        super(Zone.HAND, new ChancellorEffect(ability, text));
    }

    public ChancellorAbility(OneShotEffect effect) {
        super(Zone.HAND, effect);
    }

    public ChancellorAbility(final ChancellorAbility ability) {
        super(ability);
    }

    @Override
    public ChancellorAbility copy() {
        return new ChancellorAbility(this);
    }

    @Override
    public String getRule() {
        return "You may reveal this card from your opening hand. If you do, " + super.getRule();
    }

}

class ChancellorEffect extends OneShotEffect {

    private final DelayedTriggeredAbility ability;

    ChancellorEffect(DelayedTriggeredAbility ability, String text) {
        super(Outcome.Benefit);
        this.ability = ability;
        staticText = text;
    }

    ChancellorEffect(ChancellorEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ability.setSourceId(source.getSourceId());
        ability.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(ability);
        return true;
    }

    @Override
    public ChancellorEffect copy() {
        return new ChancellorEffect(this);
    }

}
