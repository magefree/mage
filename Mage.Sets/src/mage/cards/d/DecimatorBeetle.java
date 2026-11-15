package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class DecimatorBeetle extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public DecimatorBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Decimator Beetle enters the battlefield, put a -1/-1 counter on target creature you control.
        Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever Decimator Beetle attacks, remove a -1/-1 counter from target creature you control and put a -1/-1 counter on up to one target creature defending player controls.
        ability = new AttacksTriggeredAbility(
                new RemoveCounterTargetEffect(CounterType.M1M1.createInstance()), false);
        ability.addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance())
                .setTargetPointer(new SecondTargetPointer())
                .concatBy("and"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private DecimatorBeetle(final DecimatorBeetle card) {
        super(card);
    }

    @Override
    public DecimatorBeetle copy() {
        return new DecimatorBeetle(this);
    }
}
