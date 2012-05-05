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
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class BanishingStroke extends CardImpl<BanishingStroke> {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.getCardType().add(CardType.CREATURE);
        filter.getCardType().add(CardType.ENCHANTMENT);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public BanishingStroke(UUID ownerId) {
        super(ownerId, 7, "Banishing Stroke", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{5}{W}");
        this.expansionSetCode = "AVR";

        this.color.setWhite(true);

        // Put target artifact, creature, or enchantment on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Miracle {W}
        this.addAbility(new MiracleAbility(new ManaCostsImpl("{W}")));
    }

    public BanishingStroke(final BanishingStroke card) {
        super(card);
    }

    @Override
    public BanishingStroke copy() {
        return new BanishingStroke(this);
    }
}
