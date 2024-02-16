package mage.cards.g;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class Grindstone extends CardImpl {

    public Grindstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}: Target player puts the top two cards of their library into their graveyard. If both cards share a color, repeat this process.
        Ability ability = new SimpleActivatedAbility(new GrindstoneEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Grindstone(final Grindstone card) {
        super(card);
    }

    @Override
    public Grindstone copy() {
        return new Grindstone(this);
    }
}

class GrindstoneEffect extends OneShotEffect {

    GrindstoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "target player mills two cards. If two cards that share a color were milled this way, repeat this process.";
    }

    private GrindstoneEffect(final GrindstoneEffect effect) {
        super(effect);
    }

    @Override
    public GrindstoneEffect copy() {
        return new GrindstoneEffect(this);
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
