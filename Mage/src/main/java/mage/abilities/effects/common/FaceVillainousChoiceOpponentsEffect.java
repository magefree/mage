package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.FaceVillainousChoice;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class FaceVillainousChoiceOpponentsEffect extends OneShotEffect {

    private final FaceVillainousChoice choice;

    public FaceVillainousChoiceOpponentsEffect(FaceVillainousChoice choice) {
        super(Outcome.Benefit);
        this.choice = choice;
        staticText = "each opponent " + choice.generateRule();
    }

    private FaceVillainousChoiceOpponentsEffect(final FaceVillainousChoiceOpponentsEffect effect) {
        super(effect);
        this.choice = effect.choice;
    }

    @Override
    public FaceVillainousChoiceOpponentsEffect copy() {
        return new FaceVillainousChoiceOpponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                choice.faceChoice(player, game, source);
            }
        }
        return true;
    }
}
