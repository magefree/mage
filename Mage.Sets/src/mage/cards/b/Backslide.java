
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Backslide extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a morph ability");

    static {
        filter.add(new AbilityPredicate(MorphAbility.class));
    }

    public Backslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Turn target creature with a morph ability face down.
        this.getSpellAbility().addEffect(new BackslideEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}")));

    }

    private Backslide(final Backslide card) {
        super(card);
    }

    @Override
    public Backslide copy() {
        return new Backslide(this);
    }
}

class BackslideEffect extends OneShotEffect {

    BackslideEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn target creature with a morph ability face down.";
    }

    BackslideEffect(final BackslideEffect effect) {
        super(effect);
    }

    @Override
    public BackslideEffect copy() {
        return new BackslideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);
        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        return true;
    }
}
