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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author Styxo
 */
public class PrecipiceOfMortis extends CardImpl {

    public PrecipiceOfMortis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}{W}");

        // If a Jedi entering or leaving the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers additional time
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrecipiceOfMortisEffect()));

    }

    public PrecipiceOfMortis(final PrecipiceOfMortis card) {
        super(card);
    }

    @Override
    public PrecipiceOfMortis copy() {
        return new PrecipiceOfMortis(this);
    }
}

class PrecipiceOfMortisEffect extends ReplacementEffectImpl {

    public PrecipiceOfMortisEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a Jedi entering or leaving the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers additional time";
    }

    public PrecipiceOfMortisEffect(final PrecipiceOfMortisEffect effect) {
        super(effect);
    }

    @Override
    public PrecipiceOfMortisEffect copy() {
        return new PrecipiceOfMortisEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Precipice of Mortis
            if (source.getControllerId().equals(event.getPlayerId())) {
                GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
                // enters triggers
                if (sourceEvent.getType() == EventType.ENTERS_THE_BATTLEFIELD && sourceEvent instanceof EntersTheBattlefieldEvent) {
                    EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
                    // Only for entering Jedis
                    if (entersTheBattlefieldEvent.getTarget().getSubtype(game).contains(SubType.JEDI)) {
                        // Only for triggers of permanents
                        if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
                            return true;
                        }
                    }
                }
                // leaves triggers
                if (sourceEvent.getType() == EventType.ZONE_CHANGE && sourceEvent instanceof ZoneChangeEvent) {
                    ZoneChangeEvent leavesTheBattlefieldEvent = (ZoneChangeEvent) sourceEvent;
                    if (leavesTheBattlefieldEvent.getFromZone() == Zone.BATTLEFIELD) {
                        // Only for leaving Jedis
                        if (leavesTheBattlefieldEvent.getTarget().getSubtype(game).contains(SubType.JEDI)) {
                            // Only for triggers of permanents
                            if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
