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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public class CrazedArmodon extends CardImpl {

    public CrazedArmodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add("Elephant");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {G}: Crazed Armodon gets +3/+0 and gains trample until end of turn. Destroy Crazed Armodon at the beginning of the next end step. Activate this ability only once each turn.
        Effect effect = new BoostSourceEffect(3, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +3/+0");
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{G}"));
        effect= new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CrazedArmodonDelayedTriggeredAbility()));
        this.addAbility(ability);
    }

    public CrazedArmodon(final CrazedArmodon card) {
        super(card);
    }

    @Override
    public CrazedArmodon copy() {
        return new CrazedArmodon(this);
    }

    class CrazedArmodonDelayedTriggeredAbility extends DelayedTriggeredAbility {

        public CrazedArmodonDelayedTriggeredAbility() {
            super(new DestroySourceEffect());
        }

        public CrazedArmodonDelayedTriggeredAbility(final CrazedArmodonDelayedTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public CrazedArmodonDelayedTriggeredAbility copy() {
            return new CrazedArmodonDelayedTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.END_TURN_STEP_PRE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return true;
        }

        @Override
        public String getRule() {
            return "Destroy {this} at the beginning of the next end step";
        }
    }
}
