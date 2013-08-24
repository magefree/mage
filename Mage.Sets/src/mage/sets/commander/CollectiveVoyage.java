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
package mage.sets.commander;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class CollectiveVoyage extends CardImpl<CollectiveVoyage> {

    public CollectiveVoyage(UUID ownerId) {
        super(ownerId, 147, "Collective Voyage", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "CMD";

        this.color.setGreen(true);

        // Join forces - Starting with you, each player may pay any amount of mana. Each player searches his or her library for up to X basic land cards, where X is the total amount of mana paid this way, puts them onto the battlefield tapped, then shuffles his or her library.
        this.getSpellAbility().addEffect(new CollectiveVoyageEffect());
    }

    public CollectiveVoyage(final CollectiveVoyage card) {
        super(card);
    }

    @Override
    public CollectiveVoyage copy() {
        return new CollectiveVoyage(this);
    }
}

class CollectiveVoyageEffect extends OneShotEffect<CollectiveVoyageEffect> {

    public CollectiveVoyageEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> - Starting with you, each player may pay any amount of mana. Each player searches his or her library for up to X basic land cards, where X is the total amount of mana paid this way, puts them onto the battlefield tapped, then shuffles his or her library";
    }

    public CollectiveVoyageEffect(final CollectiveVoyageEffect effect) {
        super(effect);
    }

    @Override
    public CollectiveVoyageEffect copy() {
        return new CollectiveVoyageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += playerPaysXGenericMana(controller, source, game);
            for(UUID playerId : controller.getInRange()) {
                if (playerId != controller.getId()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += playerPaysXGenericMana(player, source, game);

                    }
                }
            }
            for(UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, xSum, new FilterBasicLandCard());
                    if (player.searchLibrary(target, game)) {
                        for (UUID cardId : target.getTargets()) {
                            Card card = player.getLibrary().getCard(cardId, game);
                            if (card != null) {
                                card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), player.getId(), true);
                            }

                        }
                        player.shuffleLibrary(game);
                    }

                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }

    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (!payed) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false);
            } else {
                payed = true;
            }
        }
        game.informPlayers(new StringBuilder(player.getName()).append(" pays {").append(xValue).append("}.").toString());
        return xValue;
    }
}

