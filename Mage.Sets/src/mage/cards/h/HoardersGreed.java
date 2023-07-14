
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HoardersGreed extends CardImpl {

    public HoardersGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // You lose 2 life and draw two cards, then clash with an opponent. If you win, repeat this process.
        this.getSpellAbility().addEffect(new HoardersGreedEffect());
    }

    private HoardersGreed(final HoardersGreed card) {
        super(card);
    }

    @Override
    public HoardersGreed copy() {
        return new HoardersGreed(this);
    }
}

class HoardersGreedEffect extends OneShotEffect {

    public HoardersGreedEffect() {
        super(Outcome.Benefit);
        this.staticText = "You lose 2 life and draw two cards, then clash with an opponent. If you win, repeat this process";
    }

    public HoardersGreedEffect(final HoardersGreedEffect effect) {
        super(effect);
    }

    @Override
    public HoardersGreedEffect copy() {
        return new HoardersGreedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            do {
                controller.loseLife(2, game, source, false);
                controller.drawCards(2, source, game);
            } while (controller.canRespond() && new ClashEffect().apply(game, source));
            return true;
        }
        return false;
    }
}
