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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author North
 */
public class AvacynsCollar extends CardImpl<AvacynsCollar> {

    public AvacynsCollar(UUID ownerId) {
        super(ownerId, 145, "Avacyn's Collar", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Equipment");

        // Equipped creature gets +1/+0 and has vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT)));
        // Whenever equipped creature dies, if it was a Human, put a 1/1 white Spirit creature token with flying onto the battlefield.
        this.addAbility(new AvacynsCollarTriggeredAbility());
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public AvacynsCollar(final AvacynsCollar card) {
        super(card);
    }

    @Override
    public AvacynsCollar copy() {
        return new AvacynsCollar(this);
    }
}

class AvacynsCollarTriggeredAbility extends TriggeredAbilityImpl<AvacynsCollarTriggeredAbility> {

    public AvacynsCollarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()));
    }

    public AvacynsCollarTriggeredAbility(final AvacynsCollarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvacynsCollarTriggeredAbility copy() {
        return new AvacynsCollarTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.getAttachments().contains(this.getSourceId()) && permanent.hasSubtype("Human")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature dies, if it was a Human, put a 1/1 white Spirit creature token with flying onto the battlefield.";
    }
}
