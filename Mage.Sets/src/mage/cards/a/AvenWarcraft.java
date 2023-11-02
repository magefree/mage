
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class AvenWarcraft extends CardImpl {

    public AvenWarcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Creatures you control get +0/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 2, Duration.EndOfTurn));

        // Threshold - If seven or more cards are in your graveyard, choose a color. Creatures you control also gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new AvenWarcraftEffect());
    }

    private AvenWarcraft(final AvenWarcraft card) {
        super(card);
    }

    @Override
    public AvenWarcraft copy() {
        return new AvenWarcraft(this);
    }
}

class AvenWarcraftEffect extends OneShotEffect {

    AvenWarcraftEffect() {
        super(Outcome.Benefit);
        this.staticText = "<br><br><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, "
                + "choose a color. Creatures you control also gain protection from the chosen color until end of turn";
    }

    private AvenWarcraftEffect(final AvenWarcraftEffect effect) {
        super(effect);
    }

    @Override
    public AvenWarcraftEffect copy() {
        return new AvenWarcraftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (new CardsInControllerGraveyardCondition(7).apply(game, source)) {
            game.addEffect(new GainProtectionFromColorAllEffect(
                    Duration.EndOfTurn,
                    StaticFilters.FILTER_CONTROLLED_CREATURES
            ), source);
        }
        return true;
    }
}
