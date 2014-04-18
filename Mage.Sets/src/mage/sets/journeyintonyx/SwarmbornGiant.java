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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class SwarmbornGiant extends CardImpl<SwarmbornGiant> {

    public SwarmbornGiant(UUID ownerId) {
        super(ownerId, 144, "Swarmborn Giant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Giant");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever you're dealt combat damage, sacrifice Swarmborn Giant.
        this.addAbility(new SwarmbornGiantTriggeredAbility());

        // {4}{G}{G}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{4}{G}{G}", 2));
        
        // As long as Swarmborn Giant is monstrous, it has reach.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinousEffect(new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.getInstance(),
                "As long as {this} is monstrous, it has reach"));
        this.addAbility(ability);
    }

    public SwarmbornGiant(final SwarmbornGiant card) {
        super(card);
    }

    @Override
    public SwarmbornGiant copy() {
        return new SwarmbornGiant(this);
    }
}

class SwarmbornGiantTriggeredAbility extends TriggeredAbilityImpl<SwarmbornGiantTriggeredAbility> {

    public SwarmbornGiantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
    }

    public SwarmbornGiantTriggeredAbility(final SwarmbornGiantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SwarmbornGiantTriggeredAbility copy() {
        return new SwarmbornGiantTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER) && event.getTargetId().equals(this.getControllerId())) {
            DamageEvent damageEvent = (DamageEvent) event;
            return damageEvent.isCombatDamage();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt combat damage, " + super.getRule();
    }
}
