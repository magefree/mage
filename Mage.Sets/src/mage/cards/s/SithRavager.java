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

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class SithRavager extends CardImpl {

    public SithRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add("Human");
        this.subtype.add("Sith");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // <i>Hate</i> &mdash; Whenever an opponent loses life from a source other than combat damage, Sith Ravager gets +1/+0 and gains haste and trample until end of turn.
        this.addAbility(new LostNonCombatLifeTriggeredAbility());
    }

    public SithRavager(final SithRavager card) {
        super(card);
    }

    @Override
    public SithRavager copy() {
        return new SithRavager(this);
    }

    public static class LostNonCombatLifeTriggeredAbility extends TriggeredAbilityImpl {

        public LostNonCombatLifeTriggeredAbility() {
            super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), false);
            addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
            addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        }

        public LostNonCombatLifeTriggeredAbility(final LostNonCombatLifeTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public LostNonCombatLifeTriggeredAbility copy() {
            return new LostNonCombatLifeTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.LOST_LIFE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return !event.getFlag() && game.getOpponents(game.getControllerId(getSourceId())).contains(event.getPlayerId());
        }

        @Override
        public String getRule() {
            return "<i>Hate</i> &mdash; Whenever an opponent loses life from a source other than combat damage, {this} gains haste and trample until end of turn.";
        }

    }
}
