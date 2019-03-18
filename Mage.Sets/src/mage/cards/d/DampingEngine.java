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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class DampingEngine extends CardImpl {

    public DampingEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // A player who controls more permanents than each other player can't play lands or cast artifact, creature, or enchantment spells. That player may sacrifice a permanent for that player to ignore this effect until end of turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DampingEngineEffect()));
        this.addAbility(new DampingEngineSpecialAction());

    }

    public DampingEngine(final DampingEngine card) {
        super(card);
    }

    @Override
    public DampingEngine copy() {
        return new DampingEngine(this);
    }
}

class DampingEngineEffect extends ContinuousRuleModifyingEffectImpl {

    public DampingEngineEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "A player who controls more permanents than each other player can't play lands or cast artifact, creature, or enchantment spells"
                + "That player may sacrifice a permanent for that player to ignore this effect until end of turn.<br><br>";
    }

    public DampingEngineEffect(final DampingEngineEffect effect) {
        super(effect);
    }

    @Override
    public DampingEngineEffect copy() {
        return new DampingEngineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't play the land or cast the spell (" + mageObject.getName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND
                || event.getType() == GameEvent.EventType.CAST_SPELL;

    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Permanent dampingEngine = game.getPermanent(source.getSourceId());
        final Card card = game.getCard(event.getSourceId());
        if (player != null || card != null) {
            // check type of spell
            if (card.isCreature()
                    || card.isArtifact()
                    || card.isEnchantment()
                    || card.isLand()) {
                // check to see if the player has more permanents
                if (new ControlsMorePermanentsThanEachOtherPlayer(player).apply(game, source)) {
                    // check to see if the player choose to ignore the effect
                    return game.getState().getValue("ignoreEffect") == null
                            || dampingEngine == null
                            || !game.getState().getValue("ignoreEffect").equals
                            (dampingEngine.getId() + "ignoreEffect" + game.getState().getPriorityPlayerId() + game.getState().getTurnNum());
                }
            }
        }
        return false;
    }
}

class DampingEngineSpecialAction extends SpecialAction {

    public DampingEngineSpecialAction() {
        super(Zone.BATTLEFIELD);
        this.addCost(new SacrificeTargetCost(new TargetControlledPermanent(), true));
        this.addEffect(new DampingEngineIgnoreEffect());
        this.setMayActivate(TargetController.ANY);
    }

    public DampingEngineSpecialAction(final DampingEngineSpecialAction ability) {
        super(ability);
    }

    @Override
    public DampingEngineSpecialAction copy() {
        return new DampingEngineSpecialAction(this);
    }
}

class DampingEngineIgnoreEffect extends OneShotEffect {

    public DampingEngineIgnoreEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "That player may sacrifice a permanent for that player to ignore this effect until end of turn";
    }

    public DampingEngineIgnoreEffect(final DampingEngineIgnoreEffect effect) {
        super(effect);
    }

    @Override
    public DampingEngineIgnoreEffect copy() {
        return new DampingEngineIgnoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        String key = permanent.getId() + "ignoreEffect" + game.getState().getPriorityPlayerId() + game.getState().getTurnNum();
        if (key != null) {
            game.getState().setValue("ignoreEffect", key);
        }
        return true;
    }
}

class ControlsMorePermanentsThanEachOtherPlayer implements Condition {

    Player player;

    public ControlsMorePermanentsThanEachOtherPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numPermanents = game.getBattlefield().countAll(new FilterPermanent(), player.getId(), game);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (numPermanents > game.getBattlefield().countAll(new FilterPermanent(), playerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "a player controls less permanents than you";
    }
}
