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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.SplitCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */


public class DownDirty extends SplitCard<DownDirty> {

    public DownDirty(UUID ownerId) {
        super(ownerId, 126, "Down", "Dirty", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{B}", "{2}{G}", true);
        this.expansionSetCode = "DGM";

        this.color.setBlack(true);
        this.color.setGreen(true);

        // Down
        // Target player discards two cards.
        getLeftHalfCard().getColor().setBlack(true);
        getLeftHalfCard().getSpellAbility().addEffect(new DiscardTargetEffect(2));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer(true));

        // Dirty
        // Return target card from your graveyard to your hand.
        getRightHalfCard().getColor().setGreen(true);
        getRightHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard());

    }

    public DownDirty(final DownDirty card) {
        super(card);
    }

    @Override
    public DownDirty copy() {
        return new DownDirty(this);
    }
}
