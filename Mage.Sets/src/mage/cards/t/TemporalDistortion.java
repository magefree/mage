package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class TemporalDistortion extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a creature or land");
    private static final FilterPermanent filter2 = new FilterPermanent("permanents with hourglass counters on them");

    static {
        filter.add(Predicates.or(CardType.LAND.getPredicate(), CardType.CREATURE.getPredicate()));
        filter2.add(CounterType.HOURGLASS.getPredicate());
    }

    public TemporalDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever a creature or land becomes tapped, put an hourglass counter on it.
        Effect effect = new AddCountersTargetEffect(CounterType.HOURGLASS.createInstance());
        effect.setText("put an hourglass counter on it");
        this.addAbility(new BecomesTappedTriggeredAbility(effect, false, filter, true));

        // Permanents with hourglass counters on them don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter2)));

        // At the beginning of each player's upkeep, remove all hourglass counters from permanents that player controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TemporalDistortionRemovalEffect(), TargetController.ANY, false));
    }

    private TemporalDistortion(final TemporalDistortion card) {
        super(card);
    }

    @Override
    public TemporalDistortion copy() {
        return new TemporalDistortion(this);
    }
}

class TemporalDistortionRemovalEffect extends OneShotEffect {

    TemporalDistortionRemovalEffect() {
        super(Outcome.Neutral);
        staticText = "remove all hourglass counters from permanents that player controls";
    }

    private TemporalDistortionRemovalEffect(final TemporalDistortionRemovalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(game.getActivePlayerId())) {
            permanent.removeCounters(CounterType.HOURGLASS.createInstance(permanent.getCounters(game).getCount(CounterType.HOURGLASS)), source, game);
        }
        return true;
    }

    @Override
    public TemporalDistortionRemovalEffect copy() {
        return new TemporalDistortionRemovalEffect(this);
    }
}
