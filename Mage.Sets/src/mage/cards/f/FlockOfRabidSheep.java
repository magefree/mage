
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.RabidSheepToken;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class FlockOfRabidSheep extends CardImpl {

    public FlockOfRabidSheep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Flip X coins. For each flip you win, create a 2/2 green Sheep creature token named Rabid Sheep.
        this.getSpellAbility().addEffect(new FlockOfRabidSheepEffect());
    }

    private FlockOfRabidSheep(final FlockOfRabidSheep card) {
        super(card);
    }

    @Override
    public FlockOfRabidSheep copy() {
        return new FlockOfRabidSheep(this);
    }
}

class FlockOfRabidSheepEffect extends OneShotEffect {

    public FlockOfRabidSheepEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Flip X coins. For each flip you win, create a 2/2 green Sheep creature token named Rabid Sheep";
    }

    private FlockOfRabidSheepEffect(final FlockOfRabidSheepEffect effect) {
        super(effect);
    }

    @Override
    public FlockOfRabidSheepEffect copy() {
        return new FlockOfRabidSheepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int repeat = source.getManaCostsToPay().getX();
            int wonCount = 0;
            for (int i = 1; i <= repeat; i++) {
                if (controller.flipCoin(source, game, true)) {
                    wonCount++;
                }
            }
            new CreateTokenEffect(new RabidSheepToken(), wonCount).apply(game, source);
            return true;
        }
        return false;
    }
}
