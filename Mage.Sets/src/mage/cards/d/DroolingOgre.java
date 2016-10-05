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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wetterlicht
 */
public class DroolingOgre extends CardImpl {

    public DroolingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add("Ogre");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts an artifact spell, that player gains control of Drooling Ogre.
        this.addAbility(new DroolingOgreTriggeredAbility());
    }

    public DroolingOgre(final DroolingOgre card) {
        super(card);
    }

    @Override
    public DroolingOgre copy() {
        return new DroolingOgre(this);
    }

    private static class DroolingOgreEffect extends OneShotEffect {

        public DroolingOgreEffect() {
            super(Outcome.GainControl);
            this.staticText = "that player gains control of {this}";
        }

        private DroolingOgreEffect(DroolingOgreEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            Player newController = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (newController != null && controller != null && !controller.equals(newController)) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
                effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                game.addEffect(effect, source);
                return true;
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new DroolingOgreEffect(this);
        }

    }

    class DroolingOgreTriggeredAbility extends TriggeredAbilityImpl {

        public DroolingOgreTriggeredAbility() {
            super(Zone.BATTLEFIELD, new DroolingOgreEffect(), false);
        }

        public DroolingOgreTriggeredAbility(final DroolingOgreTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public DroolingOgreTriggeredAbility copy() {
            return new DroolingOgreTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.SPELL_CAST;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getCardType().contains(CardType.ARTIFACT)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a player casts an artifact spell, that player gains control of {this}.";
        }
    }
}
