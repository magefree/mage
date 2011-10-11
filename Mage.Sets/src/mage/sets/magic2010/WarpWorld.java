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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author North
 */
public class WarpWorld extends CardImpl<WarpWorld> {

    public WarpWorld(UUID ownerId) {
        super(ownerId, 163, "Warp World", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}{R}{R}");
        this.expansionSetCode = "M10";

        this.color.setRed(true);

        // Each player shuffles all permanents he or she owns into his or her library, then reveals that many cards from the top of his or her library. Each player puts all artifact, creature, and land cards revealed this way onto the battlefield, then does the same for enchantment cards, then puts all cards revealed this way that weren't put onto the battlefield on the bottom of his or her library.
        this.getSpellAbility().addEffect(new WarpWorldEffect());
    }

    public WarpWorld(final WarpWorld card) {
        super(card);
    }

    @Override
    public WarpWorld copy() {
        return new WarpWorld(this);
    }
}

class WarpWorldEffect extends OneShotEffect<WarpWorldEffect> {

    public WarpWorldEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player shuffles all permanents he or she owns into his or her library, then reveals that many cards from the top of his or her library. Each player puts all artifact, creature, and land cards revealed this way onto the battlefield, then does the same for enchantment cards, then puts all cards revealed this way that weren't put onto the battlefield on the bottom of his or her library";
    }

    public WarpWorldEffect(final WarpWorldEffect effect) {
        super(effect);
    }

    @Override
    public WarpWorldEffect copy() {
        return new WarpWorldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, List<Permanent>> permanentsOwned = new HashMap<UUID, List<Permanent>>();

        Collection<Permanent> permanents = game.getBattlefield().getAllPermanents();
        for (Permanent permanent : permanents) {
            List<Permanent> list = permanentsOwned.get(permanent.getOwnerId());
            if (list == null) {
                list = new ArrayList<Permanent>();
            }
            list.add(permanent);
            permanentsOwned.put(permanent.getOwnerId(), list);
        }

        // shuffle permanents into owner's library
        Map<UUID, Integer> permanentsCount = new HashMap<UUID, Integer>();
        PlayerList playerList = game.getPlayerList();
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        do {
            List<Permanent> list = permanentsOwned.remove(player.getId());
            Integer count = 0;
            if (list != null) {
                count = list.size();
                for (Permanent permanent : list) {
                    permanent.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                }
            }

            if (count > 0) {
                player.getLibrary().shuffle();
            }

            permanentsCount.put(player.getId(), count);
            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));


        Map<UUID, CardsImpl> cardsRevealed = new HashMap<UUID, CardsImpl>();

        // draw cards and reveal them
        playerList.setCurrent(game.getActivePlayerId());
        player = game.getPlayer(game.getActivePlayerId());
        do {
            Integer count = Math.min(permanentsCount.get(player.getId()), player.getLibrary().size());
            CardsImpl cards = new CardsImpl();
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            player.revealCards("Warp World " + player.getName(), cards, game);

            cardsRevealed.put(player.getId(), cards);

            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));


        // put artifacts, creaturs and lands onto the battlefield
        playerList.setCurrent(game.getActivePlayerId());
        player = game.getPlayer(game.getActivePlayerId());
        do {
            CardsImpl cards = cardsRevealed.get(player.getId());
            for (Card card : cards.getCards(game)) {
                if (card != null && card.getCardType().contains(CardType.ARTIFACT)
                        || card.getCardType().contains(CardType.CREATURE)
                        || card.getCardType().contains(CardType.LAND)) {
                    card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), player.getId());
                    cards.remove(card);
                }
            }

            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));

        // put enchantments onto the battlefield
        playerList.setCurrent(game.getActivePlayerId());
        player = game.getPlayer(game.getActivePlayerId());
        do {
            CardsImpl cards = cardsRevealed.get(player.getId());
            for (Card card : cards.getCards(game)) {
                if (card != null && card.getCardType().contains(CardType.ENCHANTMENT)) {
                    card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), player.getId());
                    cards.remove(card);
                }
            }

            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));

        // put the rest of the cards into the graveyard
        playerList.setCurrent(game.getActivePlayerId());
        player = game.getPlayer(game.getActivePlayerId());
        do {
            CardsImpl cards = cardsRevealed.get(player.getId());
            for (Card card : cards.getCards(game)) {
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                }
            }

            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));

        return true;
    }
}
