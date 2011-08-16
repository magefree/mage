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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author Loki, North
 */
public class MolderBeast extends CardImpl<MolderBeast> {

    public MolderBeast(UUID ownerId) {
        super(ownerId, 125, "Molder Beast", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new MolderBeastTriggeredAbility());
    }

    public MolderBeast(final MolderBeast card) {
        super(card);
    }

    @Override
    public MolderBeast copy() {
        return new MolderBeast(this);
    }
}

class MolderBeastTriggeredAbility extends TriggeredAbilityImpl<MolderBeastTriggeredAbility> {

    public MolderBeastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Constants.Duration.EndOfTurn), false);
    }

    public MolderBeastTriggeredAbility(final MolderBeastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD
                    && zEvent.getToZone() == Zone.GRAVEYARD
                    && zEvent.getTarget().getCardType().contains(CardType.ARTIFACT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact is put into a graveyard from the battlefield, {this} gets +2/+0 until end of turn.";
    }

    @Override
    public MolderBeastTriggeredAbility copy() {
        return new MolderBeastTriggeredAbility(this);
    }
}