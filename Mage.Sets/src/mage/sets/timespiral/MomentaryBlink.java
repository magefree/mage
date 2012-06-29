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
package mage.sets.timespiral;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class MomentaryBlink extends CardImpl<MomentaryBlink> {

    public MomentaryBlink(UUID ownerId) {
        super(ownerId, 29, "Momentary Blink", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "TSP";

        this.color.setWhite(true);

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect("Momentary Blink"));
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect());

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{3}{U}"), Constants.TimingRule.INSTANT));
    }

    public MomentaryBlink(final MomentaryBlink card) {
        super(card);
    }

    @Override
    public MomentaryBlink copy() {
        return new MomentaryBlink(this);
    }
}
