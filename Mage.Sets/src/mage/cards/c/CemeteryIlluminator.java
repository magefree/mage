package mage.cards.c;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;
import mage.watchers.common.OnceEachTurnCastWatcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class CemeteryIlluminator extends CardImpl {

    public CemeteryIlluminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Cemetery Illuminator enters the battlefield or attacks, exile a card from a graveyard.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CemeteryIlluminatorExileEffect()));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // Once each turn, you may cast a spell from the top of your library if it shares a card type with a card exiled with Cemetery Illuminator.
        this.addAbility(new SimpleStaticAbility(new CemeteryIlluminatorPlayTopEffect())
                .setIdentifier(MageIdentifier.OnceEachTurnCastWatcher)
                        .addHint(OnceEachTurnCastWatcher.getHint()),
                new OnceEachTurnCastWatcher());
    }

    private CemeteryIlluminator(final CemeteryIlluminator card) {
        super(card);
    }

    @Override
    public CemeteryIlluminator copy() {
        return new CemeteryIlluminator(this);
    }
}

class CemeteryIlluminatorExileEffect extends OneShotEffect {

    CemeteryIlluminatorExileEffect() {
        super(Outcome.Exile);
        staticText = "exile a card from a graveyard";
    }

    private CemeteryIlluminatorExileEffect(final CemeteryIlluminatorExileEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryIlluminatorExileEffect copy() {
        return new CemeteryIlluminatorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInGraveyard target = new TargetCardInGraveyard();
            target.withNotTarget(true);
            controller.choose(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                MageObject sourceObject = source.getSourceObject(game);
                String exileName = sourceObject == null ? null : sourceObject.getIdName();
                return controller.moveCardsToExile(card, source, game, true, exileId, exileName);
            }
        }
        return false;
    }
}

class CemeteryIlluminatorPlayTopEffect extends AsThoughEffectImpl {

    CemeteryIlluminatorPlayTopEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may cast a spell from the top of your library if it shares a card type with a card exiled with {this}";
    }

    private CemeteryIlluminatorPlayTopEffect(final CemeteryIlluminatorPlayTopEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryIlluminatorPlayTopEffect copy() {
        return new CemeteryIlluminatorPlayTopEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Player controller = game.getPlayer(source.getControllerId());
        OnceEachTurnCastWatcher watcher = game.getState().getWatcher(OnceEachTurnCastWatcher.class);
        Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || watcher == null || sourceObject == null) {
            return false;
        }
        // Reference logic from PlayFromTopOfLibraryEffect and CastFromGraveyardOnceEachTurnAbility
        if (!playerId.equals(controller.getId()) || watcher.isAbilityUsed(playerId, new MageObjectReference(sourceObject, game))) {
            return false;
        }
        Card card = game.getCard(objectId);
        Card topCard = controller.getLibrary().getFromTop(game);
        if (card == null || topCard == null || !topCard.getId().equals(card.getMainCard().getId())) {
            return false;
        }
        if (!(affectedAbility instanceof SpellAbility)) {
            return false;
        }
        // need to check characteristics of spell rather than card (e.g. adventure, morph, etc.)
        Card cardToCast = ((SpellAbility) affectedAbility).getCharacteristics(game);
        if (cardToCast.getManaCost().isEmpty()) {
            return false;
        }
        Set<CardType> cardTypes = new HashSet<>(cardToCast.getCardType(game));
        // Check if it shares a card type with exiled cards
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()));
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return false;
        }
        return exileZone
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .flatMap(c -> c.getCardType(game).stream())
                .anyMatch(cardTypes::contains);
    }
}
