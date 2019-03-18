package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.effects.AsThoughManaEffect;
import mage.players.ManaPoolItem;

/**
 * @author TheElk801
 */
public final class MnemonicBetrayal extends CardImpl {

    public MnemonicBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Exile all cards from all opponents' graveyards. You may cast those cards this turn, and you may spend mana as though it were mana of any type to cast those spells. At the beginning of the next end step, if any of those cards remain exiled, return them to their owner's graveyards.
        this.getSpellAbility().addEffect(new MnemonicBetrayalExileEffect());

        // Exile Mnemonic Betrayal.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public MnemonicBetrayal(final MnemonicBetrayal card) {
        super(card);
    }

    @Override
    public MnemonicBetrayal copy() {
        return new MnemonicBetrayal(this);
    }
}

class MnemonicBetrayalExileEffect extends OneShotEffect {

    public MnemonicBetrayalExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all cards from all opponents' graveyards. "
                + "You may cast those cards this turn, "
                + "and you may spend mana as though it were mana of any type "
                + "to cast those spells. At the beginning of the next end step, "
                + "if any of those cards remain exiled, "
                + "return them to their owner's graveyards";
    }

    public MnemonicBetrayalExileEffect(final MnemonicBetrayalExileEffect effect) {
        super(effect);
    }

    @Override
    public MnemonicBetrayalExileEffect copy() {
        return new MnemonicBetrayalExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Map<UUID, Integer> cardMap = new HashMap<>();
        game.getOpponents(source.getControllerId()).stream().map((playerId) -> game.getPlayer(playerId)).filter((player) -> (player != null)).forEachOrdered((player) -> {
            cards.addAll(player.getGraveyard());
        });
        cards.getCards(game).stream().map((card) -> {
            cardMap.put(card.getId(), card.getZoneChangeCounter(game));
            return card;
        }).map((card) -> {
            game.addEffect(new MnemonicBetrayalCastFromExileEffect(card, game), source);
            return card;
        }).forEachOrdered((card) -> {
            game.addEffect(new MnemonicBetrayalAnyColorEffect(card, game), source);
        });
        controller.moveCardsToExile(cards.getCards(game), source, game, true, source.getSourceId(), source.getSourceObjectIfItStillExists(game).getName());
        game.addDelayedTriggeredAbility(new MnemonicBetrayalDelayedTriggeredAbility(cards, cardMap), source);
        return true;
    }
}

class MnemonicBetrayalCastFromExileEffect extends AsThoughEffectImpl {

    private final Card card;
    private final int zoneCounter;

    public MnemonicBetrayalCastFromExileEffect(Card card, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.card = card;
        this.zoneCounter = card.getZoneChangeCounter(game) + 1;
    }

    public MnemonicBetrayalCastFromExileEffect(final MnemonicBetrayalCastFromExileEffect effect) {
        super(effect);
        this.card = effect.card;
        this.zoneCounter = effect.zoneCounter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MnemonicBetrayalCastFromExileEffect copy() {
        return new MnemonicBetrayalCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (card.getZoneChangeCounter(game) != zoneCounter) {
            this.discard();
            return false;
        }
        return objectId.equals(card.getId())
                && card.getZoneChangeCounter(game) == zoneCounter
                && affectedControllerId.equals(source.getControllerId());
    }
}

class MnemonicBetrayalAnyColorEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final Card card;
    private final int zoneCounter;

    public MnemonicBetrayalAnyColorEffect(Card card, Game game) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        this.card = card;
        this.zoneCounter = card.getZoneChangeCounter(game) + 1;
    }

    public MnemonicBetrayalAnyColorEffect(final MnemonicBetrayalAnyColorEffect effect) {
        super(effect);
        this.card = effect.card;
        this.zoneCounter = effect.zoneCounter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MnemonicBetrayalAnyColorEffect copy() {
        return new MnemonicBetrayalAnyColorEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = game.getCard(objectId).getMainCard().getId(); // for split cards
        if (objectId.equals(card.getId())
                && card.getZoneChangeCounter(game) <= zoneCounter + 1
                && affectedControllerId.equals(source.getControllerId())) {
            return true;
        } else {
            if (objectId.equals(card.getId())) {
                this.discard();
            }
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class MnemonicBetrayalDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final Cards cards;
    private final Map<UUID, Integer> cardMap = new HashMap<>();

    public MnemonicBetrayalDelayedTriggeredAbility(Cards cards, Map<UUID, Integer> cardMap) {
        super(new MnemonicBetrayalReturnEffect(cards, cardMap));
        this.triggerOnlyOnce = true;
        this.cards = cards;
        this.cardMap.putAll(cardMap);
    }

    public MnemonicBetrayalDelayedTriggeredAbility(final MnemonicBetrayalDelayedTriggeredAbility ability) {
        super(ability);
        this.cards = ability.cards.copy();
        this.cardMap.putAll(ability.cardMap);
    }

    @Override
    public MnemonicBetrayalDelayedTriggeredAbility copy() {
        return new MnemonicBetrayalDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return cards.stream().anyMatch((cardId) -> (game.getState().getZone(cardId) == Zone.EXILED
                && game.getState().getZoneChangeCounter(cardId) == cardMap.getOrDefault(cardId, -5) + 1));
    }

    @Override
    public String getRule() {
        return "At the beginning of the next end step, "
                + "if any of those cards remain exiled, "
                + "return them to their owner's graveyards.";
    }
}

class MnemonicBetrayalReturnEffect extends OneShotEffect {

    private final Cards cards;
    private final Map<UUID, Integer> cardMap = new HashMap<>();

    public MnemonicBetrayalReturnEffect(Cards cards, Map<UUID, Integer> cardMap) {
        super(Outcome.Benefit);
        this.cards = cards;
        this.cardMap.putAll(cardMap);
    }

    public MnemonicBetrayalReturnEffect(final MnemonicBetrayalReturnEffect effect) {
        super(effect);
        this.cards = effect.cards.copy();
        this.cardMap.putAll(effect.cardMap);
    }

    @Override
    public MnemonicBetrayalReturnEffect copy() {
        return new MnemonicBetrayalReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cardsToReturn = new CardsImpl();
        cards.getCards(game).stream().filter((card) -> (game.getState().getZone(card.getId()) == Zone.EXILED
                && card.getZoneChangeCounter(game) == cardMap.getOrDefault(card.getId(), -5) + 1)).forEachOrdered((card) -> {
            cardsToReturn.add(card);
        });
        return player.moveCards(cardsToReturn, Zone.GRAVEYARD, source, game);
    }
}
