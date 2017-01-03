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
package mage.cards.a;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.SwordsToPlowsharesEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.constants.Zone;

/**
 * @author JRHerlehy
 */
public class AjaniUnyielding extends CardImpl {

    private static final FilterPermanentCard nonlandPermanentFilter = new FilterPermanentCard("nonland permanent cards");
    private static final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("creature you control");
    private static final FilterPlaneswalkerPermanent planeswalkerFilter = new FilterPlaneswalkerPermanent("other planeswalker you control");

    static {
        nonlandPermanentFilter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        creatureFilter.add(new ControllerPredicate(TargetController.YOU));
        planeswalkerFilter.add(new ControllerPredicate(TargetController.YOU));
        planeswalkerFilter.add(new AnotherPredicate());
    }

    public AjaniUnyielding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{W}");
        this.subtype.add("Ajani");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +2: Reveal the top three cards of your library. Put all nonland permanent cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new RevealLibraryPutIntoHandEffect(3, nonlandPermanentFilter, Zone.LIBRARY), 2));

        // -2: Exile target creature. Its controller gains life equal to its power.
        LoyaltyAbility ajaniAbility2 = new LoyaltyAbility(new SwordsToPlowsharesEffect(), -2);
        ajaniAbility2.addEffect(new ExileTargetEffect());
        ajaniAbility2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ajaniAbility2);

        // -9: Put five +1/+1 counters on each creature you control and five loyalty counters on each other planeswalker you control.
        LoyaltyAbility ajaniAbility3 = new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(5), creatureFilter), -9);
        ajaniAbility3.addEffect(new AddCountersAllEffect(CounterType.LOYALTY.createInstance(5), planeswalkerFilter));
        this.addAbility(ajaniAbility3);
    }

    public AjaniUnyielding(final AjaniUnyielding card) {
        super(card);
    }

    @Override
    public AjaniUnyielding copy() {
        return new AjaniUnyielding(this);
    }
}
