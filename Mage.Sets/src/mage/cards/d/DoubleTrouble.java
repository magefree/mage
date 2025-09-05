package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoubleTrouble extends CardImpl {

    public DoubleTrouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Double the power of each creature you control until end of turn.
        this.getSpellAbility().addEffect(new DoubleTroubleEffect());
    }

    private DoubleTrouble(final DoubleTrouble card) {
        super(card);
    }

    @Override
    public DoubleTrouble copy() {
        return new DoubleTrouble(this);
    }
}

class DoubleTroubleEffect extends OneShotEffect {

    DoubleTroubleEffect() {
        super(Outcome.Benefit);
        staticText = "double the power of each creature you control until end of turn";
    }

    private DoubleTroubleEffect(final DoubleTroubleEffect effect) {
        super(effect);
    }

    @Override
    public DoubleTroubleEffect copy() {
        return new DoubleTroubleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            int power = permanent.getPower().getValue();
            if (power != 0) {
                game.addEffect(new BoostTargetEffect(power, 0)
                        .setTargetPointer(new FixedTarget(permanent, game)), source);
            }
        }
        return true;
    }
}
