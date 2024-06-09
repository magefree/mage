package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheTemporalAnchor extends CardImpl {

    public TheTemporalAnchor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your upkeep, scry 2.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ScryEffect(2), TargetController.YOU, false
        ));

        // Whenever you choose to put one or more cards on the bottom of your library while scrying, exile that many cards from the bottom of your library.
        this.addAbility(new TheTemporalAnchorTriggeredAbility());

        // During your turn, you may play cards exiled with The Temporal Anchor.
        this.addAbility(new SimpleStaticAbility(new TheTemporalAnchorPlayEffect()));
    }

    private TheTemporalAnchor(final TheTemporalAnchor card) {
        super(card);
    }

    @Override
    public TheTemporalAnchor copy() {
        return new TheTemporalAnchor(this);
    }
}

class TheTemporalAnchorTriggeredAbility extends TriggeredAbilityImpl {

    TheTemporalAnchorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheTemporalAnchorExileEffect());
    }

    private TheTemporalAnchorTriggeredAbility(final TheTemporalAnchorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheTemporalAnchorTriggeredAbility copy() {
        return new TheTemporalAnchorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRY_TO_BOTTOM;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId()) && event.getAmount() > 0) {
            this.getEffects().setValue("amount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you choose to put one or more cards on the bottom of your library while scrying, " +
                "exile that many cards from the bottom of your library.";
    }
}

class TheTemporalAnchorExileEffect extends OneShotEffect {

    TheTemporalAnchorExileEffect() {
        super(Outcome.Benefit);
    }

    private TheTemporalAnchorExileEffect(final TheTemporalAnchorExileEffect effect) {
        super(effect);
    }

    @Override
    public TheTemporalAnchorExileEffect copy() {
        return new TheTemporalAnchorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int amount = (Integer) getValue("amount");
        if (player == null || amount < 1) {
            return false;
        }
        int toSkip = Math.max(player.getLibrary().size() - amount, 0);
        Set<Card> cards = player
                .getLibrary()
                .getCards(game)
                .stream()
                .skip(toSkip)
                .collect(Collectors.toSet());
        return !cards.isEmpty() && player.moveCardsToExile(
                cards, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}

class TheTemporalAnchorPlayEffect extends AsThoughEffectImpl {

    TheTemporalAnchorPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "during your turn, you may play cards exiled with {this}";
    }

    private TheTemporalAnchorPlayEffect(final TheTemporalAnchorPlayEffect effect) {
        super(effect);
    }

    @Override
    public TheTemporalAnchorPlayEffect copy() {
        return new TheTemporalAnchorPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null) {
            return false;
        }
        UUID mainId = card.getMainCard().getId(); // for split cards
        MageObject sourceObject = source.getSourceObject(game);

        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game)
        ));
        return exileZone != null
                && exileZone.contains(mainId)
                && game.getCard(mainId) != null;
    }
}
