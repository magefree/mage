package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseAndShine extends CardImpl {

    static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public RiseAndShine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target noncreature artifact you control becomes a 0/0 artifact creature. Put four +1/+1 counters on each artifact that became a creature this way.
        this.getSpellAbility().addEffect(new AddCardTypeTargetEffect(
                Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE
        ).setText("Target noncreature artifact you control becomes"));
        this.getSpellAbility().addEffect(new SetPowerToughnessTargetEffect(
                0, 0, Duration.EndOfGame
        ).setText(" a 0/0 artifact creature"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(4)
        ).setText("Put four +1/+1 counters on each artifact that became a creature this way"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Overload {4}{U}{U}
        this.addAbility(new OverloadAbility(this, new RiseAndShineEffect(), new ManaCostsImpl<>("{4}{U}{U}")));
    }

    private RiseAndShine(final RiseAndShine card) {
        super(card);
    }

    @Override
    public RiseAndShine copy() {
        return new RiseAndShine(this);
    }
}

class RiseAndShineEffect extends OneShotEffect {

    RiseAndShineEffect() {
        super(Outcome.Benefit);
    }

    private RiseAndShineEffect(final RiseAndShineEffect effect) {
        super(effect);
    }

    @Override
    public RiseAndShineEffect copy() {
        return new RiseAndShineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                RiseAndShine.filter, source.getControllerId(), source, game
        );
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(new AddCardTypeTargetEffect(
                Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE
        ).setTargetPointer(new FixedTargets(permanents, game)), source);
        game.addEffect(new SetPowerToughnessTargetEffect(
                0, 0, Duration.EndOfGame
        ).setTargetPointer(new FixedTargets(permanents, game)), source);
        for (Permanent permanent : permanents) {
            permanent.addCounters(CounterType.P1P1.createInstance(4), source.getControllerId(), source, game);
        }
        return true;
    }
}
