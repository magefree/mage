package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DauntlessUnity extends CardImpl {

    public DauntlessUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // Creatures you control get +1/+1 until end of turn. If this spell was kicked, those creatures get +2/+1 until end of turn instead.
        this.getSpellAbility().addEffect(new DauntlessUnityEffect());
    }

    private DauntlessUnity(final DauntlessUnity card) {
        super(card);
    }

    @Override
    public DauntlessUnity copy() {
        return new DauntlessUnity(this);
    }
}

class DauntlessUnityEffect extends OneShotEffect {

    DauntlessUnityEffect() {
        super(Outcome.Benefit);
        staticText = "Creatures you control get +1/+1 until end of turn. " +
                "If this spell was kicked, those creatures get +2/+1 until end of turn instead.";
    }

    private DauntlessUnityEffect(final DauntlessUnityEffect effect) {
        super(effect);
    }

    @Override
    public DauntlessUnityEffect copy() {
        return new DauntlessUnityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (KickedCondition.ONCE.apply(game, source)) {
            game.addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn), source);
        } else {
            game.addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn), source);
        }
        return true;
    }
}
