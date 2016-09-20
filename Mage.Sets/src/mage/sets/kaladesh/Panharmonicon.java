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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.NumberOfTriggersEvent;

/**
 *
 * @author emerald000
 */
public class Panharmonicon extends CardImpl {

    public Panharmonicon(UUID ownerId) {
        super(ownerId, 226, "Panharmonicon", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "KLD";

        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PanharmoniconEffect()));
    }

    public Panharmonicon(final Panharmonicon card) {
        super(card);
    }

    @Override
    public Panharmonicon copy() {
        return new Panharmonicon(this);
    }
}

class PanharmoniconEffect extends ReplacementEffectImpl {

    PanharmoniconEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time";
    }

    PanharmoniconEffect(final PanharmoniconEffect effect) {
        super(effect);
    }

    @Override
    public PanharmoniconEffect copy() {
        return new PanharmoniconEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Panharmonicon
            if (source.getControllerId().equals(event.getPlayerId())) {
                GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
                // Only EtB triggers
                if (sourceEvent.getType() == EventType.ENTERS_THE_BATTLEFIELD && sourceEvent instanceof EntersTheBattlefieldEvent) {
                    EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
                    // Only for entering artifacts or creatures
                    if (entersTheBattlefieldEvent.getTarget().getCardType().contains(CardType.ARTIFACT)
                            || entersTheBattlefieldEvent.getTarget().getCardType().contains(CardType.CREATURE)) {
                        // Only for triggers of permanents
                        if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
                            return true;
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