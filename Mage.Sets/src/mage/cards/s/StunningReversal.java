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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class StunningReversal extends CardImpl {

    public StunningReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // The next time you would lose the game this turn, instead draw seven cards and your life total becomes 1. 
        this.getSpellAbility().addEffect(new StunningReversalEffect());

        // Exile Stunning Reversal.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public StunningReversal(final StunningReversal card) {
        super(card);
    }

    @Override
    public StunningReversal copy() {
        return new StunningReversal(this);
    }
}

class StunningReversalEffect extends ReplacementEffectImpl {

    public StunningReversalEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next time you would lose the game this turn, instead draw seven cards and your life total becomes 1. ";
    }

    public StunningReversalEffect(final StunningReversalEffect effect) {
        super(effect);
    }

    @Override
    public StunningReversalEffect copy() {
        return new StunningReversalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.drawCards(7, game);
            player.setLife(1, game, source);
            this.discard();
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }

}
