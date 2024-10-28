package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Grath
 */
public final class DionusElvishArchdruid extends CardImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.ELF, "Elves");

    public DionusElvishArchdruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Elves you control have "Whenever this creature becomes tapped during your turn, untap it and put a +1/+1 counter on it. This ability triggers only once each turn."
        Ability untapAndGrow = new BecomesTappedSourceTriggeredAbility(new UntapSourceEffect()).setTriggersLimitEachTurn(1);
        untapAndGrow.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(untapAndGrow,
                Duration.WhileOnBattlefield, filter, false)));
    }

    private DionusElvishArchdruid(final DionusElvishArchdruid card) {
        super(card);
    }

    @Override
    public DionusElvishArchdruid copy() {
        return new DionusElvishArchdruid(this);
    }
}
