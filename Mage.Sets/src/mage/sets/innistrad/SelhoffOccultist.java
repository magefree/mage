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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class SelhoffOccultist extends CardImpl<SelhoffOccultist> {

    public SelhoffOccultist(UUID ownerId) {
        super(ownerId, 73, "Selhoff Occultist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Selhoff Occultist or another creature dies, target player puts the top card of his or her library into his or her graveyard.
        this.addAbility(new SelhoffOccultistTriggeredAbility());
    }

    public SelhoffOccultist(final SelhoffOccultist card) {
        super(card);
    }

    @Override
    public SelhoffOccultist copy() {
        return new SelhoffOccultist(this);
    }
}

class SelhoffOccultistTriggeredAbility extends TriggeredAbilityImpl<SelhoffOccultistTriggeredAbility> {

    public SelhoffOccultistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(1), false);
        this.addTarget(new TargetPlayer());
    }

    public SelhoffOccultistTriggeredAbility(final SelhoffOccultistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SelhoffOccultistTriggeredAbility copy() {
        return new SelhoffOccultistTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
                if (permanent != null) {
                    if (permanent.getId().equals(this.getSourceId())) {
                        return true;
                    } else {
                        if (permanent.getCardType().contains(CardType.CREATURE)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature dies, target player puts the top card of his or her library into his or her graveyard.";
    }
}
