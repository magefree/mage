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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public class BetrayalOfFlesh extends CardImpl {

    public BetrayalOfFlesh(UUID ownerId) {
        super(ownerId, 58, "Betrayal of Flesh", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{5}{B}");
        this.expansionSetCode = "MRD";

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Destroy target creature;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or return target creature card from your graveyard to the battlefield.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.getSpellAbility().getModes().addMode(mode);
        // Entwine-Sacrifice three lands.
        this.addAbility(new EntwineAbility(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, new FilterControlledLandPermanent("three lands"), true))));
    }

    public BetrayalOfFlesh(final BetrayalOfFlesh card) {
        super(card);
    }

    @java.lang.Override
    public BetrayalOfFlesh copy() {
        return new BetrayalOfFlesh(this);
    }
}
