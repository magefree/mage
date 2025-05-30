package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeedletoothPack extends CardImpl {

    public NeedletoothPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Morbid -- At the beginning of your end step, if a creature died this turn, put two +1/+1 counters on target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
        ).withInterveningIf(MorbidCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));
    }

    private NeedletoothPack(final NeedletoothPack card) {
        super(card);
    }

    @Override
    public NeedletoothPack copy() {
        return new NeedletoothPack(this);
    }
}
