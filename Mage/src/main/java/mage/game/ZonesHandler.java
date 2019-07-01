package mage.game;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.command.Commander;
import mage.game.events.ZoneChangeEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentMeld;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.*;

/**
 * Created by samuelsandeen on 9/6/16.
 */
public final class ZonesHandler {

    public static boolean cast(ZoneChangeInfo info, Game game) {
        if (maybeRemoveFromSourceZone(info, game)) {
            placeInDestinationZone(info, game);
            // create a group zone change event if a card is moved to stack for casting (it's always only one card, but some effects check for group events (one or more xxx))
            Set<Card> cards = new HashSet<>();
            Set<PermanentToken> tokens = new HashSet<>();
            Card targetCard = getTargetCard(game, info.event.getTargetId());
            if (targetCard instanceof PermanentToken) {
                tokens.add((PermanentToken) targetCard);
            } else {
                cards.add(targetCard);
            }
            game.fireEvent(new ZoneChangeGroupEvent(cards, tokens, info.event.getSourceId(), info.event.getPlayerId(), info.event.getFromZone(), info.event.getToZone()));
            // normal movement
            game.fireEvent(info.event);
            return true;
        }
        return false;
    }

    public static boolean moveCard(ZoneChangeInfo info, Game game) {
        List<ZoneChangeInfo> list = new ArrayList<>();
        list.add(info);
        return !moveCards(list, game).isEmpty();
    }

    public static List<ZoneChangeInfo> moveCards(List<ZoneChangeInfo> zoneChangeInfos, Game game) {
        // Handle Unmelded Meld Cards
        for (ListIterator<ZoneChangeInfo> itr = zoneChangeInfos.listIterator(); itr.hasNext(); ) {
            ZoneChangeInfo info = itr.next();
            MeldCard card = game.getMeldCard(info.event.getTargetId());
            // Copies should be handled as normal cards.
            if (card != null && !card.isMelded() && !card.isCopy()) {
                ZoneChangeInfo.Unmelded unmelded = new ZoneChangeInfo.Unmelded(info, game);
                if (unmelded.subInfo.isEmpty()) {
                    itr.remove();
                } else {
                    itr.set(unmelded);
                }
            }
        }
        zoneChangeInfos.removeIf(zoneChangeInfo -> !maybeRemoveFromSourceZone(zoneChangeInfo, game));
        for (ZoneChangeInfo zoneChangeInfo : zoneChangeInfos) {
            placeInDestinationZone(zoneChangeInfo, game);
            if (game.getPhase() != null) { // moving cards to zones before game started does not need events
                game.addSimultaneousEvent(zoneChangeInfo.event);
            }
        }
        return zoneChangeInfos;
    }

