package mage.game;

import mage.abilities.Ability;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.command.Commander;
import mage.game.events.ZoneChangeEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.FuzzyTestsUtil;

import java.util.*;

/**
 * Created by samuelsandeen on 9/6/16.
 */
public final class ZonesHandler {

    public static boolean cast(ZoneChangeInfo info, Ability source, Game game) {
        if (maybeRemoveFromSourceZone(info, game, source)) {
            placeInDestinationZone(info, 0, source, game);
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
        return !moveCards(list, source, game).isEmpty();
    }

    public static List<ZoneChangeInfo> moveCards(List<ZoneChangeInfo> zoneChangeInfos, Ability source, Game game) {
        // handle unmelded meld cards (if something moved a melded card to non-battlefield then parts must be moved too)
        for (ListIterator<ZoneChangeInfo> itr = zoneChangeInfos.listIterator(); itr.hasNext(); ) {
            ZoneChangeInfo info = itr.next();
            if (info.event.getToZone().equals(Zone.BATTLEFIELD)) {
                continue;
            }
            Card card = game.getCard(info.event.getTargetId());
            // Copies should be handled as normal cards.
            if (card != null && card.getMeldedWith(game) != null && !card.isCopy()) {
                MeldCardHalf meldHalf = (MeldCardHalf) card;
                ZoneChangeEvent event = info.event;
                // melded with card
                ZoneChangeEvent meldedEvent = new ZoneChangeEvent(card.getMeldedWith(game).getId(), event.getSource(),
                        event.getPlayerId(), event.getFromZone(), event.getToZone(), event.getAppliedEffects());
                ZoneChangeInfo meldedInfo = info.copy();
                meldedInfo.event = meldedEvent;
                itr.add(meldedInfo);
                // reset meld status
                card.setMeldedWith(null, game);
            }
        }

        // process Modal Double Faces cards (e.g. put card from hand)
        // rules:
        // If an effect puts a double-faced card onto the battlefield, it enters with its front face up.
        // If that front face can’t be put onto the battlefield, it doesn’t enter the battlefield.
        // For example, if an effect exiles Sejiri Glacier and returns it to the battlefield,
        // it remains in exile because an instant can’t be put onto the battlefield.
        for (ZoneChangeInfo info : zoneChangeInfos) {
            if (info.event.getToZone().equals(Zone.BATTLEFIELD)) {
                Card card = game.getCard(info.event.getTargetId());
                if (card.getIdForBattlefield(game, source) == null) {
                    continue;
                }
                info.event.setTargetId(card.getIdForBattlefield(game, source));
            }
        }

        zoneChangeInfos.removeIf(zoneChangeInfo -> !maybeRemoveFromSourceZone(zoneChangeInfo, game, source));
        int createOrder = 0;
        for (ZoneChangeInfo zoneChangeInfo : zoneChangeInfos) {
            if (createOrder == 0 && Zone.BATTLEFIELD.equals(zoneChangeInfo.event.getToZone())) {
                // All permanents go to battlefield at the same time (=create order)
                createOrder = game.getState().getNextPermanentOrderNumber();
            }
            placeInDestinationZone(zoneChangeInfo, createOrder, source, game);
            if (game.getPhase() != null) { // moving cards to zones before game started does not need events
                game.addSimultaneousEvent(zoneChangeInfo.event);
            }
        }
        return zoneChangeInfos;
    }

    private static void placeInDestinationZone(ZoneChangeInfo info, int createOrder, Ability source, Game game) {
        ZoneChangeEvent event = info.event;
        Zone toZone = event.getToZone();
        Card targetCard = getTargetCard(game, event.getTargetId());
        if (targetCard == null) {
            game.setZone(event.getTargetId(), toZone);
            return;
        }
        if (!(targetCard instanceof Permanent) && toZone != Zone.BATTLEFIELD && toZone != Zone.STACK) {
            // use main card for zones other than battlefield/stack
            targetCard = targetCard.getMainCard();
        }
        switch (toZone) {
            case HAND:
                game.getPlayer(targetCard.getOwnerId()).getHand().add(targetCard);
                break;
            case GRAVEYARD:
                game.getPlayer(targetCard.getOwnerId()).getGraveyard().add(targetCard);
                break;
            case LIBRARY:
                if (info instanceof ZoneChangeInfo.Library && ((ZoneChangeInfo.Library) info).top) {
                    // on top
                    game.getPlayer(targetCard.getOwnerId()).getLibrary().putOnTop(targetCard, game);
                } else {
                    // on bottom
                    game.getPlayer(targetCard.getOwnerId()).getLibrary().putOnBottom(targetCard, game);
                }
                break;
            case EXILED:
                if (info instanceof ZoneChangeInfo.Exile && ((ZoneChangeInfo.Exile) info).id != null) {
                    ZoneChangeInfo.Exile exileInfo = (ZoneChangeInfo.Exile) info;
                    game.getExile().createZone(exileInfo.id, exileInfo.name).add(targetCard);
                } else {
                    game.getExile().getPermanentExile().add(targetCard);
                }
                break;
            case COMMAND:
                game.addCommander(new Commander(targetCard));
                break;
            case STACK:
                Spell spell;
                if (info instanceof ZoneChangeInfo.Stack && ((ZoneChangeInfo.Stack) info).spell != null) {
                    spell = ((ZoneChangeInfo.Stack) info).spell;
                } else {
                    spell = new Spell(targetCard, targetCard.getSpellAbility().copy(), targetCard.getOwnerId(), event.getFromZone(), game);
                }
                spell.syncZoneChangeCounterOnStack(targetCard, game);
                game.getStack().push(game, spell);
                targetCard = spell;
                break;
            case BATTLEFIELD:
                Permanent permanent = event.getTarget();
                game.addPermanent(permanent, createOrder);
                game.getPermanentsEntering().remove(permanent.getId());
                break;
            default:
                throw new UnsupportedOperationException("to Zone " + toZone + " not supported yet");
        }
        // update zone in main
        targetCard.setZone(toZone, game);
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

        // Handle all normal cases
        Card card = getTargetCard(game, event.getTargetId());
        if (card == null) {
            // if we can't find the card we can't remove it.
            return false;
        }

        boolean isGoodToMove = false;
        if (info.faceDown) {
            // any card can be moved as face down (doubled faced cards also support face down)
            isGoodToMove = true;
        } else if (event.getToZone().equals(Zone.BATTLEFIELD)) {
            // non-permanents can't move to battlefield
            // TODO: possible bug with Nightbound, search all usage of getValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED and insert additional check Ability.checkCard
            /*
             * 712.14a. If a spell or ability puts a double-faced card onto the battlefield "transformed" or "converted,"
             * it enters the battlefield with its back face up. If a player is instructed to put a card that isn't a double-faced card
             * onto the battlefield transformed or converted, that card stays in its current zone.
             */
            boolean wantToTransform = Boolean.TRUE.equals(game.getState().getValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + card.getId() + card.getZoneChangeCounter(game)));
            if (wantToTransform && !(card instanceof DoubleFacedCardHalf)) {
                isGoodToMove = card.isTransformable() && card.getSecondCardFace().isPermanent(game);
            } else {
                isGoodToMove = card.isPermanent(game);
            }
        } else {
            // other zones allows to move
            isGoodToMove = true;
        }
        if (!isGoodToMove) {
            return false;
        }

        // TODO: is it buggy? Card characteristics are global - if you change face down then it will be
        //  changed in original card too, not in blueprint only
        card.setFaceDown(info.faceDown, game);

        boolean success = false;
        if (!game.replaceEvent(event)) {
            Zone fromZone = event.getFromZone();
            if (event.getToZone() == Zone.BATTLEFIELD) {
                // PUT TO BATTLEFIELD AS PERMANENT
                // prepare card and permanent (card must contain full data, even for face down)
                // if needed to take attributes from the spell (e.g. color of spell was changed)
                card = prepareBlueprintCardFromSpell(card, event, game);

                // controlling player can be replaced so use event player now
                Permanent permanent;
                if (card instanceof RoomCardHalf) {
                    // Only the main room card can etb
                    permanent = new PermanentCard(card.getMainCard(), event.getPlayerId(), game);
                } else if (card instanceof DoubleFacedCard) {
                    // main mdf card must be processed before that call (e.g. only halves can be moved to battlefield)
                    throw new IllegalStateException("Unexpected trying of move mdf card to battlefield instead half");
                } else if (card instanceof Permanent) {
                    throw new IllegalStateException("Unexpected trying of move permanent to battlefield instead card");
                } else {
                    permanent = new PermanentCard(card, event.getPlayerId(), game);
                }

                // put onto battlefield with possible counters
                game.getPermanentsEntering().put(permanent.getId(), permanent);
                card.applyEnterWithCounters(permanent, source, game);

                permanent.setTapped(info instanceof ZoneChangeInfo.Battlefield
                        && ((ZoneChangeInfo.Battlefield) info).tapped);

                // if need prototyped version
                if (Zone.STACK == event.getFromZone()) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null) {
                        permanent.setPrototyped(spell.isPrototyped());
                    }
                }

                permanent.setFaceDown(info.faceDown, game);
                if (info.faceDown) {
                    // TODO: need research cards with "setFaceDown(false"
                    // TODO: delete after new release and new face down bugs (old code remove face down status from a card for unknown reason), 2024-02-20
                    //card.setFaceDown(false, game);
                }

                // make sure the controller of all continuous effects of this card are switched to the current controller
                game.setScopeRelevant(true);
                try {
                    game.getContinuousEffects().setController(permanent.getId(), permanent.getControllerId());
                    if (permanent.entersBattlefield(source, game, fromZone, true)
                            && card.removeFromZone(game, fromZone, source)) {
                        success = true;
                        event.setTarget(permanent);

                        // tests only: inject fuzzy data with random phased out permanents
                        FuzzyTestsUtil.addRandomPhasedOutPermanent(permanent, source, game);
                    } else {
                        // revert controller to owner if permanent does not enter
                        game.getContinuousEffects().setController(permanent.getId(), permanent.getOwnerId());
                        game.getPermanentsEntering().remove(permanent.getId());
                    }
                } finally {
                    game.setScopeRelevant(false);
                }
            } else if (event.getTarget() != null) {
                // PUT PERMANENT TO OTHER ZONE (e.g. remove only)
                Permanent target = event.getTarget();
                success = target.removeFromZone(game, fromZone, source)
                        && game.getPlayer(target.getControllerId()).removeFromBattlefield(target, source, game);
            } else {
                // PUT CARD TO OTHER ZONE
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

    public static List<Card> chooseOrder(String message, Cards cards, Player player, Ability source, Game game) {
        List<Card> order = new ArrayList<>();
        if (cards.isEmpty()) {
            return order;
        }
        TargetCard target = new TargetCard(Zone.ALL, new FilterCard(message));
        target.setRequired(true);
        while (player.canRespond() && cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, source, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                order.add(card);
                cards.remove(target.getFirstTarget());
                target.clearChosen();
            } else {
                break;
            }
        }
        order.addAll(cards.getCards(game));
        return order;
    }

    private static Card prepareBlueprintCardFromSpell(Card card, ZoneChangeEvent event, Game game) {
        card = card.copy();
        if (Zone.STACK == event.getFromZone()) {
            // TODO: wtf, why only colors!? Must research and remove colors workaround or add all other data like types too
            Spell spell = game.getStack().getSpell(event.getTargetId());

            // old version
            if (false && spell != null && !spell.isFaceDown(game)) {
                if (!card.getColor(game).equals(spell.getColor(game))) {
                    // the card that is referenced to in the permanent is copied and the spell attributes are set to this copied card
                    card.getColor(game).setColor(spell.getColor(game));
                }
            }

            // new version
            if (true && spell != null && spell.getSpellAbility() != null) {
                Card characteristics = spell.getSpellAbility().getCharacteristics(game);
                if (!characteristics.isFaceDown(game)) {
                    if (!card.getColor(game).equals(characteristics.getColor(game))) {
                        // TODO: don't work with prototyped spells (setColor can't set colorless color)
                        card.getColor(game).setColor(characteristics.getColor(game));
                    }
                }
            }
        }
        return card;
    }

}
