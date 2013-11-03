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
package mage.sets.iceage;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetCreatureOrPlayerAmount;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class FieryJustice extends CardImpl<FieryJustice> {

    public FieryJustice(UUID ownerId) {
        super(ownerId, 366, "Fiery Justice", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}{G}{W}");
        this.expansionSetCode = "ICE";

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);

        // Fiery Justice deals 5 damage divided as you choose among any number of target creatures and/or players. Target opponent gains 5 life.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayerAmount(5));
        Effect effect = new GainLifeTargetEffect(5);
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("Target opponent gains 5 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent(true));
    }

    public FieryJustice(final FieryJustice card) {
        super(card);
    }

    @Override
    public FieryJustice copy() {
        return new FieryJustice(this);
    }
}
