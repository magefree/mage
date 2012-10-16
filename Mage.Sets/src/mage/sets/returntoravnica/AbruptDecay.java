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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.CantCounterSourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class AbruptDecay extends CardImpl<AbruptDecay> {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent with converted mana cost 3 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 4));
    }

    public AbruptDecay (UUID ownerId) {
        super(ownerId, 141, "Abrupt Decay", Rarity.MYTHIC, new CardType[]{CardType.INSTANT}, "{B}{G}");
        this.expansionSetCode = "RTR";
        this.color.setGreen(true);
        this.color.setBlack(true);

        // Abrupt Decay can't be countered by spells or abilities.
        this.getSpellAbility().addEffect(new CantCounterSourceEffect());

        // Destroy target nonland permanent with converted mana cost 3 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filter));
    }

    public AbruptDecay (final AbruptDecay card) {
        super(card);
    }

    @Override
    public AbruptDecay copy() {
        return new AbruptDecay(this);
    }
}
