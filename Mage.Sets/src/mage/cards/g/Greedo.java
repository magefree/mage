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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class Greedo extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Hunter or Rogue card");

    static {
        filterCard.add(Predicates.or(new SubtypePredicate(SubType.ROGUE), new SubtypePredicate(SubType.HUNTER)));

    }

    public Greedo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Rodian");
        this.subtype.add("Hunter");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creatures blocking or blocked by Greedo have first strike.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()), new BlockingAttackerIdPredicate(this.getId())));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, "Creatures blocking or blocked by {this} have first strike")));

        // When Greedo dies, you may search your library for Hunter or Rogue card, reveal it, and put it into your hand.
        this.addAbility(new DiesTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true), true));
    }

    public Greedo(final Greedo card) {
        super(card);
    }

    @Override
    public Greedo copy() {
        return new Greedo(this);
    }
}
