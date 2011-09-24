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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author nantuko
 */
public class UrgentExorcism extends CardImpl<UrgentExorcism> {

    public UrgentExorcism(UUID ownerId) {
        super(ownerId, 40, "Urgent Exorcism", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "ISD";

        this.color.setWhite(true);

        // Destroy target Spirit or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterSpiritOrEnchantment()));
    }

    public UrgentExorcism(final UrgentExorcism card) {
        super(card);
    }

    @Override
    public UrgentExorcism copy() {
        return new UrgentExorcism(this);
    }
}

class FilterSpiritOrEnchantment extends FilterPermanent<FilterSpiritOrEnchantment> {

    public FilterSpiritOrEnchantment() {
        super("Spirit or enchantment");
    }

    public FilterSpiritOrEnchantment(FilterSpiritOrEnchantment filter) {
        super(filter);
    }

    @Override
	public boolean match(Permanent permanent) {
        if (!super.match(permanent))
            return notFilter;

        if (!permanent.getCardType().contains(CardType.ENCHANTMENT) && !permanent.hasSubtype("Spirit"))
            return notFilter;

        return !notFilter;
    }

    @Override
	public FilterSpiritOrEnchantment copy() {
		return new FilterSpiritOrEnchantment(this);
	}
}
