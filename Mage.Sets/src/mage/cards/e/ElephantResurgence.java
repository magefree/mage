
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ElephantResurgenceToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ElephantResurgence extends CardImpl {

    public ElephantResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Each player creates a green Elephant creature token. Those creatures have "This creature's power and toughness are each equal to the number of creature cards in its controller's graveyard."
        this.getSpellAbility().addEffect(new ElephantResurgenceEffect());
    }

    private ElephantResurgence(final ElephantResurgence card) {
        super(card);
    }

    @Override
    public ElephantResurgence copy() {
        return new ElephantResurgence(this);
    }
}

class ElephantResurgenceEffect extends OneShotEffect {

    public ElephantResurgenceEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player creates a green Elephant creature token. Those creatures have "
                + "\"This creature's power and toughness are each equal to the number of creature cards in its controller's graveyard.\"";
    }

    public ElephantResurgenceEffect(final ElephantResurgenceEffect effect) {
        super(effect);
    }

    @Override
    public ElephantResurgenceEffect copy() {
        return new ElephantResurgenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Effect effect = new CreateTokenTargetEffect(new ElephantResurgenceToken(), 1);
                effect.setTargetPointer(new FixedTarget(playerId));
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
