package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConeOfCold extends CardImpl {

    public ConeOfCold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20);

        // 1-9 | Tap all creatures your opponents control.
        effect.addTableEntry(1, 9, new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES));

        // 10-19 | Tap all creatures your opponents control. Those creatures don't untap during their controllers' next untap steps.
        effect.addTableEntry(
                10, 19,
                new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES), new ConeOfColdEffect()
        );

        // 20 | Tap all creatures your opponents control. Those creatures don't untap during their controllers' next untap steps. Until your next turn, creatures your opponents control enter the battlefield tapped.
        effect.addTableEntry(
                20, 20,
                new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES), new ConeOfColdEffect(),
                new PermanentsEnterBattlefieldTappedEffect(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, Duration.EndOfTurn
                ).setText("Until your next turn, creatures your opponents control enter the battlefield tapped")
        );
    }

    private ConeOfCold(final ConeOfCold card) {
        super(card);
    }

    @Override
    public ConeOfCold copy() {
        return new ConeOfCold(this);
    }
}

class ConeOfColdEffect extends OneShotEffect {

    ConeOfColdEffect() {
        super(Outcome.Benefit);
        staticText = "Those creatures don't untap during their controllers' next untap steps";
    }

    private ConeOfColdEffect(final ConeOfColdEffect effect) {
        super(effect);
    }

    @Override
    public ConeOfColdEffect copy() {
        return new ConeOfColdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new DontUntapInControllersUntapStepTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(new FixedTargets(
                        game.getBattlefield().getActivePermanents(
                                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES,
                                source.getControllerId(), source, game
                        ), game
                )), source);
        return true;
    }
}
