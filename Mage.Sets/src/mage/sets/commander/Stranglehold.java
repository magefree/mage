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
package mage.sets.commander;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Stranglehold extends CardImpl {

    public Stranglehold(UUID ownerId) {
        super(ownerId, 136, "Stranglehold", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "CMD";

        this.color.setRed(true);

        // Your opponents can't search libraries.
        this.getSpellAbility().addEffect(new OpponentsCantSearchLibarariesEffect());
        // If an opponent would begin an extra turn, that player skips that turn instead.
        this.getSpellAbility().addEffect(new StrangleholdSkipExtraTurnsEffect());
    }

    public Stranglehold(final Stranglehold card) {
        super(card);
    }

    @Override
    public Stranglehold copy() {
        return new Stranglehold(this);
    }
}

class OpponentsCantSearchLibarariesEffect extends ContinuousRuleModifyingEffectImpl {

    public OpponentsCantSearchLibarariesEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, true, false);
        staticText = "Your opponents can't search libraries";
    }

    public OpponentsCantSearchLibarariesEffect(final OpponentsCantSearchLibarariesEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsCantSearchLibarariesEffect copy() {
        return new OpponentsCantSearchLibarariesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't search libraries (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (EventType.SEARCH_LIBRARY.equals(event.getType())) {
            Player controller = game.getPlayer(source.getControllerId());
            return controller != null && controller.hasOpponent(event.getPlayerId(), game);
        }
        return false;
    }
}

class StrangleholdSkipExtraTurnsEffect extends ReplacementEffectImpl {

    public StrangleholdSkipExtraTurnsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If an opponent would begin an extra turn, that player skips that turn instead";
    }

    public StrangleholdSkipExtraTurnsEffect(final StrangleholdSkipExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public StrangleholdSkipExtraTurnsEffect copy() {
        return new StrangleholdSkipExtraTurnsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getName() + " skipped");
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.EXTRA_TURN);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(event.getPlayerId(), game);
    }

}
