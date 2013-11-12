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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class TemptWithDiscovery extends CardImpl<TemptWithDiscovery> {

    public TemptWithDiscovery(UUID ownerId) {
        super(ownerId, 174, "Tempt with Discovery", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}");
        this.expansionSetCode = "C13";

        this.color.setGreen(true);

        // Tempting offer - Search your library for a land card and put it onto the battlefield.
        // Each opponent may search his or her library for a land card and put it onto the battlefield.
        // For each opponent who searches a library this way, search your library for a land card and put it onto the battlefield.
        // Then each player who searched a library this way shuffles it.
        this.getSpellAbility().addEffect(new TemptWithDiscoveryEffect());
    }

    public TemptWithDiscovery(final TemptWithDiscovery card) {
        super(card);
    }

    @Override
    public TemptWithDiscovery copy() {
        return new TemptWithDiscovery(this);
    }
}

class TemptWithDiscoveryEffect extends OneShotEffect<TemptWithDiscoveryEffect> {

    public TemptWithDiscoveryEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "<i>Tempting offer</i> - Search your library for a land card and put it onto the battlefield. Each opponent may search his or her library for a land card and put it onto the battlefield. For each opponent who searches a library this way, search your library for a land card and put it onto the battlefield. Then each player who searched a library this way shuffles it";
    }

    public TemptWithDiscoveryEffect(final TemptWithDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithDiscoveryEffect copy() {
        return new TemptWithDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
            if (controller.searchLibrary(target, game)) {
                for (UUID cardId: target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), controller.getId());
                    }
                }
            }
            int opponentsUsedSearch = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, "Search your library for a land card and put it onto the battlefield?", game)) {
                        target.clearChosen();
                        opponentsUsedSearch++;
                        if (opponent.searchLibrary(target, game)) {
                            for (UUID cardId: target.getTargets()) {
                                Card card = game.getCard(cardId);
                                if (card != null) {
                                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), opponent.getId());
                                }
                            }
                        }
                    }
                }
            }
            if (opponentsUsedSearch > 0) {
                target = new TargetCardInLibrary(0, opponentsUsedSearch, new FilterLandCard());
                if (controller.searchLibrary(target, game)) {
                    for (UUID cardId: target.getTargets()) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), controller.getId());
                        }
                    }
                }
            }
            return true;
        }
        
        return false;
    }
}