    private static void placeInDestinationZone(ZoneChangeInfo info, Game game) {
        // Handle unmelded cards
        if (info instanceof ZoneChangeInfo.Unmelded) {
            ZoneChangeInfo.Unmelded unmelded = (ZoneChangeInfo.Unmelded) info;
            Zone toZone = null;
            for (ZoneChangeInfo subInfo : unmelded.subInfo) {
                toZone = subInfo.event.getToZone();
                placeInDestinationZone(subInfo, game);
            }
            // We arbitrarily prefer the bottom half card. This should never be relevant.
            if (toZone != null) {
                game.setZone(unmelded.event.getTargetId(), toZone);
            }
            return;
        }
        // Handle normal cases
        ZoneChangeEvent event = info.event;
        Zone toZone = event.getToZone();
        Card targetCard = getTargetCard(game, event.getTargetId());
        Cards cards = null;
        // If we're moving a token it shouldn't be put into any zone as an object.
        if (!(targetCard instanceof Permanent) && targetCard != null) {
            if (targetCard instanceof MeldCard) {
                cards = ((MeldCard) targetCard).getHalves();
            } else {
                cards = new CardsImpl(targetCard);
            }
            Player owner = game.getPlayer(targetCard.getOwnerId());
            switch (toZone) {
                case HAND:
                    for (Card card : cards.getCards(game)) {
                        game.getPlayer(card.getOwnerId()).getHand().add(card);
                    }
                    break;
                case GRAVEYARD:
                    for (Card card : chooseOrder(
                            "order to put in graveyard (last chosen will be on top)", cards, owner, game)) {
                        game.getPlayer(card.getOwnerId()).getGraveyard().add(card);
                    }
                    break;
                case LIBRARY:
                    if (info instanceof ZoneChangeInfo.Library && ((ZoneChangeInfo.Library) info).top) {
                        for (Card card : chooseOrder(
                                "order to put on top of library (last chosen will be topmost)", cards, owner, game)) {
                            game.getPlayer(card.getOwnerId()).getLibrary().putOnTop(card, game);
                        }
                    } else { // buttom
                        for (Card card : chooseOrder(
                                "order to put on bottom of library (last chosen will be bottommost)", cards, owner, game)) {
                            game.getPlayer(card.getOwnerId()).getLibrary().putOnBottom(card, game);
                        }
                    }
                    break;
                case EXILED:
                    for (Card card : cards.getCards(game)) {
                        if (info instanceof ZoneChangeInfo.Exile && ((ZoneChangeInfo.Exile) info).id != null) {
                            ZoneChangeInfo.Exile exileInfo = (ZoneChangeInfo.Exile) info;
                            game.getExile().createZone(exileInfo.id, exileInfo.name).add(card);
                        } else {
                            game.getExile().getPermanentExile().add(card);
                        }
                    }
                    break;
                case COMMAND:
                    // There should never be more than one card here.
                    for (Card card : cards.getCards(game)) {
                        game.addCommander(new Commander(card));
                    }
                    break;
                case STACK:
                    // There should never be more than one card here.
                    for (Card card : cards.getCards(game)) {
                        Spell spell;
                        if (info instanceof ZoneChangeInfo.Stack && ((ZoneChangeInfo.Stack) info).spell != null) {
                            spell = ((ZoneChangeInfo.Stack) info).spell;
                        } else {
                            spell = new Spell(card, card.getSpellAbility().copy(), card.getOwnerId(), event.getFromZone());
                        }
                        game.getStack().push(spell);
                        game.getState().setZone(spell.getId(), Zone.STACK);
                        game.getState().setZone(card.getId(), Zone.STACK);
                    }
                    break;
                case BATTLEFIELD:
                    Permanent permanent = event.getTarget();
                    game.addPermanent(permanent);
                    game.getPermanentsEntering().remove(permanent.getId());
                    break;
                default:
                    throw new UnsupportedOperationException("to Zone" + toZone.toString() + " not supported yet");
            }
        }
        game.setZone(event.getTargetId(), event.getToZone());
        if (targetCard instanceof MeldCard && cards != null) {
            if (event.getToZone() != Zone.BATTLEFIELD) {
                ((MeldCard) targetCard).setMelded(false);
            }
            for (Card card : cards.getCards(game)) {
                game.setZone(card.getId(), event.getToZone());
            }
        }
    }

    public static Card getTargetCard(Game game, UUID targetId) {
        if (game.getCard(targetId) != null) {
            return game.getCard(targetId);
        }
        if (game.getMeldCard(targetId) != null) {
            return game.getMeldCard(targetId);
        }
        if (game.getPermanent(targetId) != null) {
            return game.getPermanent(targetId);
        }
        return null;
    }

