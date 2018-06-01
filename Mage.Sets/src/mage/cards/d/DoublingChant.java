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
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class DoublingChant extends CardImpl {

    public DoublingChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // For each creature you control, you may search your library for a creature card with the same name as that creature.
        // Put those cards onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new DoublingChantEffect());
    }

    public DoublingChant(final DoublingChant card) {
        super(card);
    }

    @Override
    public DoublingChant copy() {
        return new DoublingChant(this);
    }
}

class DoublingChantEffect extends OneShotEffect {

    public DoublingChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature you control, you may search your library for a creature card with the same name as that creature. Put those cards onto the battlefield, then shuffle your library";
    }

    public DoublingChantEffect(final DoublingChantEffect effect) {
        super(effect);
    }

    @Override
    public DoublingChantEffect copy() {
        return new DoublingChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> chosenCards = new HashSet<>();
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        if (creatures.isEmpty()) {
            //9/22/2011: If you control no creatures when Doubling Chant resolves, you may still search your library and you must shuffle your library.
            if (controller.chooseUse(Outcome.PutCreatureInPlay, "Search in your library?", source, game)) {
                FilterCreatureCard filter = new FilterCreatureCard("nothing (no valid card available)");
                filter.add(new NamePredicate("creatureName"));
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                controller.searchLibrary(target, game);
            }
        }
        for (Permanent creature : creatures) {
            final String creatureName = creature.getName();
            List<CardIdPredicate> uuidPredicates = new ArrayList<>();
            if (controller.chooseUse(Outcome.PutCreatureInPlay, "Search for " + creatureName + " in your library?", source, game)) {
                FilterCreatureCard filter = new FilterCreatureCard("creature card named " + creatureName);
                filter.add(new NamePredicate(creatureName));
                if (!uuidPredicates.isEmpty()) { // Prevent to select a card twice
                    filter.add(Predicates.not(Predicates.or(uuidPredicates)));
                }
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                if (controller.searchLibrary(target, game)) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        chosenCards.add(card);
                        uuidPredicates.add(new CardIdPredicate(card.getId()));
                    }
                }
            }

        }
        controller.moveCards(chosenCards, Zone.BATTLEFIELD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
