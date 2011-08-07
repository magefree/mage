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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author North
 */
public class SoulsAttendant extends CardImpl<SoulsAttendant> {

    public SoulsAttendant(UUID ownerId) {
        super(ownerId, 44, "Soul's Attendant", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new AnotherCreatureEntersBattlefieldTriggeredAbility(new GainLifeEffect(1), true));
    }

    public SoulsAttendant(final SoulsAttendant card) {
        super(card);
    }

    @Override
    public SoulsAttendant copy() {
        return new SoulsAttendant(this);
    }
}

class AnotherCreatureEntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl<AnotherCreatureEntersBattlefieldTriggeredAbility> {

    public AnotherCreatureEntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AnotherCreatureEntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public AnotherCreatureEntersBattlefieldTriggeredAbility(AnotherCreatureEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().getCardType().contains(CardType.CREATURE)
                    && zEvent.getTargetId() != this.getSourceId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield, " + super.getRule();
    }

    @Override
    public AnotherCreatureEntersBattlefieldTriggeredAbility copy() {
        return new AnotherCreatureEntersBattlefieldTriggeredAbility(this);
    }
}