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
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.common.continious.SetCardColorTargetEffect;
import mage.cards.SplitCard;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LevelX2
 */

public class IllusionReality extends SplitCard<IllusionReality> {

    public IllusionReality(UUID ownerId) {
        super(ownerId, 129, "Illusion", "Reality", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}", "{2}{G}", false);
        this.expansionSetCode = "APC";

        this.color.setBlue(true);
        this.color.setGreen(true);

        // Illusion
        // Target spell or permanent becomes the color of your choice until end of turn.
        getLeftHalfCard().getColor().setBlue(true);
        getLeftHalfCard().getSpellAbility().addEffect(new SetCardColorTargetEffect(Duration.EndOfTurn));
        Target target = new TargetSpellOrPermanent();
        target.setRequired(true);
        getLeftHalfCard().getSpellAbility().addTarget(target);
        getLeftHalfCard().getSpellAbility().addChoice(new ChoiceColor());

        // Reality
        // Destroy target artifact.
        getRightHalfCard().getColor().setGreen(true);
        getRightHalfCard().getSpellAbility().addTarget(new TargetArtifactPermanent(true));
        getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());

    }

    public IllusionReality(final IllusionReality card) {
        super(card);
    }

    @Override
    public IllusionReality copy() {
        return new IllusionReality(this);
    }
}
