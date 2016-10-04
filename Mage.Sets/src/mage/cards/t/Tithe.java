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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class Tithe extends CardImpl {

    public Tithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Search your library for a Plains card. If target opponent controls more lands than you, you may search your library for an additional Plains card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new TitheEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Tithe(final Tithe card) {
        super(card);
    }

    @Override
    public Tithe copy() {
        return new Tithe(this);
    }
}

class TitheEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Plains");
    static {
        filter.add(new SubtypePredicate("Plains"));
    }

    TitheEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a Plains card. If target opponent controls more lands than you, you may search your library for an additional Plains card. Reveal those cards and put them into your hand. Then shuffle your library";
    }

    TitheEffect(final TitheEffect effect) {
        super(effect);
    }

    @Override
    public TitheEffect copy() {
        return new TitheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numYourLands = game.getBattlefield().countAll(new FilterLandPermanent(), source.getControllerId(), game);
        int numOpponentLands = game.getBattlefield().countAll(new FilterLandPermanent(), this.getTargetPointer().getFirst(game, source), game);
        new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, (numOpponentLands > numYourLands ? 2 : 1), filter), true).apply(game, source);
        return true;
    }
}
