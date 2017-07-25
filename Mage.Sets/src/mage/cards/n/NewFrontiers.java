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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class NewFrontiers extends CardImpl {

    public NewFrontiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Each player may search his or her library for up to X basic land cards and put them onto the battlefield tapped. Then each player who searched his or her library this way shuffles it.
        this.getSpellAbility().addEffect(new NewFrontiersEffect());
    }

    public NewFrontiers(final NewFrontiers card) {
        super(card);
    }

    @Override
    public NewFrontiers copy() {
        return new NewFrontiers(this);
    }
}

class NewFrontiersEffect extends OneShotEffect {

    public NewFrontiersEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player may search his or her library for up to X basic land cards and put them onto the battlefield tapped. Then each player who searched his or her library this way shuffles it";
    }

    public NewFrontiersEffect(final NewFrontiersEffect effect) {
        super(effect);
    }

    @Override
    public NewFrontiersEffect copy() {
        return new NewFrontiersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = source.getManaCostsToPay().getX();

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, amount, StaticFilters.FILTER_BASIC_LAND_CARD);
                    if (player.searchLibrary(target, game)) {
                        for (UUID cardId : target.getTargets()) {
                            Card card = player.getLibrary().getCard(cardId, game);
                            if (card != null) {
                                card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), player.getId(), true);
                            }
                        }
                        player.shuffleLibrary(source, game);
                    }

                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
