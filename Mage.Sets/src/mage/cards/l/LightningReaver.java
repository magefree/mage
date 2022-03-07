package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LightningReaver extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.CHARGE);

    public LightningReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste; fear
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(FearAbility.getInstance());

        // Whenever Lightning Reaver deals combat damage to a player, put a charge counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()).setText("put a charge counter on it"), false));

        // At the beginning of your end step, Lightning Reaver deals damage equal to the number of charge counters on it to each opponent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DamagePlayersEffect(
                Outcome.Damage, xValue, TargetController.OPPONENT
        ).setText("{this} deals damage equal to the number of charge counters on it to each opponent"), TargetController.YOU, false));
    }

    private LightningReaver(final LightningReaver card) {
        super(card);
    }

    @Override
    public LightningReaver copy() {
        return new LightningReaver(this);
    }
}
