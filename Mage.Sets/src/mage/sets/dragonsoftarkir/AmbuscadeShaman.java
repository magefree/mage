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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AmbuscadeShaman extends CardImpl {

    public AmbuscadeShaman(UUID ownerId) {
        super(ownerId, 87, "Ambuscade Shaman", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Orc");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Ambuscade Shaman or another creature enters the battlefield under your control, that creature gets +2/+2 until end of turn.
        Effect effect = new BoostTargetEffect(2,2, Duration.EndOfTurn);
        effect.setText("that creature gets +2/+2 until end of turn");
        this.addAbility(new AmbuscadeShamanTriggeredAbility(effect));
        
        // Dash {3}{B} <i>(You may cast this spell for its dash cost. If you do, it gains haste, and it's returned from the battlefield to its owner's hand at the beginning of the next end step.)</i>);
        this.addAbility(new DashAbility(this, "{3}{B}"));

    }

    public AmbuscadeShaman(final AmbuscadeShaman card) {
        super(card);
    }

    @Override
    public AmbuscadeShaman copy() {
        return new AmbuscadeShaman(this);
    }
}

class AmbuscadeShamanTriggeredAbility extends TriggeredAbilityImpl {

    AmbuscadeShamanTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    AmbuscadeShamanTriggeredAbility(final AmbuscadeShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AmbuscadeShamanTriggeredAbility copy() {
        return new AmbuscadeShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent.getControllerId().equals(this.controllerId)
                && permanent.getCardType().contains(CardType.CREATURE)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature enters the battlefield under your control, that creature gets +2/+2 until end of turn.";
    }
}