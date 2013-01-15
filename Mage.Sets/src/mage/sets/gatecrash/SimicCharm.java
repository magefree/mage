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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class SimicCharm extends CardImpl<SimicCharm> {

    public SimicCharm (UUID ownerId) {
        super(ownerId, 195, "Simic Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);
        this.color.setGreen(true);

        //Choose one - Target creature gets +3/+3 until end of turn
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Constants.Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        //permanents you control gain hexproof until end of turn
        Mode mode = new Mode();
        mode.getEffects().add(new GainAbilityAllEffect(HexproofAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterControlledPermanent()));
        this.getSpellAbility().addMode(mode);
        //return target creature to its owner's hand.
        Mode mode2 = new Mode();
        mode2.getEffects().add(new ReturnToHandTargetEffect());
        mode2.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
        
    }

    public SimicCharm(final SimicCharm card) {
        super(card);
    }

    @Override
    public SimicCharm  copy() {
        return new SimicCharm(this);
    }
}
