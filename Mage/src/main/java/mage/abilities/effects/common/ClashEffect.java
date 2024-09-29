package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 * 1. The controller of the spell or ability chooses an opponent. (This doesn't
 * target the opponent.) 2. Each player involved in the clash reveals the top
 * card of their library. 3. The converted mana costs of the revealed cards
 * are noted. 4. In turn order, each player involved in the clash chooses to put
 * their revealed card on either the top or bottom of their library.
 * (Note that the player whose turn it is does this first, not necessarily the
 * controller of the clash spell or ability.) When the second player makes this
 * decision, they will know what the first player chose. Then all cards are
 * moved at the same time. 5. The clash is over. If one player in the clash
 * revealed a card with a higher converted mana cost than all other cards
 * revealed in the clash, that player wins the clash. 6. If any abilities
 * trigger when a player clashes, they trigger and wait to be put on the stack.
 * 7. The clash spell or ability finishes resolving. That usually involves a
 * bonus gained by the controller of the clash spell or ability if they won
 * the clash. 8. Abilities that triggered during the clash are put on the stack.
 * <p>
 * There are no draws or losses in a clash. Either you win it or you don't. Each
 * spell or ability with clash says what happens if you (the controller of that
 * spell or ability) win the clash. Typically, if you don't win the clash,
 * nothing happens. If no one reveals a card with a higher converted mana cost
 * (for example, each player reveals a card with converted mana cost 2), no one
 * wins the clash. An X in a revealed card's mana cost is treated as 0. A card
 * without a mana cost (such as a land) has a converted mana cost of 0. If one
 * or more of the clashing players reveals a split card, each of the split
 * card's converted mana costs is considered individually. In this way, it's
 * possible for multiple players to win a clash. For example, if Player A
 * reveals a split card with converted mana costs 1 and 3, and Player B reveals
 * a card with converted mana cost 2, they'll both win. (Player A's card has a
 * higher converted mana cost than Player B's card, since 3 is greater than 2.
 * Player B's card has a higher converted mana cost than Player A's card, since
 * 2 is greater than 1.)
 *
 * @author LevelX2
 */
public class ClashEffect extends OneShotEffect {

    public ClashEffect() {
        super(Outcome.Benefit);
        this.staticText = "Clash with an opponent";
    }

    protected ClashEffect(final ClashEffect effect) {
        super(effect);
    }

    @Override
    public ClashEffect copy() {
        return new ClashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null
                || sourceObject == null
        ) {
            return false;
        }
        // choose opponent
        Target target = new TargetOpponent(true);
        target.withTargetName("an opponent to clash with");
        if (!controller.choose(Outcome.Benefit, target, source, game)) {
            return false;
        }
        return new ClashTargetEffect().setTargetPointer(new FixedTarget(target.getFirstTarget())).apply(game, source);
    }
}
