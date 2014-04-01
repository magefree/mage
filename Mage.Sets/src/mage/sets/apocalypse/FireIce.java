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

package mage.sets.apocalypse;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.SplitCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlayerAmount;

/**
 *
 * @author LevelX2
 */


public class FireIce extends SplitCard<FireIce> {

    public FireIce(UUID ownerId) {
        super(ownerId, 128, "Fire", "Ice", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}", "{1}{U}", false);
        this.expansionSetCode = "APC";

        this.color.setBlue(true);
        this.color.setRed(true);

        // Fire
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        getLeftHalfCard().getColor().setRed(true);
        Effect effect = new DamageMultiEffect(2);
        effect.setText("Fire deals 2 damage divided as you choose among one or two target creatures and/or players");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlayerAmount(2));

        // Ice
        // Tap target permanent.
        // Draw a card.
        getRightHalfCard().getColor().setBlue(true);
        getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(true));
        getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

    }

    public FireIce(final FireIce card) {
        super(card);
    }

    @Override
    public FireIce copy() {
        return new FireIce(this);
    }
}
