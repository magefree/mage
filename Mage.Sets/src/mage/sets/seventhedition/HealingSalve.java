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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.cards.CardImpl;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class HealingSalve extends CardImpl<HealingSalve> {

    public HealingSalve(UUID ownerId) {
        super(ownerId, 18, "Healing Salve", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "7ED";

        this.color.setWhite(true);

        // Choose one - Target player gains 3 life; or prevent the next 3 damage that would be dealt to target creature or player this turn.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        Mode mode = new Mode();
        mode.getEffects().add(new PreventDamageTargetEffect(Constants.Duration.EndOfTurn, 3));
        mode.getTargets().add(new TargetCreatureOrPlayer());
        
        this.getSpellAbility().addMode(mode);
    }

    public HealingSalve(final HealingSalve card) {
        super(card);
    }

    @Override
    public HealingSalve copy() {
        return new HealingSalve(this);
    }
}
