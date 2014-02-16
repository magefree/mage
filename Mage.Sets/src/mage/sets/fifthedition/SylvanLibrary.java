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
package mage.sets.fifthedition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class SylvanLibrary extends CardImpl<SylvanLibrary> {

    public SylvanLibrary(UUID ownerId) {
        super(ownerId, 191, "Sylvan Library", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "5ED";

        this.color.setGreen(true);

        // At the beginning of your draw step, you may draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new SylvanLibraryEffect(), TargetController.YOU, true));

        this.addWatcher(new CardsDrawnThisTurnWatcher());

    }

    public SylvanLibrary(final SylvanLibrary card) {
        super(card);
    }

    @Override
    public SylvanLibrary copy() {
        return new SylvanLibrary(this);
    }
}

class SylvanLibraryEffect extends OneShotEffect<SylvanLibraryEffect> {

    public SylvanLibraryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library";
    }

    public SylvanLibraryEffect(final SylvanLibraryEffect effect) {
        super(effect);
    }

    @Override
    public SylvanLibraryEffect copy() {
        return new SylvanLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, game);
            CardsDrawnThisTurnWatcher watcher = (CardsDrawnThisTurnWatcher) game.getState().getWatchers().get("CardsDrawnThisTurnWatcher", source.getControllerId());
            if (watcher != null) {
                Cards cards = new CardsImpl();
                for (UUID cardId : controller.getHand()) {
                    if (watcher.getCardsDrawnThisTurn().contains(cardId)) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
                int numberOfTargets = Math.min(2, cards.size());
                if (numberOfTargets > 0) {
                    TargetCard target = new TargetCard(numberOfTargets, Zone.PICK, new FilterCard(new StringBuilder(numberOfTargets).append(" cards of cards drawn this turn").toString()));
                    target.setRequired(true);
                    controller.chooseTarget(outcome, cards, target, source, game);

                    Cards cardsPutBack = new CardsImpl();
                    for (UUID cardId :(List<UUID>) target.getTargets()) {
                        Card card = cards.get(cardId, game);
                        if (card != null) {
                            if (controller.canPayLifeCost()
                                    && controller.getLife() >= 4
                                    && controller.chooseUse(outcome, new StringBuilder("Pay 4 life for ").append(card.getName()).append("? (Otherwise it's put on top of your library)").toString(), game)) {
                                controller.loseLife(4, game);
                                game.informPlayers(new StringBuilder(controller.getName()).append(" pays 4 life to keep a card on hand").toString());
                            } else {
                                cardsPutBack.add(card);
                            }
                        }
                    }
                    int numberOfCardsToPutBack = cardsPutBack.size();
                    if (numberOfCardsToPutBack > 1) {
                        TargetCard target2 = new TargetCard(Zone.PICK, new FilterCard("card to put on the top of your library (last chosen will be on top)"));
                        target2.setRequired(true);
                        while (cardsPutBack.size() > 1) {
                            controller.choose(Outcome.Benefit, cardsPutBack, target2, game);
                            Card card = cardsPutBack.get(target2.getFirstTarget(), game);
                            if (card != null) {
                                cardsPutBack.remove(card);
                                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                            }
                            target2.clearChosen();
                        }
                    }
                    if (cardsPutBack.size() == 1) {
                        Card card = cardsPutBack.get(cardsPutBack.iterator().next(), game);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                    if (numberOfCardsToPutBack > 0) {
                        game.informPlayers(new StringBuilder(controller.getName()).append(" puts ").append(numberOfCardsToPutBack).append(" card(s) back to library").toString());
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class CardsDrawnThisTurnWatcher extends WatcherImpl<CardsDrawnThisTurnWatcher> {

    private final Set<UUID> cardsDrawnThisTurn = new HashSet<UUID>();


    public CardsDrawnThisTurnWatcher() {
        super("CardsDrawnThisTurnWatcher", WatcherScope.PLAYER);
    }

    public CardsDrawnThisTurnWatcher(final CardsDrawnThisTurnWatcher watcher) {
        super(watcher);
        this.cardsDrawnThisTurn.addAll(watcher.cardsDrawnThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD && event.getPlayerId().equals(this.getControllerId())) {
            cardsDrawnThisTurn.add(event.getTargetId());
        }
    }

    public Set<UUID> getCardsDrawnThisTurn() {
        return cardsDrawnThisTurn;
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawnThisTurn.clear();
    }

    @Override
    public CardsDrawnThisTurnWatcher copy() {
        return new CardsDrawnThisTurnWatcher(this);
    }
}
