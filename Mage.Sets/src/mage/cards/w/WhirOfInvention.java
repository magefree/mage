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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public class WhirOfInvention extends CardImpl {

    public WhirOfInvention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}{U}");


        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        addAbility(new ImproviseAbility());

        // Search your library for an artifact card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new WhirOfInventionSearchEffect());
    }

    public WhirOfInvention(final WhirOfInvention card) {
        super(card);
    }

    @Override
    public WhirOfInvention copy() {
        return new WhirOfInvention(this);
    }
}

class WhirOfInventionSearchEffect extends OneShotEffect {

    WhirOfInventionSearchEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Search your library for an artifact card with converted mana cost X or less, put it onto the battlefield, then shuffle your library";
    }

    WhirOfInventionSearchEffect(final WhirOfInventionSearchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }
        int xCost = source.getManaCostsToPay().getX();
        FilterCard filter = new FilterCard(new StringBuilder("artifact card with converted mana cost ").append(xCost).append(" or less").toString());
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        //Set the mana cost one higher to 'emulate' a less than or equal to comparison.
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, xCost + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    game.informPlayers(new StringBuilder(sourceCard.getName()).append(": Put ").append(card.getName()).append(" onto the battlefield").toString());
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId());
                }
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        player.shuffleLibrary(source, game);
        return false;
    }

    @Override
    public WhirOfInventionSearchEffect copy() {
        return new WhirOfInventionSearchEffect(this);
    }
}
