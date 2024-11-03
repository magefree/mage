package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class InspiringPaladin extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control with +1/+1 counters on them");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }


    public InspiringPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // During your turn, this creature has first strike.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FirstStrikeAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance,
                        "During your turn, this creature has first strike."
                )
        ).addHint(MyTurnHint.instance));

        // During your turn, creatures you control with +1/+1 counters on them have first strike.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilityControlledEffect(
                                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
                        ), MyTurnCondition.instance,
                        "During your turn, creatures you control with +1/+1 counters on them have first strike."
                )
        ));
    }

    private InspiringPaladin(final InspiringPaladin card) {
        super(card);
    }

    @Override
    public InspiringPaladin copy() {
        return new InspiringPaladin(this);
    }
}
