package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TruePolymorph extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");
    private static final FilterPermanent filter2;

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter2 = filter.copy();
        filter.add(new AnotherTargetPredicate(1));
        filter2.add(new AnotherTargetPredicate(2));
    }

    public TruePolymorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Target artifact or creature becomes a copy of another target artifact or creature.
        this.getSpellAbility().addEffect(new TruePolymorphEffect());
        Target target = new TargetPermanent(filter);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target.withChooseHint("becomes a copy"));
        target = new TargetPermanent(filter2);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target.withChooseHint("to copy"));
    }

    private TruePolymorph(final TruePolymorph card) {
        super(card);
    }

    @Override
    public TruePolymorph copy() {
        return new TruePolymorph(this);
    }
}

class TruePolymorphEffect extends OneShotEffect {

    TruePolymorphEffect() {
        super(Outcome.Benefit);
        staticText = "target artifact or creature becomes a copy of another target artifact or creature";
    }

    private TruePolymorphEffect(final TruePolymorphEffect effect) {
        super(effect);
    }

    @Override
    public TruePolymorphEffect copy() {
        return new TruePolymorphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (copyTo == null) {
            return false;
        }
        Permanent copyFrom = game.getPermanentOrLKIBattlefield(source.getTargets().get(1).getFirstTarget());
        if (copyFrom == null) {
            return false;
        }
        game.copyPermanent(Duration.Custom, copyFrom, copyTo.getId(), source, new EmptyCopyApplier());
        return true;
    }
}
