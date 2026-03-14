package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mirrorform extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("non-Aura permanent");

    static {
        filter.add(Predicates.not(SubType.AURA.getPredicate()));
    }

    public Mirrorform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Each nonland permanent you control becomes a copy of target non-Aura permanent.
        this.getSpellAbility().addEffect(new MirrorformEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Mirrorform(final Mirrorform card) {
        super(card);
    }

    @Override
    public Mirrorform copy() {
        return new Mirrorform(this);
    }
}

class MirrorformEffect extends OneShotEffect {

    MirrorformEffect() {
        super(Outcome.Benefit);
        staticText = "each nonland permanent you control becomes a copy of target non-Aura permanent";
    }

    private MirrorformEffect(final MirrorformEffect effect) {
        super(effect);
    }

    @Override
    public MirrorformEffect copy() {
        return new MirrorformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                source.getControllerId(), source, game
        )) {
            game.copyPermanent(targetPermanent, permanent.getId(), source, new EmptyCopyApplier());
        }
        return true;
    }
}
