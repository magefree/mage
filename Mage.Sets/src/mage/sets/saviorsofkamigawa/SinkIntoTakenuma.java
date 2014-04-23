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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SweepNumber;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.common.SweepEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class SinkIntoTakenuma extends CardImpl<SinkIntoTakenuma> {

    public SinkIntoTakenuma(UUID ownerId) {
        super(ownerId, 89, "Sink into Takenuma", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Arcane");

        this.color.setBlack(true);

        // Sweep - Return any number of Swamps you control to their owner's hand. Target player discards a card for each Swamp returned this way.
        this.getSpellAbility().addEffect(new SweepEffect("Swamp"));
        DynamicValue sweepValue = new SweepNumber("Swamp", false);
        this.getSpellAbility().addEffect(new DiscardTargetEffect(sweepValue));
        this.getSpellAbility().addTarget(new TargetPlayer(true));
    }

    public SinkIntoTakenuma(final SinkIntoTakenuma card) {
        super(card);
    }

    @Override
    public SinkIntoTakenuma copy() {
        return new SinkIntoTakenuma(this);
    }
}
