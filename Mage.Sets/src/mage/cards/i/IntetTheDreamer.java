
package mage.cards.i;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class IntetTheDreamer extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public IntetTheDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Intet, the Dreamer deals combat damage to a player, you may pay {2}{U}. If you do, exile the top card of your library face down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new IntetTheDreamerExileEffect(), new ManaCostsImpl("{2}{U}")), false, true));
        // You may look at that card for as long as it remains exiled.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new IntetTheDreamerLookEffect()));
        // You may play that card without paying its mana cost for as long as Intet remains on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IntetTheDreamerCastEffect()));
    }

    public IntetTheDreamer(final IntetTheDreamer card) {
        super(card);
    }

    @Override
    public IntetTheDreamer copy() {
        return new IntetTheDreamer(this);
    }
}

class IntetTheDreamerExileEffect extends OneShotEffect {

    public IntetTheDreamerExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top card of your library face down";
    }

    public IntetTheDreamerExileEffect(final IntetTheDreamerExileEffect effect) {
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
                Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(IntetTheDreamer.VALUE_PREFIX + source.getSourceId().toString());
                if (exileZones == null) {
                    exileZones = new HashSet<>();
                    game.getState().setValue(IntetTheDreamer.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                }
                exileZones.add(exileZoneId);
                return true;
            }
        }
        return false;
    }

    @Override
    public IntetTheDreamerExileEffect copy() {
        return new IntetTheDreamerExileEffect(this);
    }
}

class IntetTheDreamerCastEffect extends AsThoughEffectImpl {

    public IntetTheDreamerCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play the card from exile without paying its mana cost for as long as {this} remains on the battlefield";
    }

    public IntetTheDreamerCastEffect(final IntetTheDreamerCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntetTheDreamerCastEffect copy() {
        return new IntetTheDreamerCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null && card.isFaceDown(game)) {
                    ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
                    if (zone != null && zone.contains(card.getId())/* && CardUtil.cardCanBePlayedNow(card, controller.getId(), game)*/) {
                        if (card.isLand()) {
                            if (game.canPlaySorcery(controller.getId()) && game.getPlayer(controller.getId()).canPlayLand()) {
                                return controller.chooseUse(outcome, "Play " + card.getIdName() + '?', source, game);
                            }
                        } else {
                            controller.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }
}

class IntetTheDreamerLookEffect extends AsThoughEffectImpl {

    public IntetTheDreamerLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at that card for as long as it remains exiled";
    }

    public IntetTheDreamerLookEffect(final IntetTheDreamerLookEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntetTheDreamerLookEffect copy() {
        return new IntetTheDreamerLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null && card.isFaceDown(game)) {
                    Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(IntetTheDreamer.VALUE_PREFIX + source.getSourceId().toString());
                    if (exileZones != null) {
                        for (ExileZone exileZone : game.getExile().getExileZones()) {
                            if (exileZone.contains(objectId)) {
                                if (!exileZones.contains(exileZone.getId())) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
