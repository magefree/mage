package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class MindbenderSpores extends CardImpl {

    public MindbenderSpores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Mindbender Spores blocks a creature, put four fungus counters on that creature.
        // The creature gains "This creature doesn't untap during your untap step if it has a fungus counter on it"
        // and "At the beginning of your upkeep, remove a fungus counter from this creature."
        Ability ability = new BlocksCreatureTriggeredAbility(new AddCountersTargetEffect(CounterType.FUNGUS.createInstance(4)));

        SimpleStaticAbility doesntUntapAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepSourceEffect(), new SourceHasCounterCondition(CounterType.FUNGUS))
                .setText("{this} doesn't untap during your untap step if it has a fungus counter on it"));
        ability.addEffect(new GainAbilityTargetEffect(doesntUntapAbility, Duration.Custom)
                .setText("The creature gains \"This creature doesn't untap during your untap step if it has a fungus counter on it\""));

        BeginningOfUpkeepTriggeredAbility removeCounterAbility = new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.FUNGUS.createInstance()), TargetController.YOU, false);
        ability.addEffect(new GainAbilityTargetEffect(removeCounterAbility, Duration.Custom)
                .concatBy("and")
                .setText("\"At the beginning of your upkeep, remove a fungus counter from this creature.\""));

        this.addAbility(ability);
    }

    private MindbenderSpores(final MindbenderSpores card) {
        super(card);
    }

    @Override
    public MindbenderSpores copy() {
        return new MindbenderSpores(this);
    }
}
