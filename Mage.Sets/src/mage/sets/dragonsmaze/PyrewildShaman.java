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

package mage.sets.dragonsmaze;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */


public class PyrewildShaman extends CardImpl<PyrewildShaman> {

    public PyrewildShaman (UUID ownerId) {
        super(ownerId, 36, "Pyrewild Shaman", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");
        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bloodrush — {1}{R}, Discard Pyrewild Shaman: Target attacking creature gets +3/+1 until end of turn.
        this.addAbility(new BloodrushAbility("{1}{R}", new BoostTargetEffect(3,1,Duration.EndOfTurn)));

        // Whenever one or more creatures you control deal combat damage to a player, if Pyrewild Shaman is in your graveyard, you may pay {3}. If you do, return Pyrewild Shaman to your hand.
        this.addAbility(new PyrewildShamanTriggeredAbility());

    }

    public PyrewildShaman (final PyrewildShaman card) {
        super(card);
    }

    @Override
    public PyrewildShaman copy() {
        return new PyrewildShaman(this);
    }

}

class PyrewildShamanTriggeredAbility extends TriggeredAbilityImpl<PyrewildShamanTriggeredAbility> {

    List<UUID> damagedPlayerIds = new ArrayList<UUID>();

    public PyrewildShamanTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnToHandSourceEffect(), new ManaCostsImpl("{3}")), false);
    }

    public PyrewildShamanTriggeredAbility(final PyrewildShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PyrewildShamanTriggeredAbility copy() {
        return new PyrewildShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.getControllerId().equals(controllerId) && !damagedPlayerIds.contains(event.getTargetId())) {
                    damagedPlayerIds.add(event.getTargetId());
                    return true;
                }
            }
        }
        if (event.getType().equals(GameEvent.EventType.END_COMBAT_STEP_POST)){
            damagedPlayerIds.clear();
        }
        if (event.getType().equals(GameEvent.EventType.ZONE_CHANGE) && event.getTargetId().equals(getSourceId())){
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone().equals(Zone.GRAVEYARD)) {
                damagedPlayerIds.clear();
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player, if Pyrewild Shaman is in your graveyard, you may pay {3}. If you do, return Pyrewild Shaman to your hand.";
    }
}
