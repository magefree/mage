package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvolvedSpinoderm extends CardImpl {

    private static final Condition condition1 = new SourceHasCounterCondition(CounterType.OIL, 3);
    private static final Condition condition2 = new SourceHasCounterCondition(CounterType.OIL, 0, 0);

    public EvolvedSpinoderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Evolved Spinoderm enters the battlefield with four oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(4)),
                "with four oil counters on it"
        ));

        // Evolved Spinoderm has trample as long as it was two or fewer oil counters on it, Otherwise, it has hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                condition1, "{this} has trample as long as it has two " +
                "or fewer oil counters on it. Otherwise, it has hexproof"
        )));

        // At the beginning of your upkeep, remove an oil counter from Evolved Spinoderm. Then if it has no oil counters on it, sacrifice it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.OIL.createInstance()),
                TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), condition2,
                "Then if it has no oil counters on it, sacrifice it"
        ));
        this.addAbility(ability);
    }

    private EvolvedSpinoderm(final EvolvedSpinoderm card) {
        super(card);
    }

    @Override
    public EvolvedSpinoderm copy() {
        return new EvolvedSpinoderm(this);
    }
}
