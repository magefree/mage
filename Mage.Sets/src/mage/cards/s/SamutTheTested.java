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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreatureOrPlayerAmount;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Will
 */
public class SamutTheTested extends CardImpl {

    public SamutTheTested(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.subtype.add("Samut");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Up to one target creature gains double strike until end of turn.
        Effect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn);
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Samut, the Tested deals 2 damage divided as you choose among one or two target creatures and/or players.
        effect = new DamageMultiEffect(2);
        ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetCreatureOrPlayerAmount(2));
        this.addAbility(ability);

        // -7: Search your library or up to two creature and/or planeswalkercards, put them onto the battlefield, then shuffle your library.
        FilterCard filterCard = new FilterCard("creature or planeswalker card");
        filterCard.add(Predicates.or(
            new CardTypePredicate(CardType.CREATURE),
            new CardTypePredicate(CardType.PLANESWALKER)
        ));
        effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filterCard), false, true);
        ability = new LoyaltyAbility(effect, -7);
        this.addAbility(ability);
    }

    public SamutTheTested(final SamutTheTested card) {
        super(card);
    }

    @Override
    public SamutTheTested copy() {
        return new SamutTheTested(this);
    }
}
