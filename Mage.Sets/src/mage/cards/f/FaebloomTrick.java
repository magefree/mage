package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.FaerieToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaebloomTrick extends CardImpl {

    public FaebloomTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Create two 1/1 blue Faerie creature tokens with flying. When you do, tap target creature an opponent controls.
        this.getSpellAbility().addEffect(new FaebloomTrickEffect());
    }

    private FaebloomTrick(final FaebloomTrick card) {
        super(card);
    }

    @Override
    public FaebloomTrick copy() {
        return new FaebloomTrick(this);
    }
}

class FaebloomTrickEffect extends OneShotEffect {

    FaebloomTrickEffect() {
        super(Outcome.Benefit);
        staticText = "create two 1/1 blue Faerie creature tokens with flying. " +
                "When you do, tap target creature an opponent controls";
    }

    private FaebloomTrickEffect(final FaebloomTrickEffect effect) {
        super(effect);
    }

    @Override
    public FaebloomTrickEffect copy() {
        return new FaebloomTrickEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!new FaerieToken().putOntoBattlefield(2, game, source)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
