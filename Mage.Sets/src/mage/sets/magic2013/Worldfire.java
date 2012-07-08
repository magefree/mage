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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class Worldfire extends CardImpl<Worldfire> {

    public Worldfire(UUID ownerId) {
        super(ownerId, 158, "Worldfire", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{6}{R}{R}{R}");
        this.expansionSetCode = "M13";

        this.color.setRed(true);

        // Exile all permanents. Exile all cards from all hands and graveyards. Each player's life total becomes 1.
        this.getSpellAbility().addEffect(new WorldfireEffect());
    }

    public Worldfire(final Worldfire card) {
        super(card);
    }

    @Override
    public Worldfire copy() {
        return new Worldfire(this);
    }
}

class WorldfireEffect extends OneShotEffect<WorldfireEffect> {
    
    private static FilterPermanent filter = new FilterPermanent();
    
    public WorldfireEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "Exile all permanents. Exile all cards from all hands and graveyards. Each player's life total becomes 1";
    }

    public WorldfireEffect(final WorldfireEffect effect) {
        super(effect);
    }

    @Override
    public WorldfireEffect copy() {
        return new WorldfireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
            permanent.moveToExile(id, "all permanents", id, game);
        }
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getHand().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getId(), game);
                    }
                }
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getId(), game);
                    }
                }
                player.setLife(1, game);
            }
        }
        return true;
    }
}
