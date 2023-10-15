package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFifthDoctor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "creature you control that didn't attack or enter the battlefield this turn"
    );

    static {
        filter.add(Predicates.not(EnteredThisTurnPredicate.instance));
        filter.add(TheFifthDoctorPredicate.instance);
    }

    public TheFifthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Peaceful Coexistence -- At the beginning of your end step, put a +1/+1 counter on each creature you control that didn't attack or enter the battlefield this turn. Untap those creatures.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
                TargetController.YOU, false
        );
        ability.addEffect(new UntapAllEffect(filter).setText("Untap those creatures"));
        this.addAbility(ability.withFlavorWord("Peaceful Coexistence"), new AttackedThisTurnWatcher());
    }

    private TheFifthDoctor(final TheFifthDoctor card) {
        super(card);
    }

    @Override
    public TheFifthDoctor copy() {
        return new TheFifthDoctor(this);
    }
}

enum TheFifthDoctorPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return !game.getState().getWatcher(AttackedThisTurnWatcher.class).checkIfAttacked(input, game);
    }
}