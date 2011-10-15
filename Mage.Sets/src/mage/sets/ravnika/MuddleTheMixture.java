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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public class MuddleTheMixture extends CardImpl<MuddleTheMixture> {
    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.getCardType().add(CardType.INSTANT);
        filter.getCardType().add(CardType.SORCERY);
        filter.setNotScopeCardType(Filter.ComparisonScope.Any);
    }

    public MuddleTheMixture(UUID ownerId) {
        super(ownerId, 60, "Muddle the Mixture", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}{U}");
        this.expansionSetCode = "RAV";

        this.color.setBlue(true);

        // Counter target instant or sorcery spell.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
		this.getSpellAbility().addEffect(new CounterTargetEffect());
        // Transmute {1}{U}{U}
        this.addAbility(new TransmuteAbility("{1}{U}{U}"));
    }

    public MuddleTheMixture(final MuddleTheMixture card) {
        super(card);
    }

    @Override
    public MuddleTheMixture copy() {
        return new MuddleTheMixture(this);
    }
}
