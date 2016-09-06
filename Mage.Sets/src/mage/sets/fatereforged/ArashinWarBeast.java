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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class ArashinWarBeast extends CardImpl {

    public ArashinWarBeast(UUID ownerId) {
        super(ownerId, 123, "Arashin War Beast", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Beast");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Arashin War Beast deals combat damage to one or more blockers, manifest the top card of your library.
        this.addAbility(new ArashinWarBeastTriggeredAbility(new ManifestEffect(1), false));

    }

    public ArashinWarBeast(final ArashinWarBeast card) {
        super(card);
    }

    @Override
    public ArashinWarBeast copy() {
        return new ArashinWarBeast(this);
    }
}

class ArashinWarBeastTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("one or more blockers");
    
    static {
        filter.add(new BlockingPredicate());
    }
    
    boolean usedForCombatDamageStep;
            
    public ArashinWarBeastTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.usedForCombatDamageStep = false;
    }

    public ArashinWarBeastTriggeredAbility(final ArashinWarBeastTriggeredAbility ability) {
        super(ability);
        this.usedForCombatDamageStep = ability.usedForCombatDamageStep;
    }

    @Override
    public ArashinWarBeastTriggeredAbility copy() {
        return new ArashinWarBeastTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST ;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE && 
                event.getSourceId().equals(this.sourceId) && 
                ((DamagedCreatureEvent) event).isCombatDamage() &&
                !usedForCombatDamageStep) {
            Permanent creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (creature == null || !filter.match(creature, getSourceId(), getControllerId(), game)) {
                return false;
            }
            // trigger only once per combat damage step
            usedForCombatDamageStep = true;
            return true;
                    
        } 
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
        }        
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to one or more blockers, " + super.getRule();
    }
}
