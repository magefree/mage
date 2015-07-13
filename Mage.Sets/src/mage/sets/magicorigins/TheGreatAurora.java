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
package mage.sets.magicorigins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class TheGreatAurora extends CardImpl {

    public TheGreatAurora(UUID ownerId) {
        super(ownerId, 179, "The Great Aurora", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{6}{G}{G}{G}");
        this.expansionSetCode = "ORI";

        // Each player shuffles all cards from his or her hand and all permanents he or she owns into his or her library, then draws that many cards. Each player may put any number of land cards from his or her hand onto the battlefield. Exile The Great Aurora.
        this.getSpellAbility().addEffect(new TheGreatAuroraEffect());
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public TheGreatAurora(final TheGreatAurora card) {
        super(card);
    }

    @Override
    public TheGreatAurora copy() {
        return new TheGreatAurora(this);
    }
}

class TheGreatAuroraEffect extends OneShotEffect {

    public TheGreatAuroraEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player shuffles all cards from his or her hand and all permanents he or she owns into his or her library, then draws that many cards. Each player may put any number of land cards from his or her hand onto the battlefield";
    }

    public TheGreatAuroraEffect(final TheGreatAuroraEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatAuroraEffect copy() {
        return new TheGreatAuroraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Map<UUID, List<Permanent>> permanentsOwned = new HashMap<>();
        Collection<Permanent> permanents = game.getBattlefield().getAllPermanents();
        for (Permanent permanent : permanents) {
            List<Permanent> list = permanentsOwned.get(permanent.getOwnerId());
            if (list == null) {
                list = new ArrayList<>();
                permanentsOwned.put(permanent.getOwnerId(), list);
            }
            list.add(permanent);
        }

        // shuffle permanents and hand cards into owner's library
        Map<UUID, Integer> permanentsCount = new HashMap<>();

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int handCards = player.getHand().size();
                player.moveCards(player.getHand(), Zone.HAND, Zone.LIBRARY, source, game);
                List<Permanent> list = permanentsOwned.remove(player.getId());
                permanentsCount.put(playerId, handCards + (list != null ? list.size() : 0));
                for (Permanent permanent : list) {
                    player.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD, true, true);
                }
                player.getLibrary().shuffle();
            }
        }

        // Draw cards
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int count = permanentsCount.get(playerId);
                if (count > 0) {
                    player.drawCards(count, game);
                }
            }
        }

        // put lands onto the battlefield
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterLandCard("put any number of land cards from your hand onto the battlefield"));
                player.chooseTarget(Outcome.PutLandInPlay, player.getHand(), target, source, game);
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId(), false);
                    }
                }

            }
        }
        return true;
    }
}
