package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class MattMurdockJusticeSeeker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public MattMurdockJusticeSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may pay {1}. When you do, put a +1/+1 counter on target creature you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
            ability, new ManaCostsImpl<>("{1}"), "Pay {1}?"
        )));

        // Each creature you control with a counter on it has ward {1}.
        Ability wardAbility = new WardAbility(new GenericManaCost(1));
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(
                wardAbility, Duration.WhileOnBattlefield, filter
            ).setText("Each creature you control with a counter on it has " + wardAbility.getRule())
        ));

    }

    private MattMurdockJusticeSeeker(final MattMurdockJusticeSeeker card) {
        super(card);
    }

    @Override
    public MattMurdockJusticeSeeker copy() {
        return new MattMurdockJusticeSeeker(this);
    }
}
