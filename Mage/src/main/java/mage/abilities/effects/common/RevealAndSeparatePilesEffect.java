package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class RevealAndSeparatePilesEffect extends OneShotEffect {
    private static final FilterCard filter = new FilterCard("cards to put in the first pile");

    private final DynamicValue amount;
    private final TargetController playerWhoSeparates;
    private final TargetController playerWhoChooses;
    private final Zone targetZone;
    private final boolean anyOrder;

    public RevealAndSeparatePilesEffect(int amount, TargetController playerWhoSeparates, TargetController playerWhoChooses, Zone targetZone) {
        this(StaticValue.get(amount), playerWhoSeparates, playerWhoChooses, targetZone);
    }

    public RevealAndSeparatePilesEffect(DynamicValue amount, TargetController playerWhoSeparates, TargetController playerWhoChooses, Zone targetZone) {
        this(amount, playerWhoSeparates, playerWhoChooses, targetZone, true);
    }

    public RevealAndSeparatePilesEffect(DynamicValue amount, TargetController playerWhoSeparates, TargetController playerWhoChooses, Zone targetZone, boolean anyOrder) {
        super(Outcome.DrawCard);
        this.amount = amount;
        this.playerWhoSeparates = playerWhoSeparates;
        this.playerWhoChooses = playerWhoChooses;
        this.targetZone = targetZone;
        this.anyOrder = anyOrder;
        this.staticText = this.generateText();
    }

    private RevealAndSeparatePilesEffect(final RevealAndSeparatePilesEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.playerWhoSeparates = effect.playerWhoSeparates;
        this.playerWhoChooses = effect.playerWhoChooses;
        this.targetZone = effect.targetZone;
        this.anyOrder = effect.anyOrder;
    }

    @Override
    public RevealAndSeparatePilesEffect copy() {
        return new RevealAndSeparatePilesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int toReveal = amount.calculate(game, source, this);
        if (controller == null || toReveal < 1) {
            return false;
        }
        return doPiles(controller, source, game, toReveal);
    }

    private static Player getExecutingPlayer(Player controller, Game game, Ability source, TargetController targetController, String message) {
        switch (targetController) {
            case YOU:
                return controller;
            case OPPONENT:
                Player opponent;
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                if (opponents.isEmpty()) {
                    return null;
                }
                if (opponents.size() == 1) {
                    opponent = game.getPlayer(opponents.iterator().next());
                } else {
                    Target targetOpponent = new TargetOpponent(true);
                    controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game);
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to " + message);
                }
                return opponent;
        }
        return null;
    }

    private boolean doPiles(Player controller, Ability source, Game game, int toReveal) {
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, toReveal));
        controller.revealCards(source, cards, game);

        Player separatingPlayer = this.getExecutingPlayer(controller, game, source, playerWhoSeparates, "separate the revealed cards");
        TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, filter);
        List<Card> pile1 = new ArrayList<>();
        separatingPlayer.choose(Outcome.Neutral, cards, target, game);
        target.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(pile1::add);
        cards.removeIf(target.getTargets()::contains);
        List<Card> pile2 = new ArrayList<>();
        pile2.addAll(cards.getCards(game));

        Player choosingPlayer = this.getExecutingPlayer(controller, game, source, playerWhoChooses, "choose the piles");
        boolean choice = choosingPlayer.choosePile(outcome, "Choose a pile to put into " + targetZone + ".", pile1, pile2, game);

        Zone pile1Zone = choice ? targetZone : Zone.HAND;
        Zone pile2Zone = choice ? Zone.HAND : targetZone;

        game.informPlayers("Pile 1, going to " + pile1Zone + ": " + (pile1.isEmpty() ? " (none)" : pile1.stream().map(MageObject::getName).collect(Collectors.joining(", "))));
        cards.clear();
        cards.addAll(pile1);
        if (pile1Zone == Zone.LIBRARY) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
        } else {
            controller.moveCards(cards, pile1Zone, source, game);
        }

        game.informPlayers("Pile 2, going to " + pile2Zone + ": " + (pile2.isEmpty() ? " (none)" : pile2.stream().map(MageObject::getName).collect(Collectors.joining(", "))));
        cards.clear();
        cards.addAll(pile2);
        if (pile2Zone == Zone.LIBRARY) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
        } else {
            controller.moveCards(cards, pile2Zone, source, game);
        }
        return true;
    }

    private String generateText() {
        StringBuilder sb = new StringBuilder("reveal the top ");
        if (amount instanceof StaticValue) {
            sb.append(CardUtil.numberToText(((StaticValue) amount).getValue()));
            sb.append(" cards of your library");
        } else {
            sb.append("X cards of your library, where X is the number of ");
            sb.append(amount.getMessage());
        }
        switch (playerWhoSeparates) {
            case YOU:
                sb.append(" and separate them");
                break;
            case OPPONENT:
                sb.append(". An opponent separates those cards");
                break;
        }
        sb.append(" into two piles. ");
        switch (playerWhoChooses) {
            case YOU:
                sb.append("Put one pile into your hand and the other ");
                break;
            case OPPONENT:
                sb.append("An opponent chooses one of those piles. Put that pile into your hand and the other ");
                break;
        }
        switch (targetZone) {
            case GRAVEYARD:
                sb.append("into your graveyard");
                break;
            case LIBRARY:
                sb.append("on the bottom of your library in a");
                sb.append(anyOrder ? "ny" : " random");
                sb.append(" order");
                break;
        }
        return sb.toString();
    }
}
