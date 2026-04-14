package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class AppliedGeometry extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("non-Aura permanent you control");

    static {
        filter.add(Predicates.not(SubType.AURA.getPredicate()));
    }

    public AppliedGeometry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{U}");

        // Create a token that's a copy of target non-Aura permanent you control, except it's a 0/0 Fractal creature in addition to its other types. Put six +1/+1 counters on it.
        this.getSpellAbility().addEffect(new AppliedGeometryEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter));
    }

    private AppliedGeometry(final AppliedGeometry card) {
        super(card);
    }

    @Override
    public AppliedGeometry copy() {
        return new AppliedGeometry(this);
    }
}

class AppliedGeometryEffect extends OneShotEffect {

    AppliedGeometryEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target non-Aura permanent you control, " +
                "except it's a 0/0 Fractal creature in addition to its other types. Put six +1/+1 counters on it.";
    }

    private AppliedGeometryEffect(final AppliedGeometryEffect effect) {
        super(effect);
    }

    @Override
    public AppliedGeometryEffect copy() {
        return new AppliedGeometryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 1, false, false,
                null, 0, 0, false
        );
        effect.withAdditionalSubType(SubType.FRACTAL);
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        for (Permanent permanent : effect.getAddedPermanents()) {
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(6), source.getControllerId(), source, game);
        }
        return true;
    }
}
