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

package mage.sets.alarareborn;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class NemesisOfReason extends CardImpl<NemesisOfReason> {

    public NemesisOfReason (UUID ownerId) {
        super(ownerId, 28, "Nemesis of Reason", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Leviathan");
        this.subtype.add("Horror");
		this.color.setBlue(true);
		this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);
        this.addAbility(new NemesisofReasonTriggeredAbility());
    }

    public NemesisOfReason (final NemesisOfReason card) {
        super(card);
    }

    @Override
    public NemesisOfReason copy() {
        return new NemesisOfReason(this);
    }
}

class NemesisofReasonTriggeredAbility extends TriggeredAbilityImpl<NemesisofReasonTriggeredAbility> {
    NemesisofReasonTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(10));
    }

    NemesisofReasonTriggeredAbility(final NemesisofReasonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NemesisofReasonTriggeredAbility copy() {
        return new NemesisofReasonTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId()) ) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
			return true;
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever Nemesis of Reason attacks, defending player puts the top ten cards of his or her library into his or her graveyard.";
    }
}