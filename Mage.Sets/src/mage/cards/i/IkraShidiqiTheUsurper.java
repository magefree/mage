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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public class IkraShidiqiTheUsurper extends CardImpl {

    public IkraShidiqiTheUsurper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Naga");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a creature you control deals combat damage to a player, you gain life equal to that creature's toughness.
        this.addAbility(new IkraShidiqiTheUsurperTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public IkraShidiqiTheUsurper(final IkraShidiqiTheUsurper card) {
        super(card);
    }

    @Override
    public IkraShidiqiTheUsurper copy() {
        return new IkraShidiqiTheUsurper(this);
    }
}

class IkraShidiqiTheUsurperTriggeredAbility extends TriggeredAbilityImpl {

    public IkraShidiqiTheUsurperTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public IkraShidiqiTheUsurperTriggeredAbility(final IkraShidiqiTheUsurperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IkraShidiqiTheUsurperTriggeredAbility copy() {
        return new IkraShidiqiTheUsurperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damageEvent = (DamagedEvent) event;
        if (damageEvent.isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isCreature() && permanent.getControllerId().equals(this.getControllerId())) {
                this.getEffects().clear();
                this.getEffects().add(new GainLifeEffect(permanent.getToughness().getValue()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player, you gain life equal to that creature's toughness";
    }
}
