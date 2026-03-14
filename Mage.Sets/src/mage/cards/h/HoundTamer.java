package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HoundTamer extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent("Wolves and Werewolves");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public HoundTamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{G}",
                "Untamed Pup",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Hound Tamer
        this.getLeftHalfCard().setPT(3, 3);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // {3}{G}: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{G}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Untamed Pup
        this.getRightHalfCard().setPT(4, 4);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Other Wolves and Werewolves you control have trample.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // {3}{G}: Put a +1/+1 counter on target creature.
        Ability backAbility = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{G}")
        );
        backAbility.addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().addAbility(backAbility);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private HoundTamer(final HoundTamer card) {
        super(card);
    }

    @Override
    public HoundTamer copy() {
        return new HoundTamer(this);
    }
}
