package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PrimordialHydra extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, 10, Integer.MAX_VALUE);
    private static final String staticText = "{this} has trample as long as it has ten or more +1/+1 counters on it";

    public PrimordialHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Primordial Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // At the beginning of your upkeep, double the number of +1/+1 counters on Primordial Hydra.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoubleCountersSourceEffect(CounterType.P1P1), TargetController.YOU, false
        ));

        // Primordial Hydra has trample as long as it has ten or more +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()), condition, staticText
        )));
    }

    private PrimordialHydra(final PrimordialHydra card) {
        super(card);
    }

    @Override
    public PrimordialHydra copy() {
        return new PrimordialHydra(this);
    }
}
