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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class KumenaTyrantOfOrazca extends CardImpl {

    private static final FilterControlledPermanent filterAnotherNotTapped = new FilterControlledPermanent("another untapped Merfolk you control");
    private static final FilterControlledPermanent filterNotTapped = new FilterControlledPermanent("untapped Merfolk you control");
    private static final FilterControlledPermanent filterAll = new FilterControlledPermanent("Merfolk you control");

    static {
        filterAnotherNotTapped.add(new AnotherPredicate());
        filterAnotherNotTapped.add(new SubtypePredicate(SubType.MERFOLK));
        filterAnotherNotTapped.add(Predicates.not(new TappedPredicate()));

        filterNotTapped.add(new SubtypePredicate(SubType.MERFOLK));
        filterNotTapped.add(Predicates.not(new TappedPredicate()));

        filterAll.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public KumenaTyrantOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Tap another untapped Merfolk you control: Kumena, Tyrant of Orzca can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(1, 1, filterAnotherNotTapped, true))));

        // Tap three untapped Merfolk you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(3, 3, filterNotTapped, true))));

        // Tap five untapped Merfolk you control: Put a +1/+1 counter on each Merfolk you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterAll),
                new TapTargetCost(new TargetControlledPermanent(5, 5, filterNotTapped, true))));

    }

    public KumenaTyrantOfOrazca(final KumenaTyrantOfOrazca card) {
        super(card);
    }

    @Override
    public KumenaTyrantOfOrazca copy() {
        return new KumenaTyrantOfOrazca(this);
    }
}
