package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NeutralizeTheGuards extends CardImpl {

    public NeutralizeTheGuards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Creatures target opponent controls get -1/-1 until end of turn. Surveil 2.
        this.getSpellAbility().addEffect(new NeutralizeTheGuardsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SurveilEffect(2));
    }

    private NeutralizeTheGuards(final NeutralizeTheGuards card) {
        super(card);
    }

    @Override
    public NeutralizeTheGuards copy() {
        return new NeutralizeTheGuards(this);
    }
}

class NeutralizeTheGuardsEffect extends OneShotEffect {

    NeutralizeTheGuardsEffect() {
        super(Outcome.Benefit);
        staticText = "creatures target opponent controls get -1/-1 until end of turn";
    }

    private NeutralizeTheGuardsEffect(final NeutralizeTheGuardsEffect effect) {
        super(effect);
    }

    @Override
    public NeutralizeTheGuardsEffect copy() {
        return new NeutralizeTheGuardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(source.getFirstTarget()));
        return new AddContinuousEffectToGame(
                new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false)
        ).apply(game, source);
    }
}
