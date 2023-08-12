
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class CruelEntertainment extends CardImpl {

    public CruelEntertainment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Choose target player and another target player. The first player controls the second player during the second player's next turn, and the second player controls the first player during the first player's next turn.
        this.getSpellAbility().addEffect(new CruelEntertainmentEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));

    }

    private CruelEntertainment(final CruelEntertainment card) {
        super(card);
    }

    @Override
    public CruelEntertainment copy() {
        return new CruelEntertainment(this);
    }
}

class CruelEntertainmentEffect extends OneShotEffect {

    public CruelEntertainmentEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose target player and another target player. The first player controls the second player"
                + " during the second player's next turn, and the second player controls the first player during the first player's next turn";
    }

    public CruelEntertainmentEffect(final CruelEntertainmentEffect effect) {
        super(effect);
    }

    @Override
    public CruelEntertainmentEffect copy() {
        return new CruelEntertainmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player player2 = null;
        if (getTargetPointer().getTargets(game, source).size() > 1) {
            player2 = game.getPlayer(getTargetPointer().getTargets(game, source).get(1));
        }
        if (player1 != null && player2 != null) {
            game.getState().getTurnMods().add(new TurnMod(player1.getId()).withNewController(player2.getId()));
            game.getState().getTurnMods().add(new TurnMod(player2.getId()).withNewController(player1.getId()));
            return true;
        }
        return false;
    }
}
