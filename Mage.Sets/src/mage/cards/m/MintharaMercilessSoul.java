package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MintharaMercilessSoul extends CardImpl {

    public MintharaMercilessSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ward {X}, where X is the number of experience counters you have.
        Ability ability = new WardAbility(SourceControllerCountersCount.EXPERIENCE, "the number of experience counters you have");
        this.addAbility(ability);

        // At the beginning of your end step, if a permanent you controlled left the battlefield this turn, you get an experience counter.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU),
                TargetController.YOU, RevoltCondition.instance, false
        ), new RevoltWatcher());

        // Creatures you control get +1/+0 for each experience counter you have.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                SourceControllerCountersCount.EXPERIENCE,
                StaticValue.get(0),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURES,
                false
        )));
    }

    private MintharaMercilessSoul(final MintharaMercilessSoul card) {
        super(card);
    }

    @Override
    public MintharaMercilessSoul copy() {
        return new MintharaMercilessSoul(this);
    }
}