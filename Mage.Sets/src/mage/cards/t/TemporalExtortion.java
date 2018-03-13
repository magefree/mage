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
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TemporalExtortion extends CardImpl {

    public TemporalExtortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}{B}{B}");

        // When you cast Temporal Extortion, any player may pay half his or her life, rounded up. If a player does, counter Temporal Extortion.
        this.addAbility(new CastSourceTriggeredAbility(new TemporalExtortionCounterSourceEffect()));

        // Take an extra turn after this one.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
    }

    public TemporalExtortion(final TemporalExtortion card) {
        super(card);
    }

    @Override
    public TemporalExtortion copy() {
        return new TemporalExtortion(this);
    }
}

class TemporalExtortionCounterSourceEffect extends OneShotEffect {

    public TemporalExtortionCounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "any player may pay half his or her life, rounded up. If a player does, counter {source}";
    }

    public TemporalExtortionCounterSourceEffect(final TemporalExtortionCounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public TemporalExtortionCounterSourceEffect copy() {
        return new TemporalExtortionCounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player.chooseUse(outcome, "Pay half your life, rounded up to counter " + sourceObject.getIdName() + '?', source, game)) {
                    Integer amount = (int) Math.ceil(player.getLife() / 2f);
                    player.loseLife(amount, game, false);
                    game.informPlayers(player.getLogName() + " pays half his or her life, rounded up to counter " + sourceObject.getIdName() + '.');
                    Spell spell = game.getStack().getSpell(source.getSourceId());
                    if (spell != null) {
                        game.getStack().counter(spell.getId(), source.getSourceId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
