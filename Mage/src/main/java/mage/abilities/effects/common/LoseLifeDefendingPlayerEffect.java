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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author noxx
 */
public class LoseLifeDefendingPlayerEffect extends OneShotEffect {

    private DynamicValue amount;
    private boolean attackerIsSource;

    /**
     *
     * @param amount
     * @param attackerIsSource true if the source.getSourceId() contains the
     * attacker false if attacker has to be taken from targetPointer
     */
    public LoseLifeDefendingPlayerEffect(int amount, boolean attackerIsSource) {
        this(new StaticValue(amount), attackerIsSource);
    }

    public LoseLifeDefendingPlayerEffect(DynamicValue amount, boolean attackerIsSource) {
        super(Outcome.Damage);
        this.amount = amount;
        this.attackerIsSource = attackerIsSource;
    }

    public LoseLifeDefendingPlayerEffect(final LoseLifeDefendingPlayerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.attackerIsSource = effect.attackerIsSource;
    }

    @Override
    public LoseLifeDefendingPlayerEffect copy() {
        return new LoseLifeDefendingPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defender;
        if (attackerIsSource) {
            defender = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        } else {
            defender = game.getPlayer(getTargetPointer().getFirst(game, source));
        }
        if (defender != null) {
            defender.loseLife(amount.calculate(game, source, this), game, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "defending player loses " + amount + " life";
    }

}
