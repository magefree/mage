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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class WardenOfTheFirstTree extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    
    static {
        filter.add(new SubtypePredicate("Warrior"));
        filter2.add(new SubtypePredicate("Spirit"));        
    }

    public WardenOfTheFirstTree(UUID ownerId) {
        super(ownerId, 143, "Warden of the First Tree", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new WardenOfTheFirstTree1(), "", Duration.Custom),
                new ManaCostsImpl("{1}{W/B}")));

        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BecomesCreatureSourceEffect(new WardenOfTheFirstTree2(), "", Duration.Custom),
                    new LockedInCondition(new SourceMatchesFilterCondition(filter)),
                    "If {this} is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink"),
                new ManaCostsImpl("{2}{W/B}{W/B}")
                ));

        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalOneShotEffect(
                    new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                    new SourceMatchesFilterCondition(filter2),
                    "If {this} is a Spirit, put five +1/+1 counters on it"),
                new ManaCostsImpl("{3}{W/B}{W/B}{W/B}")
                ));
    }

    public WardenOfTheFirstTree(final WardenOfTheFirstTree card) {
        super(card);
    }

    @Override
    public WardenOfTheFirstTree copy() {
        return new WardenOfTheFirstTree(this);
    }
}

class WardenOfTheFirstTree1 extends Token {

    public WardenOfTheFirstTree1() {
        super("Warden of the First Tree", "Human Warrior with base power and toughness 3/3");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
}

class WardenOfTheFirstTree2 extends Token {

    public WardenOfTheFirstTree2() {
        super("Warden of the First Tree", "Human Spirit Warrior with trample and lifelink");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Human");
        this.subtype.add("Spirit");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }
}
