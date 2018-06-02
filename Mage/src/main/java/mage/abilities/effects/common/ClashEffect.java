
package mage.abilities.effects.common;

import java.io.ObjectStreamException;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 * 1. The controller of the spell or ability chooses an opponent. (This doesn't
 * target the opponent.) 2. Each player involved in the clash reveals the top
 * card of their library. 3. The converted mana costs of the revealed cards
 * are noted. 4. In turn order, each player involved in the clash chooses to put
 * their revealed card on either the top or bottom of their library.
 * (Note that the player whose turn it is does this first, not necessarily the
 * controller of the clash spell or ability.) When the second player makes this
 * decision, he or she will know what the first player chose. Then all cards are
 * moved at the same time. 5. The clash is over. If one player in the clash
 * revealed a card with a higher converted mana cost than all other cards
 * revealed in the clash, that player wins the clash. 6. If any abilities
 * trigger when a player clashes, they trigger and wait to be put on the stack.
 * 7. The clash spell or ability finishes resolving. That usually involves a
 * bonus gained by the controller of the clash spell or ability if he or she won
 * the clash. 8. Abilities that triggered during the clash are put on the stack.
 *
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
public class ClashEffect extends OneShotEffect implements MageSingleton {

    private static final ClashEffect instance = new ClashEffect();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    private ClashEffect() {
        super(Outcome.Benefit);
        this.staticText = "Clash with an opponent";
    }

    public static ClashEffect getInstance() {
        return instance;
    }

    public ClashEffect(final ClashEffect effect) {
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
        if (controller != null && sourceObject != null
                && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.CLASH, controller.getId(), controller.getId()))) {
            // choose opponent
            Target target = new TargetOpponent(true);
            target.setTargetName("an opponent to clash with");
            if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    int cmcController = Integer.MIN_VALUE;
                    Card cardController = null;
                    boolean topController = true;
                    int cmcOpponent = Integer.MIN_VALUE;
                    Card cardOpponent = null;
                    boolean topOpponent = true;
                    // Reveal top cards of involved players
                    StringBuilder message = new StringBuilder("Clash: ");
                    message.append(controller.getLogName());
                    if (controller.getLibrary().hasCards()) {
                        Cards cards = new CardsImpl();
                        cardController = controller.getLibrary().getFromTop(game);
                        cards.add(cardController);
                        controller.revealCards(sourceObject.getIdName() + ": Clash card of " + controller.getName(), cards, game);
                        cmcController = cardController.getConvertedManaCost();
                        message.append(" (").append(cmcController).append(')');
                    } else {
                        message.append(" no card");
                    }
                    message.append(" vs. ").append(opponent.getLogName());
                    if (opponent.getLibrary().hasCards()) {
                        Cards cards = new CardsImpl();
                        cardOpponent = opponent.getLibrary().getFromTop(game);
                        cards.add(cardOpponent);
                        opponent.revealCards(sourceObject.getIdName() + ": Clash card of " + opponent.getName(), cards, game);
                        cmcOpponent = cardOpponent.getConvertedManaCost();
                        message.append(" (").append(cmcOpponent).append(')');
                    } else {
                        message.append(" no card");
                    }
                    message.append(" - ");
                    if (!game.isSimulation()) {
                        if (cmcController > cmcOpponent) {
                            message.append(controller.getLogName()).append(" won the clash");
                            game.informPlayer(controller, "You won the clash!");
                        } else if (cmcController < cmcOpponent) {
                            message.append(opponent.getLogName()).append(" won the clash");
                            game.informPlayer(controller, opponent.getLogName() + " won the clash!");
                        } else {
                            message.append(" no winner ");
                        }
                        game.informPlayers(message.toString());
                    }
                    // decide to put the cards on top or on the buttom of library in turn order beginning with the active player in turn order
                    PlayerList playerList = game.getPlayerList().copy();
                    playerList.setCurrent(game.getActivePlayerId());
                    Player nextPlayer;
                    do {
                        Player current = playerList.getCurrent(game);
                        if (cardController != null && current.getId().equals(controller.getId())) {
                            topController = current.chooseUse(Outcome.Detriment, "Put " + cardController.getLogName() + " back on top of your library? (otherwise it goes to bottom)", source, game);
                        }
                        if (cardOpponent != null && current.getId().equals(opponent.getId())) {
                            topOpponent = current.chooseUse(Outcome.Detriment, "Put " + cardOpponent.getLogName() + " back on top of your library? (otherwise it goes to bottom)", source, game);
                        }
                        nextPlayer = playerList.getNext(game);
                    } while (nextPlayer != null && !nextPlayer.getId().equals(game.getActivePlayerId()));
                    // put the cards back to library
                    if (cardController != null) {
                        controller.moveCardToLibraryWithInfo(cardController, source.getSourceId(), game, Zone.LIBRARY, topController, true);
                    }
                    if (cardOpponent != null) {
                        opponent.moveCardToLibraryWithInfo(cardOpponent, source.getSourceId(), game, Zone.LIBRARY, topOpponent, true);
                    }
                    // fire CLASHED event with info about who won
                    String winner = "draw";
                    if (cmcController > cmcOpponent) {
                        winner = "controller";
                    }
                    if (cmcOpponent > cmcController) {
                        winner = "opponent";
                    }
                    GameEvent gameEvent = new GameEvent(EventType.CLASHED, opponent.getId(), source.getSourceId(), controller.getId());
                    gameEvent.setData(winner);
                    game.fireEvent(gameEvent);

                    // set opponent to DoIfClashWonEffect
                    for (Effect effect : source.getEffects()) {
                        if (effect instanceof DoIfClashWonEffect) {
                            effect.setValue("clashOpponent", opponent);
                        }
                    }
                    return cmcController > cmcOpponent;
                }
            }
        }
        return false;
    }
}
