
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SavorTheMoment extends CardImpl {

    public SavorTheMoment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}{U}");


        // Take an extra turn after this one. Skip the untap step of that turn.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
        this.getSpellAbility().addEffect(new SkipNextUntapStepSourceControllerEffect());
    }

    private SavorTheMoment(final SavorTheMoment card) {
        super(card);
    }

    @Override
    public SavorTheMoment copy() {
        return new SavorTheMoment(this);
    }
}

class SkipNextUntapStepSourceControllerEffect extends OneShotEffect {

    public SkipNextUntapStepSourceControllerEffect() {
        super(Outcome.Detriment);
        this.staticText = "Skip the untap step of that turn";
    }

    public SkipNextUntapStepSourceControllerEffect(SkipNextUntapStepSourceControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.getState().getTurnMods().add(new TurnMod(controller.getId()).withSkipStep(PhaseStep.UNTAP));
            return true;
        }
        return false;
    }

    @Override
    public SkipNextUntapStepSourceControllerEffect copy() {
        return new SkipNextUntapStepSourceControllerEffect(this);
    }

}
