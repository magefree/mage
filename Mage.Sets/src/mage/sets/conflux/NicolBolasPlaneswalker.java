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
package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class NicolBolasPlaneswalker extends CardImpl<NicolBolasPlaneswalker> {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public NicolBolasPlaneswalker(UUID ownerId) {
        super(ownerId, 120, "Nicol Bolas, Planeswalker", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}{B}{R}");
        this.expansionSetCode = "CON";
        this.subtype.add("Bolas");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), ""));

        // +3: Destroy target noncreature permanent.
        LoyaltyAbility ability = new LoyaltyAbility(new DestroyTargetEffect(), 3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // -2: Gain control of target creature.
        ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.Custom), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // -9: Nicol Bolas, Planeswalker deals 7 damage to target player. That player discards seven cards, then sacrifices seven permanents.
        ability = new LoyaltyAbility(new DamageTargetEffect(7), -9);
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new DiscardTargetEffect(7));
        ability.addEffect(new SacrificeEffect(new FilterPermanent(), 7, "then"));
        this.addAbility(ability);
    }

    public NicolBolasPlaneswalker(final NicolBolasPlaneswalker card) {
        super(card);
    }

    @Override
    public NicolBolasPlaneswalker copy() {
        return new NicolBolasPlaneswalker(this);
    }
}
