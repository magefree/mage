package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.*;
import mage.ApprovingObject;

/**
 * @author LevelX2
 */
public final class JaceArchitectOfThought extends CardImpl {

    public JaceArchitectOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtStartEffect1(), 1));

        // -2: Reveal the top three cards of your library. An opponent separates those cards into two piles. 
        // Put one pile into your hand and the other on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect2(), -2));

        // -8: For each player, search that player's library for a nonland card and exile it, 
        // then that player shuffles their library. You may cast those cards without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect3(), -8));

    }

    private JaceArchitectOfThought(final JaceArchitectOfThought card) {
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
        this.staticText = "Until your next turn, whenever a creature an opponent "
                + "controls attacks, it gets -1/-0 until end of turn";
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
        game.addDelayedTriggeredAbility(delayedAbility, source);
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
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            getEffects().forEach((effect) -> {
                effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
            });
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
        return game.isActivePlayer(getControllerId())
                && game.getTurnNum() != startingTurn;
    }

    @Override
    public String getRule() {
        return "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.";
    }
}

class JaceArchitectOfThoughtEffect2 extends OneShotEffect {

    public JaceArchitectOfThoughtEffect2() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top three cards of your library. An opponent separates those cards "
                + "into two piles. Put one pile into your hand and the other on the bottom of your library in any order";
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

        Cards allCards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        player.revealCards(source, allCards, game);
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
            TargetCard target = new TargetCard(0, allCards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
            target.setNotTarget(true);
            opponent.choose(Outcome.Neutral, allCards, target, game);
            Cards pile1 = new CardsImpl(target.getTargets());
            Cards pile2 = new CardsImpl(allCards);
            pile2.removeAll(pile1);
            player.revealCards(source, "Pile 1", pile1, game);
            player.revealCards(source, "Pile 2", pile2, game);

            postPileToLog("Pile 1", pile1.getCards(game), game);
            postPileToLog("Pile 2", pile2.getCards(game), game);

            boolean pileChoice = player.choosePile(Outcome.Neutral, "Choose a pile to to put into your hand.",
                    new ArrayList<>(pile1.getCards(game)),
                    new ArrayList<>(pile2.getCards(game)), game);
            game.informPlayers(player.getLogName() + " chose pile" + (pileChoice ? "1" : "2"));
            player.moveCards(pileChoice ? pile1 : pile2, Zone.HAND, source, game);
            player.putCardsOnBottomOfLibrary(pileChoice ? pile2 : pile1, game, source, true);
            return true;
        }
        return false;
    }

    private void postPileToLog(String pileName, Set<Card> cards, Game game) {
        StringBuilder message = new StringBuilder(pileName).append(": ");
        cards.forEach((card) -> {
            message.append(card.getName()).append(' ');
        });
        if (cards.isEmpty()) {
            message.append(" (empty)");
        }
        game.informPlayers(message.toString());
    }
}

class JaceArchitectOfThoughtEffect3 extends OneShotEffect {

    public JaceArchitectOfThoughtEffect3() {
        super(Outcome.PlayForFree);
        this.staticText = "For each player, search that player's library for a nonland card and exile it, "
                + "then that player shuffles. You may cast those cards without paying their mana costs";
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
        if (controller.chooseUse(Outcome.Benefit, "Look at all players' libraries before card select?", null, game)) {
            game.informPlayers(controller.getLogName() + " is looking at all players' libraries.");
            controller.lookAtAllLibraries(source, game);
        }
        List<UUID> playerList = new ArrayList<>();
        playerList.addAll(game.getState().getPlayersInRange(controller.getId(), game));
        Set<UUID> checkList = new HashSet<>();
        while (!playerList.isEmpty()) {
            FilterPlayer filter = new FilterPlayer();
            List<PlayerIdPredicate> playerPredicates = new ArrayList<>();
            playerList.forEach((playerId) -> {
                playerPredicates.add(new PlayerIdPredicate(playerId));
            });
            filter.add(Predicates.or(playerPredicates));
            TargetPlayer targetPlayer = new TargetPlayer(1, 1, true, filter);
            targetPlayer.setRequired(!checkList.containsAll(playerList));
            if (controller.chooseTarget(outcome, targetPlayer, source, game)) {
                UUID playerId = targetPlayer.getFirstTarget();
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    String playerName = player.getLogName() + "'s";
                    if (source.isControlledBy(player.getId())) {
                        playerName = "your";
                    }
                    TargetCardInLibrary target = new TargetCardInLibrary(new FilterNonlandCard("nonland card from " + playerName + " library"));
                    if (controller.searchLibrary(target, source, game, playerId)) {
                        checkList.add(playerId);
                        UUID targetId = target.getFirstTarget();
                        Card card = player.getLibrary().remove(targetId, game);
                        if (card != null) {
                            controller.moveCardsToExile(card, source, game, true, CardUtil.getCardExileZoneId(game, source), sourcePermanent.getName());
                            playerList.remove(playerId);
                        }
                    } else {
                        playerList.remove(playerId);
                    }
                } else {
                    playerList.remove(playerId);
                }
            } else {
                break;
            }

            // remove disconnected or quit players
            playerList.removeIf(playerId -> game.getPlayer(playerId) == null || !game.getPlayer(playerId).canRespond());
        }
        checkList.stream().map((playerId) -> game.getPlayer(playerId)).filter((player) -> (player != null)).forEachOrdered((player) -> {
            player.shuffleLibrary(source, game);
        });
        ExileZone jaceExileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (jaceExileZone == null) {
            return true;
        }
        FilterCard filter = new FilterCard("card to cast without mana costs");
        TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
        Cards cardsToChoose = new CardsImpl(jaceExileZone.getCards(game));
        while (controller.canRespond()
                && cardsToChoose.count(filter, game) > 0
                && controller.chooseUse(Outcome.Benefit, "Cast another spell from exile zone for free?", source, game)) {
            controller.choose(Outcome.PlayForFree, cardsToChoose, target, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                cardsToChoose.remove(card);
                if (cardWasCast) {
                    game.getExile().removeCard(card, game);
                }
            }
            target.clearChosen();
        }
        return true;
    }
}
