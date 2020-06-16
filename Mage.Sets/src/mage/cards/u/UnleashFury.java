package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnleashFury extends CardImpl {

    public UnleashFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Double the power of target creature until end of turn.
        this.getSpellAbility().addEffect(new UnleashFuryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnleashFury(final UnleashFury card) {
        super(card);
    }

    @Override
    public UnleashFury copy() {
        return new UnleashFury(this);
    }
}

class UnleashFuryEffect extends OneShotEffect {

    UnleashFuryEffect() {
        super(Outcome.Benefit);
        staticText = "double the power of target creature until end of turn";
    }

    private UnleashFuryEffect(final UnleashFuryEffect effect) {
        super(effect);
    }

    @Override
    public UnleashFuryEffect copy() {
        return new UnleashFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                permanent.getPower().getValue(), 0, Duration.EndOfTurn
        ), source);
        return true;
    }
}
