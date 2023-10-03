
package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class SphinxsTutelage extends CardImpl {

    public SphinxsTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you draw a card, target opponent puts the top two cards of their library into their graveyard. If they're both nonland cards that share a color, repeat this process.
        Ability ability = new DrawCardControllerTriggeredAbility(new SphinxsTutelageEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {5}{U}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1), new ManaCostsImpl<>("{5}{U}")));
    }

    private SphinxsTutelage(final SphinxsTutelage card) {
        super(card);
    }

    @Override
    public SphinxsTutelage copy() {
        return new SphinxsTutelage(this);
    }
}

class SphinxsTutelageEffect extends OneShotEffect {

    public SphinxsTutelageEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent mills two cards. If two nonland cards that share a color were milled this way, repeat this process.";
    }

    private SphinxsTutelageEffect(final SphinxsTutelageEffect effect) {
        super(effect);
    }

    @Override
    public SphinxsTutelageEffect copy() {
        return new SphinxsTutelageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));

        if (targetPlayer != null) {
            int possibleIterations = targetPlayer.getLibrary().size() / 2;
            int iteration = 0;
            boolean colorShared;
            do {
                iteration++;
                if (iteration > possibleIterations + 20) {
                    // 801.16. If the game somehow enters a "loop" of mandatory actions, repeating a sequence of events
                    // with no way to stop, the game is a draw for each player who controls an object that's involved in
                    // that loop, as well as for each player within the range of influence of any of those players. They
                    // leave the game. All remaining players continue to play the game.
                    game.setDraw(source.getControllerId());
                    return true;
                }
                colorShared = false;
                List<Card> cards = targetPlayer
                        .millCards(2, source, game)
                        .getCards(game)
                        .stream()
                        .filter(card -> !card.isLand(game))
                        .collect(Collectors.toList());
                if (cards.size() < 2) {
                    break;
                }
                for (int i = 0; i < cards.size(); i++) {
                    if (colorShared) {
                        break;
                    }
                    ObjectColor color1 = cards.get(i).getColor(game);
                    if (color1.isColorless()) {
                        continue;
                    }
                    for (int j = 0; j < cards.size(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        ObjectColor color2 = cards.get(j).getColor(game);
                        if (color1.shares(color2)) {
                            colorShared = true;
                            break;
                        }
                    }
                }
            } while (colorShared);
            return true;
        }
        return false;
    }
}
