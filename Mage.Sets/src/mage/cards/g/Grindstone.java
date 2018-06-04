
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Grindstone extends CardImpl {

    public Grindstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}: Target player puts the top two cards of their library into their graveyard. If both cards share a color, repeat this process.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrindstoneEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public Grindstone(final Grindstone card) {
        super(card);
    }

    @Override
    public Grindstone copy() {
        return new Grindstone(this);
    }
}

class GrindstoneEffect extends OneShotEffect {

    public GrindstoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player puts the top two cards of their library into their graveyard. If both cards share a color, repeat this process";
    }

    public GrindstoneEffect(final GrindstoneEffect effect) {
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
                Card card1 = null;
                Cards toGraveyard = new CardsImpl();
                for (Card card : targetPlayer.getLibrary().getCards(game)) {
                    toGraveyard.add(card);
                    if (card1 == null) {
                        card1 = card;
                    } else {
                        colorShared = card1.getColor(game).shares(card.getColor(game));
                        break;
                    }
                }
                targetPlayer.moveCards(toGraveyard, Zone.GRAVEYARD, source, game);
            } while (colorShared);
            return true;
        }
        return false;
    }
}
