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
package mage.sets.tempest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class LivingDeath extends CardImpl {

    public LivingDeath(UUID ownerId) {
        super(ownerId, 36, "Living Death", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "TMP";

        // Each player exiles all creature cards from his or her graveyard, then sacrifices all creatures he or she controls, then puts all cards he or she exiled this way onto the battlefield.
        this.getSpellAbility().addEffect(new LivingDeathEffect());
    }

    public LivingDeath(final LivingDeath card) {
        super(card);
    }

    @Override
    public LivingDeath copy() {
        return new LivingDeath(this);
    }
}

class LivingDeathEffect extends OneShotEffect {

    public LivingDeathEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player exiles all creature cards from his or her graveyard, then sacrifices all creatures he or she controls, then puts all cards he or she exiled this way onto the battlefield";
    }

    public LivingDeathEffect(final LivingDeathEffect effect) {
        super(effect);
    }

    @Override
    public LivingDeathEffect copy() {
        return new LivingDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Map<UUID, Set<Card>> exiledCards = new HashMap<>();
            // move creature cards from graveyard to exile
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cardsPlayer = player.getGraveyard().getCards(new FilterCreatureCard(), game);
                    if (!cardsPlayer.isEmpty()) {
                        exiledCards.put(player.getId(), cardsPlayer);
                        player.moveCards(cardsPlayer, Zone.EXILED, source, game);
                    }
                }
            }
            game.applyEffects();
            // sacrifice all creatures
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                permanent.sacrifice(source.getSourceId(), game);
            }
            game.applyEffects();
            // put exiled cards to battlefield
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cardsPlayer = exiledCards.get(playerId);
                    if (cardsPlayer != null && !cardsPlayer.isEmpty()) {
                        player.moveCards(cardsPlayer, Zone.BATTLEFIELD, source, game, false, false, false, null);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
