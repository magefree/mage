package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MercadiasDownfall extends CardImpl {

    public MercadiasDownfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Each attacking creature gets +1/+0 until end of turn for each nonbasic land defending player controls.
        this.getSpellAbility().addEffect(new MercadiasDownfallEffect());
    }

    private MercadiasDownfall(final MercadiasDownfall card) {
        super(card);
    }

    @Override
    public MercadiasDownfall copy() {
        return new MercadiasDownfall(this);
    }
}

class MercadiasDownfallEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    MercadiasDownfallEffect() {
        super(Outcome.Benefit);
        staticText = "each attacking creature gets +1/+0 until end of turn for each nonbasic land defending player controls";
    }

    private MercadiasDownfallEffect(final MercadiasDownfallEffect effect) {
        super(effect);
    }

    @Override
    public MercadiasDownfallEffect copy() {
        return new MercadiasDownfallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_ATTACKING_CREATURE, source.getControllerId(), source, game
        )) {
            int count = game.getBattlefield().count(
                    filter, game.getCombat().getDefendingPlayerId(permanent.getId(), game), source, game
            );
            game.addEffect(new BoostTargetEffect(
                    count, 0, Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
