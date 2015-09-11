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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class IvoryCharm extends CardImpl {

    public IvoryCharm(UUID ownerId) {
        super(ownerId, 227, "Ivory Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "MIR";

        // Choose one - All creatures get -2/-0 until end of turn
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, 0, Duration.EndOfTurn));
        // or tap target creature
        Mode mode = new Mode();
        mode.getEffects().add(new TapTargetEffect());
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or prevent the next 1 damage that would be dealt to target creature or player this turn.
        mode = new Mode();
        mode.getEffects().add(new PreventDamageToTargetEffect(Duration.EndOfTurn, 1));
        mode.getTargets().add(new TargetCreatureOrPlayer());
        this.getSpellAbility().addMode(mode);
    }

    public IvoryCharm(final IvoryCharm card) {
        super(card);
    }

    @Override
    public IvoryCharm copy() {
        return new IvoryCharm(this);
    }
}
