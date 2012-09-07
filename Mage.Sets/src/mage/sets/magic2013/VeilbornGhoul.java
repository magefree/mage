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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class VeilbornGhoul extends CardImpl<VeilbornGhoul> {

    public VeilbornGhoul(UUID ownerId) {
        super(ownerId, 114, "Veilborn Ghoul", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "M13";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Veilborn Ghoul can't block.
        this.addAbility(new CantBlockAbility());
        
        // Whenever a Swamp enters the battlefield under your control, you may return Veilborn Ghoul from your graveyard to your hand.
        this.addAbility(new VeilbornGhoulTriggeredAbility());
    }

    public VeilbornGhoul(final VeilbornGhoul card) {
        super(card);
    }

    @Override
    public VeilbornGhoul copy() {
        return new VeilbornGhoul(this);
    }
}

class VeilbornGhoulTriggeredAbility extends TriggeredAbilityImpl<VeilbornGhoulTriggeredAbility> {

    VeilbornGhoulTriggeredAbility() {
        super(Constants.Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), true);
    }

    VeilbornGhoulTriggeredAbility(VeilbornGhoulTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Constants.Zone.BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(CardType.LAND) && permanent.getControllerId().equals(this.controllerId)) {
                if(permanent.hasSubtype("Swamp")){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public VeilbornGhoulTriggeredAbility copy() {
        return new VeilbornGhoulTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a Swamp enters the battlefield under your control, you may return Veilborn Ghoul from your graveyard to your hand.";
    }
}
