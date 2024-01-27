package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EliminateTheImpossible extends CardImpl {

    public EliminateTheImpossible(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Investigate. Creatures your opponents control get -2/-0 until end of turn. If any of them are suspected, they're no longer suspected.
        this.getSpellAbility().addEffect(new InvestigateEffect());
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -2, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));
        this.getSpellAbility().addEffect(new EliminateTheImpossibleEffect());
    }

    private EliminateTheImpossible(final EliminateTheImpossible card) {
        super(card);
    }

    @Override
    public EliminateTheImpossible copy() {
        return new EliminateTheImpossible(this);
    }
}

class EliminateTheImpossibleEffect extends OneShotEffect {

    EliminateTheImpossibleEffect() {
        super(Outcome.Benefit);
        staticText = "If any of them are suspected, they're no longer suspected";
    }

    private EliminateTheImpossibleEffect(final EliminateTheImpossibleEffect effect) {
        super(effect);
    }

    @Override
    public EliminateTheImpossibleEffect copy() {
        return new EliminateTheImpossibleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (permanent.isSuspected()) {
                permanent.setSuspected(false, game, source);
            }
        }
        return true;
    }
}