    private static boolean maybeRemoveFromSourceZone(ZoneChangeInfo info, Game game) {
        // Handle Unmelded Cards
        if (info instanceof ZoneChangeInfo.Unmelded) {
            ZoneChangeInfo.Unmelded unmelded = (ZoneChangeInfo.Unmelded) info;
            MeldCard meld = game.getMeldCard(info.event.getTargetId());
            for (Iterator<ZoneChangeInfo> itr = unmelded.subInfo.iterator(); itr.hasNext(); ) {
                ZoneChangeInfo subInfo = itr.next();
                if (!maybeRemoveFromSourceZone(subInfo, game)) {
                    itr.remove();
                } else if (Objects.equals(subInfo.event.getTargetId(), meld.getTopHalfCard().getId())) {
                    meld.setTopLastZoneChangeCounter(meld.getTopHalfCard().getZoneChangeCounter(game));
                } else if (Objects.equals(subInfo.event.getTargetId(), meld.getBottomHalfCard().getId())) {
                    meld.setBottomLastZoneChangeCounter(meld.getBottomHalfCard().getZoneChangeCounter(game));
                }
            }
            if (unmelded.subInfo.isEmpty()) {
                return false;
            }
            // We arbitrarily prefer the bottom half card. This should never be relevant.
            meld.updateZoneChangeCounter(game, unmelded.subInfo.get(unmelded.subInfo.size() - 1).event);
            return true;
        }
        // Handle all normal cases
        ZoneChangeEvent event = info.event;
        Card card = getTargetCard(game, event.getTargetId());
        if (card == null) {
            // If we can't find the card we can't remove it.
            return false;
        }

        boolean success = false;
        if (info.faceDown) {
            card.setFaceDown(true, game);
        }
        if (!game.replaceEvent(event)) {
            Zone fromZone = event.getFromZone();
            if (event.getToZone() == Zone.BATTLEFIELD) {
                // If needed take attributes from the spell (e.g. color of spell was changed)
                card = takeAttributesFromSpell(card, event, game);
                // controlling player can be replaced so use event player now
                Permanent permanent;
                if (card instanceof MeldCard) {
                    permanent = new PermanentMeld(card, event.getPlayerId(), game);
                } else if (card instanceof Permanent) {
                    // This should never happen.
                    permanent = (Permanent) card;
                } else {
                    permanent = new PermanentCard(card, event.getPlayerId(), game);
                }
                game.getPermanentsEntering().put(permanent.getId(), permanent);
                card.checkForCountersToAdd(permanent, game);
                permanent.setTapped(
                        info instanceof ZoneChangeInfo.Battlefield && ((ZoneChangeInfo.Battlefield) info).tapped);
                permanent.setFaceDown(info.faceDown, game);

                if (info.faceDown) {
                    card.setFaceDown(false, game);
                }
                // make sure the controller of all continuous effects of this card are switched to the current controller
                game.setScopeRelevant(true);
                game.getContinuousEffects().setController(permanent.getId(), permanent.getControllerId());
                if (permanent.entersBattlefield(event.getSourceId(), game, fromZone, true)
                        && card.removeFromZone(game, fromZone, event.getSourceId())) {
                    success = true;
                    event.setTarget(permanent);
                } else {
                    // revert controller to owner if permanent does not enter
                    game.getContinuousEffects().setController(permanent.getId(), permanent.getOwnerId());
                    game.getPermanentsEntering().remove(permanent.getId());
                }
                game.setScopeRelevant(false);
            } else if (event.getTarget() != null) {
                card.setFaceDown(info.faceDown, game);
                Permanent target = event.getTarget();
                success = game.getPlayer(target.getControllerId()).removeFromBattlefield(target, game)
                        && target.removeFromZone(game, fromZone, event.getSourceId());
            } else {
                card.setFaceDown(info.faceDown, game);
                success = card.removeFromZone(game, fromZone, event.getSourceId());
            }
        }
        if (success) {
            if (event.getToZone() == Zone.BATTLEFIELD && event.getTarget() != null) {
                event.getTarget().updateZoneChangeCounter(game, event);
            } else if (!(card instanceof Permanent)) {
                card.updateZoneChangeCounter(game, event);
            }
        }
        return success;
    }

    public static List<Card> chooseOrder(String message, Cards cards, Player player, Game game) {
        List<Card> order = new ArrayList<>();
        TargetCard target = new TargetCard(Zone.ALL, new FilterCard(message));
        target.setRequired(true);
        while (player.isInGame() && cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            UUID targetObjectId = target.getFirstTarget();
            order.add(cards.get(targetObjectId, game));
            cards.remove(targetObjectId);
            target.clearChosen();
        }
        order.add(cards.getCards(game).iterator().next());
        return order;
    }

    private static Card takeAttributesFromSpell(Card card, ZoneChangeEvent event, Game game) {
        card = card.copy();
        if (Zone.STACK == event.getFromZone()) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && !spell.isFaceDown(game)) {
                if (!card.getColor(game).equals(spell.getColor(game))) {
                    // the card that is referenced to in the permanent is copied and the spell attributes are set to this copied card
                    card.getColor(game).setColor(spell.getColor(game));
                }
            }
        }
        return card;
    }

}
