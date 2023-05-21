package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfMuraganda extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public InvasionOfMuraganda(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{4}{G}",
                "Primordial Plasm",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.OOZE}, "G"
        );
        this.getLeftHalfCard().setStartingDefense(6);
        this.getRightHalfCard().setPT(4, 4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Muraganda enters the battlefield, put a +1/+1 counter on target creature you control. Then that creature fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new FightTargetsEffect()
                .setText("Then that creature fights up to one target creature you don't control"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getLeftHalfCard().addAbility(ability);

        // Primordial Plasm
        // At the beginning of combat on your turn, another target creature gets +2/+2 and loses all abilities until end of turn.
        ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 2)
                        .setText("another target creature gets +2/+2"),
                TargetController.YOU, false
        );
        ability.addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                .setText("and loses all abilities until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private InvasionOfMuraganda(final InvasionOfMuraganda card) {
        super(card);
    }

    @Override
    public InvasionOfMuraganda copy() {
        return new InvasionOfMuraganda(this);
    }
}
