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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author Quercitron
 */
public class GoblinRabblemaster extends CardImpl {

    private static final FilterCreaturePermanent otherGoblinFilter = new FilterCreaturePermanent("Goblin", "Other Goblin creatures you control");
    private static final FilterCreaturePermanent attackingFilter = new FilterCreaturePermanent("Goblin", "other attacking Goblin");
    
    static {
        otherGoblinFilter.add(new AnotherPredicate());
        otherGoblinFilter.add(new ControllerPredicate(TargetController.YOU));
        
        attackingFilter.add(new AttackingPredicate());
        attackingFilter.add(new AnotherPredicate());
    }
    
    public GoblinRabblemaster(UUID ownerId) {
        super(ownerId, 145, "Goblin Rabblemaster", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "M15";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Goblin creatures you control attack each turn if able.
        Effect effect = new AttacksIfAbleAllEffect(otherGoblinFilter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // At the beginning of combat on your turn, put a 1/1 red Goblin creature token with haste onto the battlefield.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new GoblinToken()), TargetController.YOU, false));
        
        // When Goblin Rabblemaster attacks, it gets +1/+0 until end of turn for each other attacking Goblin.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(attackingFilter), new StaticValue(0), Duration.EndOfTurn, true), false));
    }

    public GoblinRabblemaster(final GoblinRabblemaster card) {
        super(card);
    }

    @Override
    public GoblinRabblemaster copy() {
        return new GoblinRabblemaster(this);
    }
}

class GoblinToken extends Token {

    public GoblinToken() {
        super("Goblin", "1/1 red Goblin creature token with haste");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        subtype.add("Goblin");
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }
    
}
