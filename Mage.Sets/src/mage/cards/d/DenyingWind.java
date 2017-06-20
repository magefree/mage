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
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class DenyingWind extends CardImpl {

    public DenyingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{U}{U}");

        // Search target player's library for up to seven cards and exile them. Then that player shuffles his or her library.
        getSpellAbility().addEffect(new DenyingWindEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    public DenyingWind(final DenyingWind card) {
        super(card);
    }

    @Override
    public DenyingWind copy() {
        return new DenyingWind(this);
    }
}

class DenyingWindEffect extends OneShotEffect {

    public DenyingWindEffect() {
        super(Outcome.Neutral);
        staticText = "search target player's library for up to seven cards and exile them. Then that player shuffles his or her library";
    }

    public DenyingWindEffect(final DenyingWindEffect effect) {
        super(effect);
    }

    @Override
    public DenyingWindEffect copy() {
        return new DenyingWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 7, new FilterCard("cards from player's library to exile"));
            if (controller.searchLibrary(target, game, player.getId())) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = player.getLibrary().remove(targetId, game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY, true);
                    }
                }
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
