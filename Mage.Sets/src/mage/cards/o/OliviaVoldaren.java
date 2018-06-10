
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class OliviaVoldaren extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    private static final FilterCreaturePermanent vampireFilter = new FilterCreaturePermanent("Vampire");

    static {
        filter.add(new AnotherPredicate());
        vampireFilter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public OliviaVoldaren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        String rule = "Gain control of target Vampire for as long as you control {this}";

        FilterPermanent filter2 = new FilterPermanent();
        filter2.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new CardIdPredicate(this.getId()));

        this.addAbility(FlyingAbility.getInstance());

        // {1}{R}: Olivia Voldaren deals 1 damage to another target creature. That creature becomes a Vampire in addition to its other types. Put a +1/+1 counter on Olivia Voldaren.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{1}{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        Effect effect = new AddCardSubTypeTargetEffect(SubType.VAMPIRE, Duration.WhileOnBattlefield);
        effect.setText("That creature becomes a Vampire in addition to its other types");
        ability.addEffect(effect);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);

        // {3}{B}{B}: Gain control of target Vampire for as long as you control Olivia Voldaren.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), new PermanentsOnTheBattlefieldCondition(filter2), rule),
                new ManaCostsImpl("{3}{B}{B}"));
        ability2.addTarget(new TargetCreaturePermanent(vampireFilter));
        this.addAbility(ability2);
    }

    public OliviaVoldaren(final OliviaVoldaren card) {
        super(card);
    }

    @Override
    public OliviaVoldaren copy() {
        return new OliviaVoldaren(this);
    }
}
