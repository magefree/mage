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
import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public class ShefetMonitor extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or a Desert card");
    static {
        filter.add(
            Predicates.or(
                Predicates.and(
                         new CardTypePredicate(CardType.LAND),
                         new SupertypePredicate(SuperType.BASIC)),
                new SubtypePredicate("Desert")));
    }

    public ShefetMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add("Lizard");
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Cycling {3}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{3}{G}")));

        // When you cycle Shefet Monitor, you may search your library for a basic land card or a Desert card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new CycleTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true),
                true));
    }

    public ShefetMonitor(final ShefetMonitor card) {
        super(card);
    }

    @Override
    public ShefetMonitor copy() {
        return new ShefetMonitor(this);
    }
}
