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
package mage.sets.shardsofalara;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class ClarionUltimatum extends CardImpl {

    public ClarionUltimatum(UUID ownerId) {
        super(ownerId, 163, "Clarion Ultimatum", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{G}{G}{W}{W}{W}{U}{U}");
        this.expansionSetCode = "ALA";

        // Choose five permanents you control. For each of those permanents, you may search your library for a card with the same name as that permanent. Put those cards onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ClarionUltimatumEffect());
    }

    public ClarionUltimatum(final ClarionUltimatum card) {
        super(card);
    }

    @Override
    public ClarionUltimatum copy() {
        return new ClarionUltimatum(this);
    }
}

class ClarionUltimatumEffect extends OneShotEffect {

    public ClarionUltimatumEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose five permanents you control. For each of those permanents, you may search your library for a card with the same name as that permanent. Put those cards onto the battlefield tapped, then shuffle your library";
    }

    public ClarionUltimatumEffect(final ClarionUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public ClarionUltimatumEffect copy() {
        return new ClarionUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        int permanentsCount = game.getBattlefield().getAllActivePermanents(source.getControllerId()).size();
        if (controller == null || permanentsCount < 1) {
            return false;
        }

        TargetControlledPermanent permanentsTarget = new TargetControlledPermanent(Math.min(permanentsCount, 5));
        controller.choose(Outcome.Benefit, permanentsTarget, source.getSourceId(), game);

        Set<Card> chosenCards = new LinkedHashSet<>();
        List<String> namesFiltered = new ArrayList<>();
        List<UUID> permanents = permanentsTarget.getTargets();
        for (UUID permanentId : permanents) {
            Permanent permanent = game.getPermanent(permanentId);
            final String cardName = permanent.getName();
            if (!namesFiltered.contains(cardName)) {
                if (controller.chooseUse(Outcome.PutCardInPlay, "Search for " + cardName + " in your library?", source, game)) {
                    FilterCard filter = new FilterCard("card named " + cardName);
                    filter.add(new NamePredicate(cardName));
                    TargetCardInLibrary target = new TargetCardInLibrary(filter);
                    if (controller.searchLibrary(target, game)) {
                        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                        if (card != null) {
                            chosenCards.add(card);
                        }
                    }
                } else {
                    namesFiltered.add(cardName);
                }
            }
        }

        controller.moveCards(chosenCards, Zone.BATTLEFIELD, source, game, true, false, false, null);
        controller.shuffleLibrary(game);
        return true;
    }
}
