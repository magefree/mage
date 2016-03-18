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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public class TraverseTheUlvenwald extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or land card");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }

    public TraverseTheUlvenwald(UUID ownerId) {
        super(ownerId, 234, "Traverse the Ulvenwald", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "SOI";

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, new FilterBasicLandCard()), true),
                new InvertCondition(DeliriumCondition.getInstance()),
                "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library."));

        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead search your library
        // for a creature or land card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, filter), true),
                DeliriumCondition.getInstance(),
                "<br><i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead search your library for a creature or land card, "
                        + "reveal it, put it into your hand, then shuffle your library."));
    }

    public TraverseTheUlvenwald(final TraverseTheUlvenwald card) {
        super(card);
    }

    @Override
    public TraverseTheUlvenwald copy() {
        return new TraverseTheUlvenwald(this);
    }
}
