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
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public class FinalPunishment extends CardImpl {

    public FinalPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Target player loses life equal to the damage already dealt to him or her this turn.
        Effect effect = new LoseLifeTargetEffect(new FinalPunishmentAmount());
        effect.setText("target player loses life equal to the damage already dealt to him or her this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
    }

    public FinalPunishment(final FinalPunishment card) {
        super(card);
    }

    @Override
    public FinalPunishment copy() {
        return new FinalPunishment(this);
    }
}

class FinalPunishmentAmount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        AmountOfDamageAPlayerReceivedThisTurnWatcher watcher
            = (AmountOfDamageAPlayerReceivedThisTurnWatcher) game.getState().getWatchers().get(AmountOfDamageAPlayerReceivedThisTurnWatcher.class.getSimpleName());
        if(watcher != null) {
            return watcher.getAmountOfDamageReceivedThisTurn(source.getFirstTarget());
        }
        return 0;
    }

    @Override
    public FinalPunishmentAmount copy() {
        return new FinalPunishmentAmount();
    }

    @Override
    public String getMessage() {
        return "the damage already dealt to him or her this turn";
    }
}
