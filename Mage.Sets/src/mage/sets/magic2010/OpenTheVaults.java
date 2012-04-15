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
package mage.sets.magic2010;

import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class OpenTheVaults extends CardImpl<OpenTheVaults> {

    public OpenTheVaults(UUID ownerId) {
        super(ownerId, 21, "Open the Vaults", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "M10";

        this.color.setWhite(true);

        // Return all artifact and enchantment cards from all graveyards to the battlefield under their owners' control.
        this.getSpellAbility().addEffect(new OpenTheVaultsEffect());
    }

    public OpenTheVaults(final OpenTheVaults card) {
        super(card);
    }

    @Override
    public OpenTheVaults copy() {
        return new OpenTheVaults(this);
    }
}

class OpenTheVaultsEffect extends OneShotEffect<OpenTheVaultsEffect> {

    public OpenTheVaultsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all artifact and enchantment cards from all graveyards to the battlefield under their owners' control";
    }

    public OpenTheVaultsEffect(final OpenTheVaultsEffect effect) {
        super(effect);
    }

    @Override
    public OpenTheVaultsEffect copy() {
        return new OpenTheVaultsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            LinkedList<Card> enchantments = new LinkedList<Card>();
            LinkedList<Card> artifacts = new LinkedList<Card>();

            Set<UUID> playerIds = player.getInRange();
            playerIds.add(player.getId());

            for (UUID playerId : playerIds) {
                Cards graveyard = game.getPlayer(playerId).getGraveyard();
                for (UUID cardId : graveyard) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getCardType().contains(CardType.ENCHANTMENT)) {
                        enchantments.add(card);
                    }
                    if (card != null && card.getCardType().contains(CardType.ARTIFACT)) {
                        artifacts.add(card);
                    }
                }
            }

            for (Card card : enchantments) {
                card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), card.getOwnerId());
            }
            for (Card card : artifacts) {
                card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), card.getOwnerId());
            }
        }
        return false;
    }
}
