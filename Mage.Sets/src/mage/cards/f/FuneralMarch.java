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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class FuneralMarch extends CardImpl {

    public FuneralMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature leaves the battlefield, its controller sacrifices a creature.
        this.addAbility(new FuneralMarchTriggeredAbility());
    }

    public FuneralMarch(final FuneralMarch card) {
        super(card);
    }

    @Override
    public FuneralMarch copy() {
        return new FuneralMarch(this);
    }
}

class FuneralMarchTriggeredAbility extends ZoneChangeTriggeredAbility {

    public FuneralMarchTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "its controller"), "When enchanted creature leaves the battlefield, ", false);
    }

    public FuneralMarchTriggeredAbility(final FuneralMarchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FuneralMarchTriggeredAbility copy() {
        return new FuneralMarchTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if ((fromZone == null || zEvent.getFromZone() == fromZone) && (toZone == null || zEvent.getToZone() == toZone)) {
                for (Effect effect : getEffects()) {
                    if (zEvent.getTarget() != null) {
                        Permanent attachedTo = (Permanent) game.getLastKnownInformation(enchantment.getAttachedTo(), Zone.BATTLEFIELD, enchantment.getAttachedToZoneChangeCounter());
                        if (attachedTo != null) {
                            effect.setTargetPointer(new FixedTarget(attachedTo.getControllerId()));
                        }
                    }  
                }
                return true;
            }
        }
        return false;
    }
}
