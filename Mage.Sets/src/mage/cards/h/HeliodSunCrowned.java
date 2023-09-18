package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeliodSunCrowned extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("creature or enchantment you control");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter2.add(AnotherPredicate.instance);
    }

    public HeliodSunCrowned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to white is less than five, Heliod isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.W, 5))
                .addHint(DevotionCount.W.getHint()));

        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        Ability ability = new GainLifeControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {1}{W}: Another target creature gains lifelink until end of turn.
        ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private HeliodSunCrowned(final HeliodSunCrowned card) {
        super(card);
    }

    @Override
    public HeliodSunCrowned copy() {
        return new HeliodSunCrowned(this);
    }
}
