
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class TradeSecrets extends CardImpl {

    public TradeSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // Target opponent draws two cards, then you draw up to four cards. That opponent may repeat this process as many times as they choose.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new TradeSecretsEffect());

    }

    private TradeSecrets(final TradeSecrets card) {
        super(card);
    }

    @Override
    public TradeSecrets copy() {
        return new TradeSecrets(this);
    }
}

class TradeSecretsEffect extends OneShotEffect {

    public TradeSecretsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target opponent draws two cards, then you draw up to four cards. That opponent may repeat this process as many times as they choose";
    }

    private TradeSecretsEffect(final TradeSecretsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String message = "Do you want to draw two cards and allow the spellcaster to draw up to four cards again?";
        String message2 = "How many cards do you want to draw?";
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (controller != null
                && targetOpponent != null) {
            new DrawCardTargetEffect(2).apply(game, source);//The drawcard method would not work immediately
            int amountOfCardsToDraw = controller.getAmount(0, 4, message2, game);
            new DrawCardSourceControllerEffect(amountOfCardsToDraw).apply(game, source);
            while (targetOpponent.chooseUse(Outcome.AIDontUseIt, message, source, game)) {
                new DrawCardTargetEffect(2).apply(game, source);
                amountOfCardsToDraw = controller.getAmount(0, 4, message2, game);
                new DrawCardSourceControllerEffect(amountOfCardsToDraw).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public TradeSecretsEffect copy() {
        return new TradeSecretsEffect(this);
    }
}
