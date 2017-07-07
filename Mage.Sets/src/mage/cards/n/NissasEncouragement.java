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
package mage.cards.n;

import java.util.HashMap;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public class NissasEncouragement extends CardImpl {

    public NissasEncouragement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Search your library and graveyard for a card named Forest, a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage. Reveal those cards, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new NissasEncouragementEffect());

    }

    public NissasEncouragement(final NissasEncouragement card) {
        super(card);
    }

    @Override
    public NissasEncouragement copy() {
        return new NissasEncouragement(this);
    }
}

class NissasEncouragementEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Forest, a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage");
    private static final FilterCard filterGY = new FilterCard();

    static {
        filter.add(Predicates.or(new NamePredicate("Forest"), new NamePredicate("Brambleweft Behemoth"), new NamePredicate("Nissa, Genesis Mage")));
    }

    public NissasEncouragementEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library and graveyard for a card named Forest, a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage. Reveal those cards, put them into your hand, then shuffle your library.";
    }

    public NissasEncouragementEffect(final NissasEncouragementEffect effect) {
        super(effect);
    }

    @Override
    public NissasEncouragementEffect copy() {
        return new NissasEncouragementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }

        NissasEncouragementTarget target = new NissasEncouragementTarget(filter);
        if (player.searchLibrary(target, game)) {
            boolean searchGY = false;

            if (target.getTargets().size() < 3) {
                searchGY = true;
            }

            HashMap<String, Integer> foundCards = new HashMap<>();
            foundCards.put("Forest", 0);
            foundCards.put("Brambleweft Behemoth", 0);
            foundCards.put("Nissa, Genesis Mage", 0);
            Cards cards = new CardsImpl();
            
            if (!target.getTargets().isEmpty()) {                
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);

                    if (card != null) {
                        cards.add(card);
                        foundCards.put(card.getName(), 1);
                    }
                }
            }

            if (searchGY) {
                for (String name : foundCards.keySet()) {
                    if (foundCards.get(name) == 1) {
                        continue;
                    }
                    // Look in graveyard for any with this name
                    FilterCard namedFilterGY = filterGY.copy(); // never change static objects so copy the object here before
                    namedFilterGY.add(new NamePredicate(name));
                    if (player.getGraveyard().count(namedFilterGY, game) > 0) {
                        TargetCard targetGY = new TargetCard(0, 1, Zone.GRAVEYARD, namedFilterGY);
                        if (player.choose(Outcome.ReturnToHand, player.getGraveyard(), targetGY, game)) {
                            for (UUID cardIdGY : targetGY.getTargets()) {
                                Card cardGY = player.getGraveyard().get(cardIdGY, game);
                                cards.add(cardGY);
                            }
                        }
                    }
                }
            }
            
            if (!cards.isEmpty()) {
                player.revealCards(sourceCard.getIdName(), cards, game);
                player.moveCards(cards, Zone.HAND, source, game);
                player.shuffleLibrary(source, game);
                return true;
            }
        }
        player.shuffleLibrary(source, game);
        return false;
    }
}

class NissasEncouragementTarget extends TargetCardInLibrary {

    public NissasEncouragementTarget(FilterCard filter) {
        super(0, 3, filter);
    }

    public NissasEncouragementTarget(final NissasEncouragementTarget target) {
        super(target);
    }

    @Override
    public NissasEncouragementTarget copy() {
        return new NissasEncouragementTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            return filter.match(card, game);
        }
        return false;
    }
}
