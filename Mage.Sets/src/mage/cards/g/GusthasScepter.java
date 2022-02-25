package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2, jeffwadsworth, L_J & TheElk801
 */
public final class GusthasScepter extends CardImpl {

    public GusthasScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}: Exile a card from your hand face down. You may look at it for as long as it remains exiled.
        this.addAbility(new SimpleActivatedAbility(new GusthasScepterExileEffect(), new TapSourceCost()));

        // {T}: Return a card you own exiled with Gustha’s Scepter to your hand.
        this.addAbility(new SimpleActivatedAbility(new GusthasScepterReturnEffect(), new TapSourceCost()));

        // When you lose control of Gustha’s Scepter, put all cards exiled with Gustha’s Scepter into their owner’s graveyard.
        this.addAbility(new GusthasScepterLoseControlAbility());
    }

    private GusthasScepter(final GusthasScepter card) {
        super(card);
    }

    @Override
    public GusthasScepter copy() {
        return new GusthasScepter(this);
    }
}

class GusthasScepterExileEffect extends OneShotEffect {

    public GusthasScepterExileEffect() {
        super(Outcome.DrawCard);
        staticText = "exile a card from your hand face down. You may look at it for as long as it remains exiled";
    }

    public GusthasScepterExileEffect(final GusthasScepterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || controller.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand();
        controller.chooseTarget(outcome, controller.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCardsToExile(
                card, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        card.setFaceDown(true, game);
        game.addEffect(new GusthasScepterLookAtCardEffect(card, game), source);
        return true;
    }

    @Override
    public GusthasScepterExileEffect copy() {
        return new GusthasScepterExileEffect(this);
    }
}

class GusthasScepterReturnEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card you own exiled with this permanent");

    GusthasScepterReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return a card you own exiled with {this} to your hand";
    }

    private GusthasScepterReturnEffect(final GusthasScepterReturnEffect effect) {
        super(effect);
    }

    @Override
    public GusthasScepterReturnEffect copy() {
        return new GusthasScepterReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(filter, CardUtil.getExileZoneId(game, source));
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            return false;
        }
        player.choose(outcome, target, source.getSourceId(), game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}

class GusthasScepterLookAtCardEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    public GusthasScepterLookAtCardEffect(Card card, Game game) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
        staticText = "You may look at it for as long as it remains exiled";
    }

    public GusthasScepterLookAtCardEffect(final GusthasScepterLookAtCardEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GusthasScepterLookAtCardEffect copy() {
        return new GusthasScepterLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!mor.zoneCounterIsCurrent(game)) {
            discard();
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || !exileZone.contains(mor.getSourceId())) {
            discard();
            return false;
        }
        return mor.refersTo(objectId, game) && source.isControlledBy(affectedControllerId);
    }
}

class GusthasScepterLoseControlAbility extends DelayedTriggeredAbility {

    public GusthasScepterLoseControlAbility() {
        super(new GusthasScepterPutExiledCardsInOwnersGraveyard(), Duration.EndOfGame, false);
    }

    public GusthasScepterLoseControlAbility(final GusthasScepterLoseControlAbility ability) {
        super(ability);
    }

    @Override
    public GusthasScepterLoseControlAbility copy() {
        return new GusthasScepterLoseControlAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case LOST_CONTROL:
                return event.getPlayerId().equals(controllerId)
                        && event.getTargetId().equals(this.getSourceId());
            case ZONE_CHANGE:
                return event.getTargetId().equals(this.getSourceId()) && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you lose control of {this}, put all cards exiled with {this} into their owner's graveyard.";
    }
}

class GusthasScepterPutExiledCardsInOwnersGraveyard extends OneShotEffect {

    public GusthasScepterPutExiledCardsInOwnersGraveyard() {
        super(Outcome.Neutral);
    }

    public GusthasScepterPutExiledCardsInOwnersGraveyard(final GusthasScepterPutExiledCardsInOwnersGraveyard effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null && controller.moveCards(exileZone.getCards(game), Zone.GRAVEYARD, source, game);
    }

    @Override
    public GusthasScepterPutExiledCardsInOwnersGraveyard copy() {
        return new GusthasScepterPutExiledCardsInOwnersGraveyard(this);
    }
}
