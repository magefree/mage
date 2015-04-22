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
package mage.sets.commander2014;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public ScrapMastery(UUID ownerId) {
        super(ownerId, 38, "Scrap Mastery", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "C14";

        this.color.setRed(true);

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
            Map<UUID, Set<UUID>> exiledCards = new HashMap<>();
            // exile artifacts from graveyard
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<UUID> cards = new HashSet<>();
                    for (Card card: player.getGraveyard().getCards(new FilterArtifactCard(), game)) {
                        cards.add(card.getId());
                        player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                    }
                    exiledCards.put(player.getId(), cards);
                }
            }
            // sacrifice all artifacts
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent: game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), playerId, game)) {
                        permanent.sacrifice(source.getSourceId(), game);
                    }
                }
            }
            // puts all cards he or she exiled this way onto the battlefield
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (UUID cardId: exiledCards.get(playerId)) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            player.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, cardId);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
