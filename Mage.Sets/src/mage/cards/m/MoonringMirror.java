
package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MoonringMirror extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public MoonringMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever you draw a card, exile the top card of your library face down.
        this.addAbility(new DrawCardControllerTriggeredAbility(new MoonringMirrorExileEffect(), false));

        // At the beginning of your upkeep, you may exile all cards from your hand face down. If you do, put all other cards you own exiled with Moonring Mirror into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MoonringMirrorEffect(), TargetController.YOU, true));
    }

    private MoonringMirror(final MoonringMirror card) {
        super(card);
    }

    @Override
    public MoonringMirror copy() {
        return new MoonringMirror(this);
    }
}

class MoonringMirrorExileEffect extends OneShotEffect {

    public MoonringMirrorExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top card of your library face down";
    }

    public MoonringMirrorExileEffect(final MoonringMirrorExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            MageObject sourceObject = source.getSourceObject(game);
            if (card != null && sourceObject != null) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                card.setFaceDown(true, game);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                card.setFaceDown(true, game);
                Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(MoonringMirror.VALUE_PREFIX + source.getSourceId().toString());
                if (exileZones == null) {
                    exileZones = new HashSet<>();
                    game.getState().setValue(MoonringMirror.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                }
                exileZones.add(exileZoneId);
                return true;
            }
        }
        return false;
    }

    @Override
    public MoonringMirrorExileEffect copy() {
        return new MoonringMirrorExileEffect(this);
    }
}

class MoonringMirrorEffect extends OneShotEffect {

    public MoonringMirrorEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile all cards from your hand face down. If you do, put all other cards you own exiled with {this} into your hand";
    }

    public MoonringMirrorEffect(final MoonringMirrorEffect effect) {
        super(effect);
    }

    @Override
    public MoonringMirrorEffect copy() {
        return new MoonringMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);

        Cards cardsToHand = null;
        if (exileZone != null && !exileZone.isEmpty()) {
            cardsToHand = new CardsImpl(exileZone);
        }

        // hand
        for (Card card : controller.getHand().getCards(game)) {
            card.setFaceDown(true, game);
        }
        controller.moveCardsToExile(controller.getHand().getCards(game), source, game, false, exileZoneId, sourceObject.getIdName());

        if (cardsToHand != null) {
            controller.moveCards(cardsToHand.getCards(game), Zone.HAND, source, game, false, true, false, null);
        }

        exileZone = game.getExile().getExileZone(exileZoneId);
        if (exileZone != null && !exileZone.isEmpty()) {
            for (Card card : game.getExile().getExileZone(exileZoneId).getCards(game)) {
                card.setFaceDown(true, game);
            }
        }

        return true;
    }
}
