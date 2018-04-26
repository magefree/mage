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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

/**
 *
 * @author TheElk801
 */
public class NabanDeanOfIteration extends CardImpl {

    public NabanDeanOfIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If a Wizard entering the battlefield under your control causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NabanDeanOfIterationEffect()));
    }

    public NabanDeanOfIteration(final NabanDeanOfIteration card) {
        super(card);
    }

    @Override
    public NabanDeanOfIteration copy() {
        return new NabanDeanOfIteration(this);
    }
}

class NabanDeanOfIterationEffect extends ReplacementEffectImpl {

    NabanDeanOfIterationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a Wizard entering the battlefield under your control causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time";
    }

    NabanDeanOfIterationEffect(final NabanDeanOfIterationEffect effect) {
        super(effect);
    }

    @Override
    public NabanDeanOfIterationEffect copy() {
        return new NabanDeanOfIterationEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Naban
            if (source.getControllerId().equals(event.getPlayerId())) {
                GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
                // Only EtB triggers
                if (sourceEvent != null
                        && sourceEvent.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                        && sourceEvent instanceof EntersTheBattlefieldEvent) {
                    EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
                    // Only for entering artifacts or creatures
                    if (entersTheBattlefieldEvent.getTarget().hasSubtype(SubType.WIZARD, game)) {
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
