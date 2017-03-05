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
package mage.cards.s;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
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
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class StrongholdGambit extends CardImpl {

    public StrongholdGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Each player chooses a card in his or her hand. Then each player reveals his or her chosen card. The owner of each creature card revealed this way with the lowest converted mana cost puts it onto the battlefield.
        getSpellAbility().addEffect(new StrongholdGambitEffect());
    }

    public StrongholdGambit(final StrongholdGambit card) {
        super(card);
    }

    @Override
    public StrongholdGambit copy() {
        return new StrongholdGambit(this);
    }
}

class StrongholdGambitEffect extends OneShotEffect {

    public StrongholdGambitEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player chooses a card in his or her hand. Then each player reveals his or her chosen card. The owner of each creature card revealed this way with the lowest converted mana cost puts it onto the battlefield";
    }

    public StrongholdGambitEffect(final StrongholdGambitEffect effect) {
        super(effect);
    }

    @Override
    public StrongholdGambitEffect copy() {
        return new StrongholdGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Map<UUID, UUID> choosenCard = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                Player player = game.getPlayer(playerId);
                if (player != null && !player.getHand().isEmpty()) {
                    TargetCardInHand target = new TargetCardInHand();
                    if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                        choosenCard.put(playerId, target.getFirstTarget());
                    }
                }
            }
            int lowestCMC = Integer.MAX_VALUE;
            for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                Player player = game.getPlayer(playerId);
                if (player != null && choosenCard.containsKey(playerId)) {
                    Card card = game.getCard(choosenCard.get(playerId));
                    if (card != null) {
                        Cards cardsToReveal = new CardsImpl(card);
                        player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', cardsToReveal, game);
                        if (card.isCreature()
                                && lowestCMC > card.getConvertedManaCost()) {
                            lowestCMC = card.getConvertedManaCost();
                        }
                    }
                }
            }
            if (lowestCMC < Integer.MAX_VALUE) {
                Cards creaturesToBattlefield = new CardsImpl();
                for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && choosenCard.containsKey(playerId)) {
                        Card card = game.getCard(choosenCard.get(playerId));
                        if (card != null) {
                            if (card.isCreature()
                                    && lowestCMC == card.getConvertedManaCost()) {
                                creaturesToBattlefield.add(card);
                            }
                        }
                    }
                }
                controller.moveCards(creaturesToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }
}
