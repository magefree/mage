package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkatewingSpy extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public SkatewingSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {5}{U}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{5}{U}"));

        // Each creature you control with a +1/+1 counter on it has flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        FlyingAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        filter, "Each creature you control " +
                        "with a +1/+1 counter on it has flying"
                )
        ));
    }

    private SkatewingSpy(final SkatewingSpy card) {
        super(card);
    }

    @Override
    public SkatewingSpy copy() {
        return new SkatewingSpy(this);
    }
}
