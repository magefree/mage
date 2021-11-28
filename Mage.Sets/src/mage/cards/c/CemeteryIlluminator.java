package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;
import mage.watchers.Watcher;

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
                .setIdentifier(MageIdentifier.CemeteryIlluminatorWatcher),
                new CemeteryIlluminatorWatcher());
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

    public CemeteryIlluminatorExileEffect() {
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
            target.setNotTarget(true);
            controller.choose(outcome, target, source.getSourceId(), game);
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

    public CemeteryIlluminatorPlayTopEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit, true);
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
        // Same checks as in PlayTheTopCardEffect
        // Once per turn clause checked by Watcher same as Lurrus of the Dream Den
        if (affectedControllerId.equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            CemeteryIlluminatorWatcher watcher = game.getState().getWatcher(CemeteryIlluminatorWatcher.class);
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (controller != null && watcher != null && sourceObject != null && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game))) {
                Card card = game.getCard(objectId);
                Card topCard = controller.getLibrary().getFromTop(game);
                if (card != null && topCard != null && topCard.getId().equals(card.getMainCard().getId()) && !card.isLand(game) && !card.getManaCost().isEmpty()) {
                    // Check if it shares a card type with exiled cards
                    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()));
                    ExileZone exileZone = game.getExile().getExileZone(exileId);
                    if (exileZone != null) {
                        HashSet<CardType> cardTypes = new HashSet<>(card.getCardType(game));
                        for (UUID exileCardId : exileZone) {
                            Card exileCard = game.getCard(exileCardId);
                            if (exileCard != null) {
                                for (CardType exileType : exileCard.getCardType(game)) {
                                    if (cardTypes.contains(exileType)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

class CemeteryIlluminatorWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public CemeteryIlluminatorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.CemeteryIlluminatorWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
