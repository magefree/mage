
package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class GoblinGame extends CardImpl {

    public GoblinGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Each player hides at least one item, then all players reveal them simultaneously. Each player loses life equal to the number of items they revealed. The player who revealed the fewest items then loses half their life, rounded up. If two or more players are tied for fewest, each loses half their life, rounded up.
        // Reinterpreted as: Each player secretly chooses a number greater than 0. Then those numbers are revealed. Each player loses life equal to their chosen number. The player who revealed the lowest number then loses half their life, rounded up. If two or more players are tied for lowest, each loses half their life, rounded up.
        this.getSpellAbility().addEffect(new GoblinGameEffect());

    }

    public GoblinGame(final GoblinGame card) {
        super(card);
    }

    @Override
    public GoblinGame copy() {
        return new GoblinGame(this);
    }
}

class GoblinGameEffect extends OneShotEffect {

    public GoblinGameEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player hides at least one item, then all players reveal them simultaneously. Each player loses life equal to the number of items they revealed. The player who revealed the fewest items then loses half their life, rounded up. If two or more players are tied for fewest, each loses half their life, rounded up.";
    }

    public GoblinGameEffect(final GoblinGameEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGameEffect copy() {
        return new GoblinGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lowestNumber = 0;
        int number = 0;
        String message = "Choose a number of objects to hide.";
        Map<Player, Integer> numberChosen = new HashMap<>();

        //players choose numbers
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                // TODO: consider changing 1000 to another cap, or even Integer.MAX_VALUE if the Volcano Hellion binary wraparound gets addressed (although hiding over two billions of items would be rather difficult IRL)
                number = player.getAmount(1, 1000, message, game);
                numberChosen.put(player, number);
            }
        }
        //get lowest number
        for (Player player : numberChosen.keySet()) {
            if (lowestNumber == 0 || lowestNumber > numberChosen.get(player)) {
                lowestNumber = numberChosen.get(player);
            }
        }
        //reveal numbers to players and follow through with effects
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                game.informPlayers(player.getLogName() + " chose number " + numberChosen.get(player));
                player.loseLife(numberChosen.get(player), game, false);
            }
        }
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                if (numberChosen.get(player) <= lowestNumber) {
                    game.informPlayers(player.getLogName() + " chose the lowest number");
                    Integer amount = (int) Math.ceil(player.getLife() / 2f);
                    if (amount > 0) {
                        player.loseLife(amount, game, false);
                    }
                }
            }
        }
        return true;
    }
}
