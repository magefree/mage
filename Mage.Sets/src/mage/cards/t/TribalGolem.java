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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public class TribalGolem extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("a Beast");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("a Goblin");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent("a Soldier");
    private static final FilterControlledPermanent filter4 = new FilterControlledPermanent("a Wizard");
    private static final FilterControlledPermanent filter5 = new FilterControlledPermanent("a Zombie");

    static {
        filter1.add(new SubtypePredicate(SubType.BEAST));
        filter2.add(new SubtypePredicate(SubType.GOBLIN));
        filter3.add(new SubtypePredicate(SubType.SOLDIER));
        filter4.add(new SubtypePredicate(SubType.WIZARD));
        filter5.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public TribalGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Tribal Golem has trample as long as you control a Beast, haste as long as you control a Goblin, first strike as long as you control a Soldier, flying as long as you control a Wizard, and "{B}: Regenerate Tribal Golem" as long as you control a Zombie.
        Effect effect1 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter1),
                "{this} has trample as long as you control a Beast,"
        );
        Effect effect2 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter2),
                "haste as long as you control a Goblin,"
        );
        Effect effect3 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter3),
                "first strike as long as you control a Soldier,"
        );
        Effect effect4 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter4),
                "flying as long as you control a Wizard,"
        );
        Effect effect5 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new SimpleActivatedAbility(
                        Zone.BATTLEFIELD,
                        new RegenerateSourceEffect(),
                        new ManaCostsImpl("{B}")
                )),
                new PermanentsOnTheBattlefieldCondition(filter5),
                "and \"{B}: Regenerate {this}\" as long as you control a Zombie"
        );
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        ability.addEffect(effect3);
        ability.addEffect(effect4);
        ability.addEffect(effect5);
        this.addAbility(ability);
    }

    public TribalGolem(final TribalGolem card) {
        super(card);
    }

    @Override
    public TribalGolem copy() {
        return new TribalGolem(this);
    }
}
