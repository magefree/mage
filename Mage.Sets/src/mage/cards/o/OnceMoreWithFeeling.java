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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SetPlayerLifeAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class OnceMoreWithFeeling extends CardImpl {

    public OnceMoreWithFeeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{W}{W}");

        // Exile all permanents and all cards from all graveyards. Each player shuffles his or her hand into his or her library, then draws seven cards. Each player's life total becomes 10. Exile Once More with Feeling.
        this.getSpellAbility().addEffect(new OnceMoreWithFeelingEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new SetPlayerLifeAllEffect(10));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

        // DCI ruling — A deck can have only one card named Once More with Feeling.
        // (according to rule 112.6m, this shouldn't do anything)
        this.getSpellAbility().addEffect(new InfoEffect("<br>DCI ruling &mdash; A deck can have only one card named {this}"));
    }

    public OnceMoreWithFeeling(final OnceMoreWithFeeling card) {
        super(card);
    }

    @Override
    public OnceMoreWithFeeling copy() {
        return new OnceMoreWithFeeling(this);
    }
}

class OnceMoreWithFeelingEffect extends OneShotEffect {

    public OnceMoreWithFeelingEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all permanents and all cards from all graveyards. Each player shuffles his or her hand into his or her library";
    }

    public OnceMoreWithFeelingEffect(final OnceMoreWithFeelingEffect effect) {
        super(effect);
    }

    @Override
    public OnceMoreWithFeelingEffect copy() {
        return new OnceMoreWithFeelingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            permanent.moveToExile(null, "", source.getSourceId(), game);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getSourceId(), game);
                    }
                }
                player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }
}
