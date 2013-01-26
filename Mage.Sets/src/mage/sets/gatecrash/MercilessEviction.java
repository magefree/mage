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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;

/**
 *
 * @author LevelX2
 */
public class MercilessEviction extends CardImpl<MercilessEviction> {

    public MercilessEviction(UUID ownerId) {
        super(ownerId, 177, "Merciless Eviction", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlack(true);
        this.color.setWhite(true);

        // Choose one - Exile all artifacts
        this.getSpellAbility().addEffect(new ExileAllEffect(new FilterArtifactPermanent("artifacts")));
        // or exile all creatures
        Mode mode = new Mode();
        mode.getEffects().add(new ExileAllEffect(new FilterCreaturePermanent("creatures")));
        this.getSpellAbility().addMode(mode);
        // or exile all enchantments
        Mode mode2 = new Mode();
        mode2.getEffects().add(new ExileAllEffect(new FilterEnchantmentPermanent("enchantments")));
        this.getSpellAbility().addMode(mode2);
        // or exile all planeswalkers.
        Mode mode3 = new Mode();
        mode3.getEffects().add(new ExileAllEffect(new FilterPlaneswalkerPermanent("planeswalkers")));
        this.getSpellAbility().addMode(mode3);
    }

    public MercilessEviction(final MercilessEviction card) {
        super(card);
    }

    @Override
    public MercilessEviction copy() {
        return new MercilessEviction(this);
    }
}
