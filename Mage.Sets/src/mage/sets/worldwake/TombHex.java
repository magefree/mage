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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LandfallWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class TombHex extends CardImpl<TombHex> {

    public TombHex(UUID ownerId) {
        super(ownerId, 69, "Tomb Hex", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "WWK";

        this.color.setBlack(true);

        // Target creature gets -2/-2 until end of turn.
        // Landfall - If you had a land enter the battlefield under your control this turn, that creature gets -4/-4 until end of turn instead.
        this.addWatcher(new LandfallWatcher());
        this.getSpellAbility().addEffect(new ConditionalContinousEffect(new BoostTargetEffect(-4, -4, Constants.Duration.EndOfTurn), new BoostTargetEffect(-2, -2, Constants.Duration.EndOfTurn), LandfallCondition.getInstance(), "Target creature gets -2/-2 until end of turn. Landfall - If you had a land enter the battlefield under your control this turn, that creature gets -4/-4 until end of turn instead", true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TombHex(final TombHex card) {
        super(card);
    }

    @Override
    public TombHex copy() {
        return new TombHex(this);
    }
}
