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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class BloodhallPriest extends CardImpl {

    public BloodhallPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        this.subtype.add("Vampire");
        this.subtype.add("Cleric");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Bloodhall Priest enters the battlefield or attacks, if you have no cards in hand, Bloodhall Priest deals 2 damage to target creature or player.
        this.addAbility(new ConditionalTriggeredAbility(new BloodhallPriestAbility(), HellbentCondition.getInstance(), null));

        // Madness {1}{B}{R}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{B}{R}")));
    }

    public BloodhallPriest(final BloodhallPriest card) {
        super(card);
    }

    @Override
    public BloodhallPriest copy() {
        return new BloodhallPriest(this);
    }
}

class BloodhallPriestAbility extends TriggeredAbilityImpl {

    public BloodhallPriestAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
        this.addTarget(new TargetCreatureOrPlayer());
    }

    public BloodhallPriestAbility(final BloodhallPriestAbility ability) {
        super(ability);
    }

    @Override
    public BloodhallPriestAbility copy() {
        return new BloodhallPriestAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED || event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or attacks, if you have no cards in hand, {this} deals 2 damage to target creature or player.";
    }
}
