/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.effects.common;

import java.io.ObjectStreamException;
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
    1. The controller of the spell or ability chooses an opponent. (This doesn't target the opponent.)
    2. Each player involved in the clash reveals the top card of his or her library.
    3. The converted mana costs of the revealed cards are noted.
    4. In turn order, each player involved in the clash chooses to put his or her revealed card on either the top or bottom of his or her library. (Note that the player whose turn it is does this first, not necessarily the controller of the clash spell or ability.) When the second player makes this decision, he or she will know what the first player chose. Then all cards are moved at the same time.
    5. The clash is over. If one player in the clash revealed a card with a higher converted mana cost than all other cards revealed in the clash, that player wins the clash.
    6. If any abilities trigger when a player clashes, they trigger and wait to be put on the stack.
    7. The clash spell or ability finishes resolving. That usually involves a bonus gained by the controller of the clash spell or ability if he or she won the clash.
    8. Abilities that triggered during the clash are put on the stack.

    There are no draws or losses in a clash. Either you win it or you don't.
    Each spell or ability with clash says what happens if you (the controller of that spell or ability) win the clash. Typically, if you don't win the clash, nothing happens.
    If no one reveals a card with a higher converted mana cost (for example, each player reveals a card with converted mana cost 2), no one wins the clash.
    An X in a revealed card's mana cost is treated as 0.
    A card without a mana cost (such as a land) has a converted mana cost of 0.
    If one or more of the clashing players reveals a split card, each of the split card's converted mana costs is considered individually. In this way, it's possible for multiple players to win a clash. For example, if Player A reveals a split card with converted mana costs 1 and 3, and Player B reveals a card with converted mana cost 2, they'll both win. (Player A's card has a higher converted mana cost than Player B's card, since 3 is greater than 2. Player B's card has a higher converted mana cost than Player A's card, since 2 is greater than 1.)

 * @author LevelX2
 */

public class ClashEffect extends OneShotEffect implements MageSingleton {
    
    private static final ClashEffect fINSTANCE =  new ClashEffect();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }    
    
    private ClashEffect() {
        super(Outcome.Benefit);
        this.staticText = "Clash with an opponent";
    }
    
    public static ClashEffect getInstance() {
        return fINSTANCE;
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
        if (controller != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.CLASH, controller.getId(), controller.getId()))) {
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
                    if (controller.getLibrary().size() > 0) {                        
                        Cards cards = new CardsImpl();
                        cardController = controller.getLibrary().getFromTop(game);
                        cards.add(cardController);
                        controller.revealCards("for clash by " + controller.getLogName(), cards, game);
                        cmcController = cardController.getManaCost().convertedManaCost();
                        message.append(" (").append(cmcController).append(")");
                    } else {
                        message.append(" no card");
                    }
                    message.append(" vs. ").append(opponent.getLogName());
                    if (opponent.getLibrary().size() > 0) {
                        Cards cards = new CardsImpl();
                        cardOpponent = opponent.getLibrary().getFromTop(game);
                        cards.add(cardOpponent);
                        opponent.revealCards("for clash by " + opponent.getLogName(), cards, game);
                        cmcOpponent = cardOpponent.getManaCost().convertedManaCost();
                        message.append(" (").append(cmcOpponent).append(")");
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
                    do {
                        Player current = playerList.getCurrent(game);
                        if (cardController != null && current.getId().equals(controller.getId())) {
                            topController = current.chooseUse(Outcome.Detriment, "Put " + cardController.getLogName() + " back on top of your library? (otherwise it goes to bottom)" , game);
                        }                        
                        if (cardOpponent != null && current.getId().equals(opponent.getId())) {
                            topOpponent = current.chooseUse(Outcome.Detriment, "Put " + cardOpponent.getLogName() + " back on top of your library? (otherwise it goes to bottom)" , game);
                        }                                                
                    } while (!playerList.getNext(game).getId().equals(game.getActivePlayerId()));
                    // put the cards back to library
                    if (cardController != null)  {
                        controller.moveCardToLibraryWithInfo(cardController, source.getSourceId(), game, Zone.LIBRARY, topController, true);
                    }
                    if (cardOpponent != null)  {
                        opponent.moveCardToLibraryWithInfo(cardOpponent, source.getSourceId(), game, Zone.LIBRARY, topOpponent, true);
                    }
                    game.fireEvent(new GameEvent(EventType.CLASHED, opponent.getId(), source.getSourceId(), controller.getId(), 0, cmcController > cmcOpponent));
                    // set opponent to DoIfClashWonEffect
                    for (Effect effect :source.getEffects()) {
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
