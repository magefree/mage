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

package mage.sets.eventide;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class AshlingtheExtinguisher extends CardImpl<AshlingtheExtinguisher> {

    public AshlingtheExtinguisher (UUID ownerId) {
        super(ownerId, 33, "Ashling, the Extinguisher", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "EVE";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");
		this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new AshlingtheExtinguisherTriggeredAbility());
    }

    public AshlingtheExtinguisher (final AshlingtheExtinguisher card) {
        super(card);
    }

    @Override
    public AshlingtheExtinguisher copy() {
        return new AshlingtheExtinguisher(this);
    }

}

class AshlingtheExtinguisherTriggeredAbility extends TriggeredAbilityImpl<AshlingtheExtinguisherTriggeredAbility> {
    public AshlingtheExtinguisherTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeTargetEffect());
        this.addTarget(new TargetCreaturePermanent());
    }

    public AshlingtheExtinguisherTriggeredAbility(final AshlingtheExtinguisherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AshlingtheExtinguisherTriggeredAbility copy() {
        return new AshlingtheExtinguisherTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
			    return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Ashling, the Extinguisher deals combat damage to a player, choose target creature that player controls. He or she sacrifices that creature.";
    }
}