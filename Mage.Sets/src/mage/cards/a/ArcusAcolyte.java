package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcusAcolyte extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control without a +1/+1 counter on it");

    static {
        filter.add(Predicates.not(CounterType.P1P1.getPredicate()));
    }

    public ArcusAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Outlast {G/W}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{G/W}")));

        // Each other creature you control without a +1/+1 counter on it has outlast {G/W}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new OutlastAbility(new ManaCostsImpl<>("{G/W}")),
                Duration.WhileOnBattlefield, filter, true
        ).setText("each other creature you control without a +1/+1 counter on it has outlast {G/W}")));
    }

    private ArcusAcolyte(final ArcusAcolyte card) {
        super(card);
    }

    @Override
    public ArcusAcolyte copy() {
        return new ArcusAcolyte(this);
    }
}
