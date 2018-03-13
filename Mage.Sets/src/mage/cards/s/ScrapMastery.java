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

import java.util.HashMap;
import java.util.Map;
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
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ScrapMastery extends CardImpl {

    public ScrapMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Each player exiles all artifact cards from his or her graveyard, then sacrifices all artifacts he or she controls, then puts all cards he or she exiled this way onto the battlefield.
        this.getSpellAbility().addEffect(new ScrapMasteryEffect());
    }

    public ScrapMastery(final ScrapMastery card) {
        super(card);
    }

    @Override
    public ScrapMastery copy() {
        return new ScrapMastery(this);
    }
}

class ScrapMasteryEffect extends OneShotEffect {

    public ScrapMasteryEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Each player exiles all artifact cards from his or her graveyard, then sacrifices all artifacts he or she controls, then puts all cards he or she exiled this way onto the battlefield";
    }

    public ScrapMasteryEffect(final ScrapMasteryEffect effect) {
        super(effect);
    }

    @Override
    public ScrapMasteryEffect copy() {
        return new ScrapMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Set<Card>> exiledCards = new HashMap<>();
            // exile artifacts from graveyard
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cards = player.getGraveyard().getCards(new FilterArtifactCard(), game);
                    controller.moveCards(cards, Zone.EXILED, source, game);
                    exiledCards.put(player.getId(), cards);
                }
            }
            // sacrifice all artifacts
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), playerId, game)) {
                        permanent.sacrifice(source.getSourceId(), game);
                    }
                }
            }
            // puts all cards he or she exiled this way onto the battlefield
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(exiledCards.get(playerId), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
