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
package mage.cards.m;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class MartyrsCry extends CardImpl {

    public MartyrsCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}");

        // Exile all white creatures. For each creature exiled this way, its controller draws a card.
        this.getSpellAbility().addEffect(new MartyrsCryEffect());
    }

    public MartyrsCry(final MartyrsCry card) {
        super(card);
    }

    @Override
    public MartyrsCry copy() {
        return new MartyrsCry(this);
    }
}

class MartyrsCryEffect extends OneShotEffect {

    MartyrsCryEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all white creatures. For each creature exiled this way, its controller draws a card.";
    }

    MartyrsCryEffect(final MartyrsCryEffect effect) {
        super(effect);
    }

    @Override
    public MartyrsCryEffect copy() {
        return new MartyrsCryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playerCrtCount = new HashMap<>();
        for (Iterator<Permanent> it = game.getBattlefield().getActivePermanents(source.getControllerId(), game).iterator(); it.hasNext();) {
            Permanent perm = it.next();
            if (perm != null && perm.isCreature() && perm.getColor(game).isWhite() && perm.moveToExile(null, null, source.getSourceId(), game)) {
                playerCrtCount.putIfAbsent(perm.getControllerId(), 0);
                playerCrtCount.compute(perm.getControllerId(), (p, amount) -> amount + 1);
            }
        }
        for (UUID playerId : game.getPlayerList().toList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(playerCrtCount.getOrDefault(playerId, 0), game);
            }
        }
        return true;
    }
}
