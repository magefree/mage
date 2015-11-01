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
package mage.sets.returntoravnica;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 *
 * @author LevelX2
 */
public class JaceArchitectOfThought extends CardImpl {

    public JaceArchitectOfThought(UUID ownerId) {
        super(ownerId, 44, "Jace, Architect of Thought", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Jace");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtStartEffect1(), 1));

        // -2: Reveal the top three cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect2(), -2));

        // -8: For each player, search that player's library for a nonland card and exile it, then that player shuffles his or her library. You may cast those cards without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect3(), -8));

    }

    public JaceArchitectOfThought(final JaceArchitectOfThought card) {
        super(card);
    }

    @Override
    public JaceArchitectOfThought copy() {
        return new JaceArchitectOfThought(this);
    }
}

class JaceArchitectOfThoughtStartEffect1 extends OneShotEffect {

    public JaceArchitectOfThoughtStartEffect1() {
        super(Outcome.UnboostCreature);
        this.staticText = "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn";
    }

    public JaceArchitectOfThoughtStartEffect1(final JaceArchitectOfThoughtStartEffect1 effect) {
        super(effect);
    }

    @Override
    public JaceArchitectOfThoughtStartEffect1 copy() {
        return new JaceArchitectOfThoughtStartEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new JaceArchitectOfThoughtDelayedTriggeredAbility(game.getTurnNum());
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        delayedAbility.setSourceObject(source.getSourceObject(game), game);
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}

class JaceArchitectOfThoughtDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private int startingTurn;

    public JaceArchitectOfThoughtDelayedTriggeredAbility(int startingTurn) {
        super(new BoostTargetEffect(-1, 0, Duration.EndOfTurn), Duration.Custom, false);
        this.startingTurn = startingTurn;
    }

    public JaceArchitectOfThoughtDelayedTriggeredAbility(final JaceArchitectOfThoughtDelayedTriggeredAbility ability) {
        super(ability);
        this.startingTurn = ability.startingTurn;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getSourceId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public JaceArchitectOfThoughtDelayedTriggeredAbility copy() {
        return new JaceArchitectOfThoughtDelayedTriggeredAbility(this);
    }

    @Override
    public boolean isInactive(Game game) {
        return game.getActivePlayerId().equals(getControllerId()) && game.getTurnNum() != startingTurn;
    }

    @Override
    public String getRule() {
        return "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.";
    }
}

class JaceArchitectOfThoughtEffect2 extends OneShotEffect {

    public JaceArchitectOfThoughtEffect2() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top three cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other on the bottom of your library in any order";
    }

    public JaceArchitectOfThoughtEffect2(final JaceArchitectOfThoughtEffect2 effect) {
        super(effect);
    }

    @Override
    public JaceArchitectOfThoughtEffect2 copy() {
        return new JaceArchitectOfThoughtEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.revealCards("Jace, Architect of Thought", cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = null;
            if (opponents.size() > 1) {
                TargetOpponent targetOpponent = new TargetOpponent();
                if (player.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                }
            }
            if (opponent == null) {
                opponent = game.getPlayer(opponents.iterator().next());
            }

            TargetCard target = new TargetCard(0, cards.size(), Zone.PICK, new FilterCard("cards to put in the first pile"));
            target.setRequired(false);
            Cards pile1 = new CardsImpl();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                for (UUID targetId : (List<UUID>) target.getTargets()) {
                    Card card = cards.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        cards.remove(card);
                    }
                }
            }
            player.revealCards("Pile 1 (Jace, Architect of Thought)", pile1, game);
            player.revealCards("Pile 2 (Jace, Architect of Thought)", cards, game);

            postPileToLog("Pile 1", pile1.getCards(game), game);
            postPileToLog("Pile 2", cards.getCards(game), game);

            Cards cardsToHand = cards;
            Cards cardsToLibrary = pile1;
            List<Card> cardPile1 = new ArrayList<>();
            List<Card> cardPile2 = new ArrayList<>();
            for (UUID cardId : pile1) {
                cardPile1.add(game.getCard(cardId));
            }
            for (UUID cardId : cards) {
                cardPile2.add(game.getCard(cardId));
            }

            boolean pileChoice = player.choosePile(Outcome.Neutral, "Choose a pile to to put into your hand.", cardPile1, cardPile2, game);
            if (pileChoice) {
                cardsToHand = pile1;
                cardsToLibrary = cards;
            }
            game.informPlayers(player.getLogName() + " chose pile" + (pileChoice ? "1" : "2"));

            for (UUID cardUuid : cardsToHand) {
                Card card = cardsToHand.get(cardUuid, game);
                if (card != null) {
                    player.moveCards(card, null, Zone.HAND, source, game);
                }
            }

            TargetCard targetCard = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
            while (player.canRespond() && cardsToLibrary.size() > 1) {
                player.choose(Outcome.Neutral, cardsToLibrary, targetCard, game);
                Card card = cardsToLibrary.get(targetCard.getFirstTarget(), game);
                if (card != null) {
                    cardsToLibrary.remove(card);
                    player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
                }
                target.clearChosen();
            }
            if (cardsToLibrary.size() == 1) {
                Card card = cardsToLibrary.get(cardsToLibrary.iterator().next(), game);
                player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
            }
            return true;
        }
        return false;
    }

    private void postPileToLog(String pileName, Set<Card> cards, Game game) {
        StringBuilder message = new StringBuilder(pileName).append(": ");
        for (Card card : cards) {
            message.append(card.getName()).append(" ");
        }
        if (cards.isEmpty()) {
            message.append(" (empty)");
        }
        game.informPlayers(message.toString());
    }
}

class JaceArchitectOfThoughtEffect3 extends OneShotEffect {

    public JaceArchitectOfThoughtEffect3() {
        super(Outcome.PlayForFree);
        this.staticText = "For each player, search that player's library for a nonland card and exile it, then that player shuffles his or her library. You may cast those cards without paying their mana costs";
    }

    public JaceArchitectOfThoughtEffect3(final JaceArchitectOfThoughtEffect3 effect) {
        super(effect);
    }

    @Override
    public JaceArchitectOfThoughtEffect3 copy() {
        return new JaceArchitectOfThoughtEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        for (UUID playerId : controller.getInRange()) {
            Player player = game.getPlayer(playerId);
            String playerName = new StringBuilder(player.getLogName()).append("'s").toString();
            if (source.getControllerId().equals(player.getId())) {
                playerName = "your";
            }
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterNonlandCard(new StringBuilder("nonland card from ").append(playerName).append(" library").toString()));
            if (controller.searchLibrary(target, game, playerId)) {
                UUID targetId = target.getFirstTarget();
                Card card = player.getLibrary().remove(targetId, game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                }
            }
            player.shuffleLibrary(game);
        }

        ExileZone jaceExileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (jaceExileZone == null) {
            return true;
        }
        FilterCard filter = new FilterCard("card to cast without mana costs");
        TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
        while (jaceExileZone.count(filter, game) > 0 && controller.choose(Outcome.PlayForFree, jaceExileZone, target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                if (controller.cast(card.getSpellAbility(), game, true)) {
                    game.getExile().removeCard(card, game);
                }
            }
            target.clearChosen();
        }
        return true;
    }
}
