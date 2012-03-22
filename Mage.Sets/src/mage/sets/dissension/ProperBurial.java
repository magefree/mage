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
package mage.sets.dissension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class ProperBurial extends CardImpl<ProperBurial> {

    public ProperBurial(UUID ownerId) {
        super(ownerId, 16, "Proper Burial", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "DIS";

        this.color.setWhite(true);

        // Whenever a creature you control dies, you gain life equal to that creature's toughness.
        this.addAbility(new ProperBurialTriggeredAbility());
    }

    public ProperBurial(final ProperBurial card) {
        super(card);
    }

    @Override
    public ProperBurial copy() {
        return new ProperBurial(this);
    }
}

class ProperBurialTriggeredAbility extends TriggeredAbilityImpl<ProperBurialTriggeredAbility> {

    public ProperBurialTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public ProperBurialTriggeredAbility(final ProperBurialTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ProperBurialTriggeredAbility copy() {
        return new ProperBurialTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.getControllerId().equals(this.getControllerId()) && permanent.getCardType().contains(CardType.CREATURE)) {
                this.getEffects().clear();
                this.addEffect(new GainLifeEffect(permanent.getToughness().getValue()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies, you gain life equal to that creature's toughness.";
    }
}
