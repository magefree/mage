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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class TheEldestReborn extends CardImpl {

    public TheEldestReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);
        // I — Each opponent sacrifices a creature or planeswalker.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A));

        // II — Each opponent discards a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
                new DiscardEachPlayerEffect(TargetController.OPPONENT));

        // III — Put target creature or planeswalker card from a graveyard onto the battlefield under your control.
        Ability ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new ReturnFromGraveyardToBattlefieldTargetEffect());
        FilterCard filter = new FilterCard("creature or planeswalker card from a graveyard");
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.PLANESWALKER)));
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(sagaAbility);

    }

    public TheEldestReborn(final TheEldestReborn card) {
        super(card);
    }

    @Override
    public TheEldestReborn copy() {
        return new TheEldestReborn(this);
    }
}
