/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class EnterBattlefieldPayCostOrPutGraveyardEffect extends ReplacementEffectImpl {

    private final Cost cost;

    public EnterBattlefieldPayCostOrPutGraveyardEffect(Cost cost) {
        super(Duration.WhileOnBattlefield, Outcome.PutCardInPlay);
        this.cost = cost;
        staticText = "If {this} would enter the battlefield, " + cost.getText() + " instead. If you do, put {this} onto the battlefield. If you don't, put it into its owner's graveyard";
    }

    public EnterBattlefieldPayCostOrPutGraveyardEffect(final EnterBattlefieldPayCostOrPutGraveyardEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public EnterBattlefieldPayCostOrPutGraveyardEffect copy() {
        return new EnterBattlefieldPayCostOrPutGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && cost != null && sourceObject != null){
            boolean replace = true;
            if (cost.canPay(source, source.getSourceId(), player.getId(), game)) {
                if (player.chooseUse(outcome, cost.getText() + "? (otherwise " + sourceObject.getLogName() + " is put into graveyard)", source, game)) {
                    cost.clearPaid();
                    replace = !cost.pay(source, game, source.getSourceId(), source.getControllerId(), false);
                }
            }
            if (replace) {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    player.moveCards(card, game.getState().getZone(event.getTargetId()), Zone.GRAVEYARD, source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.getSourceId().equals(event.getTargetId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if(zEvent.getToZone().equals(Zone.BATTLEFIELD)){
                return true;
            }
        }
        return false;
    }

}
