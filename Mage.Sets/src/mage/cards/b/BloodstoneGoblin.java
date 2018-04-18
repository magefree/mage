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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public class BloodstoneGoblin extends CardImpl {

    public BloodstoneGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a spell, if that spell was kicked, Bloodstone Goblin gets +1/+1 and gains menace until end of turn.
        this.addAbility(new BloodstoneGoblinTriggeredAbility());
    }

    public BloodstoneGoblin(final BloodstoneGoblin card) {
        super(card);
    }

    @Override
    public BloodstoneGoblin copy() {
        return new BloodstoneGoblin(this);
    }
}

class BloodstoneGoblinTriggeredAbility extends TriggeredAbilityImpl {

    BloodstoneGoblinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("{this} gets +1/+1"), true);
        this.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn).setText("and gains menace until end of turn"));
    }

    BloodstoneGoblinTriggeredAbility(final BloodstoneGoblinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodstoneGoblinTriggeredAbility copy() {
        return new BloodstoneGoblinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getControllerId().equals(controllerId)) {
            for (Ability ability : spell.getAbilities()) {
                if (ability instanceof KickerAbility && ((KickerAbility) ability).getKickedCounter(game, spell.getSpellAbility()) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, if it was kicked," + super.getRule();
    }
}
