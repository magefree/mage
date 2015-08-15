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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class BiteOfTheBlackRose extends CardImpl {

    public BiteOfTheBlackRose(UUID ownerId) {
        super(ownerId, 26, "Bite of the Black Rose", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "CNS";

        // Will of the council - Starting with you, each player votes for sickness or psychosis. If sickness gets more votes, creatures your opponents control get -2/-2 until end of turn. If psychosis gets more votes or the vote is tied, each opponent discards two cards.
        this.getSpellAbility().addEffect(new BiteOfTheBlackRoseEffect());
    }

    public BiteOfTheBlackRose(final BiteOfTheBlackRose card) {
        super(card);
    }

    @Override
    public BiteOfTheBlackRose copy() {
        return new BiteOfTheBlackRose(this);
    }
}

class BiteOfTheBlackRoseEffect extends OneShotEffect {

    BiteOfTheBlackRoseEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> - Starting with you, each player votes for sickness or psychosis. If sickness gets more votes, creatures your opponents control get -2/-2 until end of turn. If psychosis gets more votes or the vote is tied, each opponent discards two cards";
    }

    BiteOfTheBlackRoseEffect(final BiteOfTheBlackRoseEffect effect) {
        super(effect);
    }

    @Override
    public BiteOfTheBlackRoseEffect copy() {
        return new BiteOfTheBlackRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int sicknessCount = 0;
            int psychosisCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.ExtraTurn, "Choose sickness?", source, game)) {
                        sicknessCount++;
                        game.informPlayers(player.getLogName() + " has chosen: sickness");
                    } else {
                        psychosisCount++;
                        game.informPlayers(player.getLogName() + " has chosen: psychosis");
                    }
                }
            }
            if (sicknessCount > psychosisCount) {
                ContinuousEffect effect = new BoostOpponentsEffect(-2, -2, Duration.EndOfTurn);
                game.addEffect(effect, source);
            } else {
                new DiscardEachPlayerEffect(new StaticValue(2), false, TargetController.OPPONENT).apply(game, source);
            }
            return true;
        }
        return false;
    }
}