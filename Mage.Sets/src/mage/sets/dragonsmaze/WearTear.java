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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FuseAbility;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.filter.common.FilterEnchantment;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public class WearTear extends SplitCard<WearTear> {

    public WearTear(UUID ownerId) {
        super(ownerId, 135, "Wear - Tear", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");
        this.expansionSetCode = "DGM";

        this.color.setRed(true);
        this.color.setWhite(true);

        // Wear
        Card leftHalfCard = this.createLeftHalfCard("Wear", "{1}{R}");
        // Destroy target artifact.
        leftHalfCard.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        target.setRequired(true);
        leftHalfCard.getSpellAbility().addTarget(target);

        // Tear
        Card rightHalfCard = this.createRightHalfCard("Tear", "{W}");
        // Destroy target enchantment.
        rightHalfCard.getSpellAbility().addEffect(new DestroyTargetEffect());
        target = new TargetPermanent(new FilterEnchantment());
        target.setRequired(true);
        rightHalfCard.getSpellAbility().addTarget(target);


        // Fuse (You may cast one or both halves of this card from your hand.)
        this.addAbility(new FuseAbility(this, this.getManaCost()));
    }

    public WearTear(final WearTear card) {
        super(card);
    }

    @Override
    public WearTear copy() {
        return new WearTear(this);
    }
}
