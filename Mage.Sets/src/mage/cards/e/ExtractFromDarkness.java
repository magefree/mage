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
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class ExtractFromDarkness extends CardImpl {

    public ExtractFromDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{B}");

        // Each player puts the top two cards of his or her library into his or her graveyard.
        this.getSpellAbility().addEffect(new ExtractFromDarknessMillEffect());
        // Then put a creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect());
    }

    public ExtractFromDarkness(final ExtractFromDarkness card) {
        super(card);
    }

    @Override
    public ExtractFromDarkness copy() {
        return new ExtractFromDarkness(this);
    }
}

class ExtractFromDarknessMillEffect extends OneShotEffect {

    ExtractFromDarknessMillEffect() {
        super(Outcome.Detriment);
        staticText = "Each player puts the top two cards of his or her library into his or her graveyard";
    }

    ExtractFromDarknessMillEffect(final ExtractFromDarknessMillEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getLibrary().getTopCards(game, 2), Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public ExtractFromDarknessMillEffect copy() {
        return new ExtractFromDarknessMillEffect(this);
    }
}

class ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect extends OneShotEffect {

    public ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a creature card from a graveyard onto the battlefield under your control";
    }

    public ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect(final ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect copy() {
        return new ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInGraveyard(new FilterCreatureCard());
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                return controller.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
