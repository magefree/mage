package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfMoag extends TransformingDoubleFacedCard {

    public InvasionOfMoag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{G}{W}",
                "Bloomwielder Dryads",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRYAD}, "GW"
        );

        // Invasion of Moag
        this.getLeftHalfCard().setStartingDefense(5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Moag enters the battlefield, put a +1/+1 counter on each creature you control.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));

        // Bloomwielder Dryads
        this.getRightHalfCard().setPT(3, 3);

        // Ward {2}
        this.getRightHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of your end step, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getRightHalfCard().addAbility(ability);
    }

    private InvasionOfMoag(final InvasionOfMoag card) {
        super(card);
    }

    @Override
    public InvasionOfMoag copy() {
        return new InvasionOfMoag(this);
    }
}
