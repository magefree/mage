package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TawnossTinkering extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("artifact, creature, or land you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public TawnossTinkering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Put two +1/+1 counters on target artifact, creature, or land you control. Untap that permanent. If it isn't a creature, it becomes a 0/0 creature in addition to its other types.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new UntapTargetEffect("Untap that permanent"));
        this.getSpellAbility().addEffect(new TawnossTinkeringEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private TawnossTinkering(final TawnossTinkering card) {
        super(card);
    }

    @Override
    public TawnossTinkering copy() {
        return new TawnossTinkering(this);
    }
}

class TawnossTinkeringEffect extends OneShotEffect {

    TawnossTinkeringEffect() {
        super(Outcome.Benefit);
        staticText = "if it isn't a creature, it becomes a 0/0 creature in addition to its other types";
    }

    private TawnossTinkeringEffect(final TawnossTinkeringEffect effect) {
        super(effect);
    }

    @Override
    public TawnossTinkeringEffect copy() {
        return new TawnossTinkeringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.isCreature(game)) {
            return false;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0),
                false, false, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
