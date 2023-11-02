package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelDromedary extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public SteelDromedary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Steel Dromedary enters the battlefield tapped with two +1/+1 counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), "tapped with two +1/+1 counters on it"
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.addAbility(ability);

        // Steel Dromedary doesn't untap during your untap step if it has a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepSourceEffect(), condition
        ).setText("{this} doesn't untap during your untap step if it has a +1/+1 counter on it")));

        // At the beginning of combat on your turn, you may move a +1/+1 counter from Steel Dromedary onto target creature.
        ability = new BeginningOfCombatTriggeredAbility(
                new MoveCountersFromSourceToTargetEffect(), TargetController.YOU, true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SteelDromedary(final SteelDromedary card) {
        super(card);
    }

    @Override
    public SteelDromedary copy() {
        return new SteelDromedary(this);
    }
}
