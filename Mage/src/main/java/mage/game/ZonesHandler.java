package mage.game;

import mage.abilities.Ability;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
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

    public static boolean cast(ZoneChangeInfo info, Game game, Ability source) {
        if (maybeRemoveFromSourceZone(info, game, source)) {
            placeInDestinationZone(info, game, 0);
            // create a group zone change event if a card is moved to stack for casting (it's always only one card, but some effects check for group events (one or more xxx))
            Set<Card> cards = new HashSet<>();
            Set<PermanentToken> tokens = new HashSet<>();
            Card targetCard = getTargetCard(game, info.event.getTargetId());
            if (targetCard instanceof PermanentToken) {
                tokens.add((PermanentToken) targetCard);
            } else {
                cards.add(targetCard);
            }
            game.fireEvent(new ZoneChangeGroupEvent(
                    cards, 
                    tokens, 
                    info.event.getSourceId(), 
                    info.event.getSource(), 
                    info.event.getPlayerId(), 
                    info.event.getFromZone(), 
                    info.event.getToZone()));
            // normal movement
            game.fireEvent(info.event);
            return true;
        }
        return false;
    }

    public static boolean moveCard(ZoneChangeInfo info, Game game, Ability source) {
        List<ZoneChangeInfo> list = new ArrayList<>();
        list.add(info);
        return !moveCards(list, game, source).isEmpty();
    }

    public static List<ZoneChangeInfo> moveCards(List<ZoneChangeInfo> zoneChangeInfos, Game game, Ability source) {
        // Handle Unmelded Meld Cards
        for (ListIterator<ZoneChangeInfo> itr = zoneChangeInfos.listIterator(); itr.hasNext(); ) {
            ZoneChangeInfo info = itr.next();
            MeldCard card = game.getMeldCard(info.event.getTargetId());
            // Copies should be handled as normal cards.
            if (card != null && !card.isMelded(game) && !card.isCopy()) {
                ZoneChangeInfo.Unmelded unmelded = new ZoneChangeInfo.Unmelded(info, game);
                if (unmelded.subInfo.isEmpty()) {
                    itr.remove();
                } else {
                    itr.set(unmelded);
                }
            }
        }

        // process Modal Double Faces cards (e.g. put card from hand)
        // rules:
        // If an effect puts a double-faced card onto the battlefield, it enters with its front face up.
        // If that front face can’t be put onto the battlefield, it doesn’t enter the battlefield.
        // For example, if an effect exiles Sejiri Glacier and returns it to the battlefield,
        // it remains in exile because an instant can’t be put onto the battlefield.
        for (ListIterator<ZoneChangeInfo> itr = zoneChangeInfos.listIterator(); itr.hasNext(); ) {
            ZoneChangeInfo info = itr.next();
            if (info.event.getToZone().equals(Zone.BATTLEFIELD)) {
                Card card = game.getCard(info.event.getTargetId());
                if (card instanceof ModalDoubleFacesCard || card instanceof ModalDoubleFacesCardHalf) {
                    boolean forceToMainSide = false;

                    // if effect put half mdf card to battlefield then it must be the main side only (example: return targeted half card to battle)
                    if (card instanceof ModalDoubleFacesCardHalf && !source.getAbilityType().isPlayCardAbility()) {
                        forceToMainSide = true;
                    }

                    // if effect put mdf card to battlefield then it must be main side only
                    if (card instanceof ModalDoubleFacesCard) {
                        forceToMainSide = true;
                    }

                    if (forceToMainSide) {
                        info.event.setTargetId(((ModalDoubleFacesCard) card.getMainCard()).getLeftHalfCard().getId());
                    }
                }
            }
        }

        zoneChangeInfos.removeIf(zoneChangeInfo -> !maybeRemoveFromSourceZone(zoneChangeInfo, game, source));
        int createOrder = 0;
        for (ZoneChangeInfo zoneChangeInfo : zoneChangeInfos) {
            if (createOrder == 0 && Zone.BATTLEFIELD.equals(zoneChangeInfo.event.getToZone())) {
                // All permanents go to battlefield at the same time (=create order)
                createOrder = game.getState().getNextPermanentOrderNumber();
            }
            placeInDestinationZone(zoneChangeInfo, game, createOrder);
            if (game.getPhase() != null) { // moving cards to zones before game started does not need events
                game.addSimultaneousEvent(zoneChangeInfo.event);
            }
        }
        return zoneChangeInfos;
    }

    private static void placeInDestinationZone(ZoneChangeInfo info, Game game, int createOrder) {
        // Handle unmelded cards
        if (info instanceof ZoneChangeInfo.Unmelded) {
            ZoneChangeInfo.Unmelded unmelded = (ZoneChangeInfo.Unmelded) info;
            Zone toZone = null;
            for (ZoneChangeInfo subInfo : unmelded.subInfo) {
                toZone = subInfo.event.getToZone();
                placeInDestinationZone(subInfo, game, createOrder);
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

        Cards cardsToMove = null; // moving real cards
        Map<Zone, Cards> cardsToUpdate = new LinkedHashMap<>(); // updating all card's parts (must be ordered LinkedHashMap)
        cardsToUpdate.put(toZone, new CardsImpl());
        cardsToUpdate.put(Zone.OUTSIDE, new CardsImpl());
        // if we're moving a token it shouldn't be put into any zone as an object.
        if (!(targetCard instanceof Permanent) && targetCard != null) {
            if (targetCard instanceof MeldCard) {
                // meld/group cards must be independent (use can choose order)
                cardsToMove = ((MeldCard) targetCard).getHalves();
                cardsToUpdate.get(toZone).addAll(cardsToMove);
            } else if (targetCard instanceof ModalDoubleFacesCard
                    || targetCard instanceof ModalDoubleFacesCardHalf) {
                // mdf cards must be moved as single object, but each half must be updated separately
                ModalDoubleFacesCard mdfCard = (ModalDoubleFacesCard) targetCard.getMainCard();
                cardsToMove = new CardsImpl(mdfCard);
                cardsToUpdate.get(toZone).add(mdfCard);
                // example: cast left side
                // result:
                // * main to battlefield
                // * left to battlefield
                // * right to outside (it helps to ignore all triggers and other effects from that card)
                switch (toZone) {
                    case STACK:
                    case BATTLEFIELD:
                        if (targetCard.getId().equals(mdfCard.getLeftHalfCard().getId())) {
                            // play left
                            cardsToUpdate.get(toZone).add(mdfCard.getLeftHalfCard());
                            cardsToUpdate.get(Zone.OUTSIDE).add(mdfCard.getRightHalfCard());
                        } else if (targetCard.getId().equals(mdfCard.getRightHalfCard().getId())) {
                            // play right
                            cardsToUpdate.get(toZone).add(mdfCard.getRightHalfCard());
                            cardsToUpdate.get(Zone.OUTSIDE).add(mdfCard.getLeftHalfCard());
                        } else {
                            // cast mdf (only on stack)
                            if (!toZone.equals(Zone.STACK)) {
                                throw new IllegalStateException("Wrong mdf card move to " + toZone + " in placeInDestinationZone");
                            }
                            cardsToUpdate.get(toZone).add(mdfCard.getLeftHalfCard());
                            cardsToUpdate.get(toZone).add(mdfCard.getRightHalfCard());
                        }
                        break;
                    default:
                        // move all parts
                        cardsToUpdate.get(toZone).add(mdfCard.getLeftHalfCard());
                        cardsToUpdate.get(toZone).add(mdfCard.getRightHalfCard());
                        break;
                }
            } else {
                cardsToMove = new CardsImpl(targetCard);
                cardsToUpdate.get(toZone).addAll(cardsToMove);
            }

            Player owner = game.getPlayer(targetCard.getOwnerId());
            switch (toZone) {
                case HAND:
                    for (Card card : cardsToMove.getCards(game)) {
                        game.getPlayer(card.getOwnerId()).getHand().add(card);
                    }
                    break;
                case GRAVEYARD:
                    for (Card card : chooseOrder(
                            "order to put in graveyard (last chosen will be on top)", cardsToMove, owner, game)) {
                        game.getPlayer(card.getOwnerId()).getGraveyard().add(card);
                    }
                    break;
                case LIBRARY:
                    if (info instanceof ZoneChangeInfo.Library && ((ZoneChangeInfo.Library) info).top) {
                        // on top
                        for (Card card : chooseOrder(
                                "order to put on top of library (last chosen will be topmost)", cardsToMove, owner, game)) {
                            game.getPlayer(card.getOwnerId()).getLibrary().putOnTop(card, game);
                        }
                    } else {
                        // on bottom
                        for (Card card : chooseOrder(
                                "order to put on bottom of library (last chosen will be bottommost)", cardsToMove, owner, game)) {
                            game.getPlayer(card.getOwnerId()).getLibrary().putOnBottom(card, game);
                        }
                    }
                    break;
                case EXILED:
                    for (Card card : cardsToMove.getCards(game)) {
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
                    for (Card card : cardsToMove.getCards(game)) {
                        game.addCommander(new Commander(card));
                    }
                    break;
                case STACK:
                    // There should never be more than one card here.
                    for (Card card : cardsToMove.getCards(game)) {
                        Spell spell;
                        if (info instanceof ZoneChangeInfo.Stack && ((ZoneChangeInfo.Stack) info).spell != null) {
                            spell = ((ZoneChangeInfo.Stack) info).spell;
                        } else {
                            spell = new Spell(card, card.getSpellAbility().copy(), card.getOwnerId(), event.getFromZone(), game);
                        }
                        spell.syncZoneChangeCounterOnStack(card, game);
                        game.getStack().push(spell);
                        game.getState().setZone(spell.getId(), Zone.STACK);
                        game.getState().setZone(card.getId(), Zone.STACK);
                    }
                    break;
                case BATTLEFIELD:
                    Permanent permanent = event.getTarget();
                    game.addPermanent(permanent, createOrder);
                    game.getPermanentsEntering().remove(permanent.getId());
                    break;
                default:
                    throw new UnsupportedOperationException("to Zone " + toZone.toString() + " not supported yet");
            }
        }

        // update zone in main
        game.setZone(event.getTargetId(), event.getToZone());

        // update zone in other parts (meld cards, mdf half cards)
        cardsToUpdate.entrySet().forEach(entry -> {
            for (Card card : entry.getValue().getCards(game)) {
                if (!card.getId().equals(event.getTargetId())) {
                    game.setZone(card.getId(), entry.getKey());
                }
            }
        });

        // reset meld status
        if (targetCard instanceof MeldCard) {
            if (event.getToZone() != Zone.BATTLEFIELD) {
                ((MeldCard) targetCard).setMelded(false, game);
            }
        }
    }

    public static Card getTargetCard(Game game, UUID targetId) {
        Card card = game.getCard(targetId);
        if (card != null) {
            return card;
        }

        card = game.getMeldCard(targetId);
        if (card != null) {
            return card;
        }

        return game.getPermanent(targetId);
    }

    private static boolean maybeRemoveFromSourceZone(ZoneChangeInfo info, Game game, Ability source) {
        ZoneChangeEvent event = info.event;

        // Handle Unmelded Cards
        if (info instanceof ZoneChangeInfo.Unmelded) {
            ZoneChangeInfo.Unmelded unmelded = (ZoneChangeInfo.Unmelded) info;
            MeldCard meld = game.getMeldCard(event.getTargetId());
            for (Iterator<ZoneChangeInfo> itr = unmelded.subInfo.iterator(); itr.hasNext(); ) {
                ZoneChangeInfo subInfo = itr.next();
                if (!maybeRemoveFromSourceZone(subInfo, game, source)) {
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
        Card card = getTargetCard(game, event.getTargetId());
        if (card == null) {
            // If we can't find the card we can't remove it.
            return false;
        }

        boolean success = false;
        if (info.faceDown) {
            card.setFaceDown(true, game);
        } else if (event.getToZone().equals(Zone.BATTLEFIELD)) {
            if (!card.isPermanent(game)
                    && (!card.isTransformable() || Boolean.FALSE.equals(game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId())))) {
                // Non permanents (Instants, Sorceries, ... stay in the zone they are if an abilty/effect tries to move it to the battlefield
                return false;
            }
        }

        if (!game.replaceEvent(event)) {
            Zone fromZone = event.getFromZone();
            if (event.getToZone() == Zone.BATTLEFIELD) {
                // prepare card and permanent
                // If needed take attributes from the spell (e.g. color of spell was changed)
                card = takeAttributesFromSpell(card, event, game);
                // controlling player can be replaced so use event player now
                Permanent permanent;
                if (card instanceof MeldCard) {
                    permanent = new PermanentMeld(card, event.getPlayerId(), game);
                } else if (card instanceof ModalDoubleFacesCard) {
                    // main mdf card must be processed before that call (e.g. only halfes can be moved to battlefield)
                    throw new IllegalStateException("Unexpected trying of move mdf card to battlefield instead half");
                } else if (card instanceof Permanent) {
                    throw new IllegalStateException("Unexpected trying of move permanent to battlefield instead card");
                } else {
                    permanent = new PermanentCard(card, event.getPlayerId(), game);
                }

                // put onto battlefield with possible counters
                game.getPermanentsEntering().put(permanent.getId(), permanent);
                card.checkForCountersToAdd(permanent, source, game);
                permanent.setTapped(info instanceof ZoneChangeInfo.Battlefield
                        && ((ZoneChangeInfo.Battlefield) info).tapped);

                permanent.setFaceDown(info.faceDown, game);
                if (info.faceDown) {
                    card.setFaceDown(false, game);
                }

                // make sure the controller of all continuous effects of this card are switched to the current controller
                game.setScopeRelevant(true);
                game.getContinuousEffects().setController(permanent.getId(), permanent.getControllerId());
                if (permanent.entersBattlefield(source, game, fromZone, true)
                        && card.removeFromZone(game, fromZone, source)) {
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
                success = target.removeFromZone(game, fromZone, source)
                        && game.getPlayer(target.getControllerId()).removeFromBattlefield(target, source, game);
            } else {
                card.setFaceDown(info.faceDown, game);
                success = card.removeFromZone(game, fromZone, source);
            }
        }
        if (success) {
            // change ZCC on real enter
            // warning, tokens creation code uses same zcc logic as cards (+1 zcc on enter to battlefield)
            // so if you want to change zcc logic here (but I know you don't) then change token code
            // too in TokenImpl.putOntoBattlefieldHelper
            // KickerTest do many tests for token's zcc
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
        if (cards.isEmpty()) {
            return order;
        }
        TargetCard target = new TargetCard(Zone.ALL, new FilterCard(message));
        target.setRequired(true);
        while (player.canRespond() && cards.size() > 1) {
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
