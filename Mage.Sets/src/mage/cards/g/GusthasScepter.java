package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
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
        this.addAbility(new GusthasScepterTriggeredAbility());
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

    private GusthasScepterExileEffect(final GusthasScepterExileEffect effect) {
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
        target.withNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
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

    private GusthasScepterLookAtCardEffect(final GusthasScepterLookAtCardEffect effect) {
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

class GusthasScepterTriggeredAbility extends TriggeredAbilityImpl {

    public GusthasScepterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GusthasScepterPutExiledCardsInOwnersGraveyardEffect());
        setTriggerPhrase("When you lose control of {this}, ");
    }

    private GusthasScepterTriggeredAbility(final GusthasScepterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GusthasScepterTriggeredAbility copy() {
        return new GusthasScepterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(sourceId);
    }
}

class GusthasScepterPutExiledCardsInOwnersGraveyardEffect extends OneShotEffect {

    public GusthasScepterPutExiledCardsInOwnersGraveyardEffect() {
        super(Outcome.Neutral);
        this.staticText = "put all cards exiled with {this} into their owner's graveyard";
    }

    private GusthasScepterPutExiledCardsInOwnersGraveyardEffect(final GusthasScepterPutExiledCardsInOwnersGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        exileZone.getCards(game).stream().forEach(card -> card.moveToZone(Zone.GRAVEYARD, source, game, false));
        return true;
    }

    @Override
    public GusthasScepterPutExiledCardsInOwnersGraveyardEffect copy() {
        return new GusthasScepterPutExiledCardsInOwnersGraveyardEffect(this);
    }
}
