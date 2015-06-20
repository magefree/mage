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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnTransformedSourceEffect;
import mage.abilities.effects.common.ExileAndReturnTransformedSourceEffect.Gender;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisCombatWatcher;

/**
 *
 * @author LevelX2
 */
public class KytheonHeroOfAkros extends CardImpl {

    public KytheonHeroOfAkros(UUID ownerId) {
        super(ownerId, 23, "Kytheon, Hero of Akros", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ORI";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        
        this.canTransform = true;
        this.secondSideCard = new GideonBattleForged(ownerId);

        // At end of combat, if Kytheon, Hero of Akros and at least two other creatures attacked this combat, exile Kytheon, 
        // then return him to the battlefield transformed under his owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalTriggeredAbility(new EndOfCombatTriggeredAbility(new ExileAndReturnTransformedSourceEffect(Gender.MALE), false), 
                new KytheonHeroOfAkrosCondition(), "At end of combat, if {this} and at least two other creatures attacked this combat, exile {this}, " +
                        "then return him to the battlefield transformed under his owner's control."), new AttackedThisCombatWatcher());
        
        // {2}{W}: Kytheon gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{W}")));
        
    }

    public KytheonHeroOfAkros(final KytheonHeroOfAkros card) {
        super(card);
    }

    @Override
    public KytheonHeroOfAkros copy() {
        return new KytheonHeroOfAkros(this);
    }
}

class KytheonHeroOfAkrosCondition implements Condition {
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            AttackedThisCombatWatcher watcher = (AttackedThisCombatWatcher) game.getState().getWatchers().get("AttackedThisCombat");
            if (watcher != null) {
                boolean sourceFound = false;
                int number = 0;
                for (MageObjectReference mor: watcher.getAttackedThisTurnCreatures()) {
                    if (mor.refersTo(sourceObject, game)) {
                        sourceFound = true;
                    } else {
                        number++;
                    }
                }
                return sourceFound && number >= 2;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if {this} and at least two other creatures attacked this combat";
    }
}
