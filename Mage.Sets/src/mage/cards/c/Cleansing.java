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
package mage.cards.c;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
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
public class Cleansing extends CardImpl {

    public Cleansing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{W}");

        // For each land, destroy that land unless any player pays 1 life.
        this.getSpellAbility().addEffect(new CleansingEffect());
    }

    public Cleansing(final Cleansing card) {
        super(card);
    }

    @Override
    public Cleansing copy() {
        return new Cleansing(this);
    }
}

class CleansingEffect extends OneShotEffect {

    CleansingEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "For each land, destroy that land unless any player pays 1 life";
    }

    CleansingEffect(final CleansingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Iterator<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game).iterator();
        while (permanents.hasNext()) {
            boolean paidLife = false;
            Permanent p = permanents.next();
            if (p.isLand()) {
                paidLife = false;
                game.informPlayers("Any player may pay 1 life to prevent the destruction of " + p.getLogName() + " controlled by " + game.getPlayer(p.getControllerId()).getLogName() + ".");
                PayLifeCost cost = new PayLifeCost(1);
                for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    cost.clearPaid();
                    if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                            && player.chooseUse(outcome, "Pay 1 life to prevent this?", source, game)) {
                        if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                            game.informPlayers(player.getLogName() + " pays 1 life to prevent the destruction of " + p.getLogName());
                            paidLife = true;
                        }
                    }
                }
                if (!paidLife) {
                    p.destroy(source.getSourceId(), game, false);
                }
            }
        }

        return true;
    }

    @Override
    public CleansingEffect copy() {
        return new CleansingEffect(this);
    }
}
