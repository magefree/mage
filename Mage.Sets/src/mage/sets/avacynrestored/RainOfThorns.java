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
package mage.sets.avacynrestored;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterEnchantment;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author noxx

 */
public class RainOfThorns extends CardImpl<RainOfThorns> {

    public RainOfThorns(UUID ownerId) {
        super(ownerId, 190, "Rain of Thorns", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Choose one or more - Destroy target artifact; destroy target enchantment; and/or destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        Mode mode1 = new Mode();
        mode1.getEffects().add(new DestroyTargetEffect());
        mode1.getTargets().add(new TargetPermanent(new FilterEnchantment()));
        this.getSpellAbility().addMode(mode1);

        Mode mode2 = new Mode();
        mode2.getEffects().add(new DestroyTargetEffect());
        mode2.getTargets().add(new TargetPermanent(new FilterLandPermanent()));
        this.getSpellAbility().addMode(mode2);

        Mode mode3 = new Mode();
        mode3.getEffects().add(new DestroyTargetEffect("Destroy target artifact, then destroy target enchantment"));
        mode3.getTargets().add(new TargetArtifactPermanent());
        mode3.getTargets().add(new TargetPermanent(new FilterEnchantment()));
        this.getSpellAbility().addMode(mode3);

        Mode mode4 = new Mode();
        mode4.getEffects().add(new DestroyTargetEffect("Destroy target artifact, then destroy target land"));
        mode4.getTargets().add(new TargetArtifactPermanent());
        mode4.getTargets().add(new TargetPermanent(new FilterLandPermanent()));
        this.getSpellAbility().addMode(mode4);

        Mode mode5 = new Mode();
        mode5.getEffects().add(new DestroyTargetEffect("Destroy target enchantment, then destroy target land"));
        mode5.getTargets().add(new TargetPermanent(new FilterEnchantment()));
        mode5.getTargets().add(new TargetPermanent(new FilterLandPermanent()));
        this.getSpellAbility().addMode(mode5);
    }

    public RainOfThorns(final RainOfThorns card) {
        super(card);
    }

    @Override
    public RainOfThorns copy() {
        return new RainOfThorns(this);
    }
}
