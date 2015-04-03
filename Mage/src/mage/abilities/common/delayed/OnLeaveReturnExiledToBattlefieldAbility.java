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
package mage.abilities.common.delayed;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * Returns the exiled cards/permanents as source leaves battlefield
 *
 * Uses no stack
 * @author LevelX2
 */

public class OnLeaveReturnExiledToBattlefieldAbility extends DelayedTriggeredAbility {

    public OnLeaveReturnExiledToBattlefieldAbility() {
        super(new ReturnExiledPermanentsEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public OnLeaveReturnExiledToBattlefieldAbility(final OnLeaveReturnExiledToBattlefieldAbility ability) {
        super(ability);
    }

    @Override
    public OnLeaveReturnExiledToBattlefieldAbility copy() {
        return new OnLeaveReturnExiledToBattlefieldAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class ReturnExiledPermanentsEffect extends OneShotEffect {

    public ReturnExiledPermanentsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled permanents";
    }

    public ReturnExiledPermanentsEffect(final ReturnExiledPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public ReturnExiledPermanentsEffect copy() {
        return new ReturnExiledPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() -1;
            UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter);
            if (exileZone != null) {
                ExileZone exile = game.getExile().getExileZone(exileZone);
                if (exile != null) {
                    LinkedList<UUID> cards = new LinkedList<>(exile);
                    for (UUID cardId : cards) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            Player owner = game.getPlayer(card.getOwnerId());
                            if (owner != null && owner.isInGame()) {
                                owner.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId());
                            }
                        }
                    }
                    exile.clear();
                }
                return true;
            }
        }
        return false;
    }
}
