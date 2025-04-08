package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.CardState;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class TinybonesBaubleBurglar extends CardImpl {

    public TinybonesBaubleBurglar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever an opponent discards a card, exile it from their graveyard with a stash counter on it.
        this.addAbility(new TinybonesBaubleBurglarTriggeredAbility(new TinybonesBaubleBurglarExileEffect()));

        // During your turn, you may play cards you don't own with stash counters on them from exile, and mana of any type can be spent to cast those spells.
        Ability staticAbility = new SimpleStaticAbility(new TinybonesBaubleBurglarPlayEffect());
        staticAbility.addEffect(new TinybonesBaubleBurglarSpendAnyManaEffect());
        this.addAbility(staticAbility);

        // {3}{B}, {T}: Each opponent discards a card. Activate only as a sorcery.
        Ability activatedAbility = new ActivateAsSorceryActivatedAbility(
                new DiscardEachPlayerEffect(
                        StaticValue.get(1), false, TargetController.OPPONENT
                ),
                new ManaCostsImpl<>("{3}{B}")
        );
        activatedAbility.addCost(new TapSourceCost());
        this.addAbility(activatedAbility);
    }

    private TinybonesBaubleBurglar(final TinybonesBaubleBurglar card) {
        super(card);
    }

    @Override
    public TinybonesBaubleBurglar copy() {
        return new TinybonesBaubleBurglar(this);
    }
}

class TinybonesBaubleBurglarExileEffect extends OneShotEffect {

    TinybonesBaubleBurglarExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile it from their graveyard with a stash counter on it";
    }

    private TinybonesBaubleBurglarExileEffect(final TinybonesBaubleBurglarExileEffect effect) {
        super(effect);
    }

    @Override
    public TinybonesBaubleBurglarExileEffect copy() {
        return new TinybonesBaubleBurglarExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = (Card) getValue("discardedCard");
        if (player == null || card == null || !Zone.GRAVEYARD.match(game.getState().getZone(card.getId()))) {
            return false;
        }
        player.moveCardsToExile(
                card, source, game, true,
                null, ""
        );
        card.addCounters(CounterType.STASH.createInstance(), source, game);
        return true;
    }
}

class TinybonesBaubleBurglarTriggeredAbility extends TriggeredAbilityImpl {

    TinybonesBaubleBurglarTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever an opponent discards a card, ");
    }

    private TinybonesBaubleBurglarTriggeredAbility(final TinybonesBaubleBurglarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TinybonesBaubleBurglarTriggeredAbility copy() {
        return new TinybonesBaubleBurglarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            this.getEffects().setValue("discardedCard", game.getCard(event.getTargetId()));
            return true;
        }
        return false;
    }
}

class TinybonesBaubleBurglarPlayEffect extends AsThoughEffectImpl {

    TinybonesBaubleBurglarPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During your turn, you may play cards you don't own with stash counters on them from exile";
    }

    private TinybonesBaubleBurglarPlayEffect(final TinybonesBaubleBurglarPlayEffect effect) {
        super(effect);
    }

    @Override
    public TinybonesBaubleBurglarPlayEffect copy() {
        return new TinybonesBaubleBurglarPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.isActivePlayer(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                Card mainCard = card.getMainCard();
                return game.getState().getZone(mainCard.getId()).equals(Zone.EXILED)
                        && !mainCard.isOwnedBy(source.getControllerId())
                        && mainCard.getCounters(game).containsKey(CounterType.STASH);
            }
        }
        return false;
    }
}

class TinybonesBaubleBurglarSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    TinybonesBaubleBurglarSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = ", and mana of any type can be spent to cast those spells";
    }

    private TinybonesBaubleBurglarSpendAnyManaEffect(final TinybonesBaubleBurglarSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TinybonesBaubleBurglarSpendAnyManaEffect copy() {
        return new TinybonesBaubleBurglarSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || !game.getOpponents(game.getOwnerId(sourceId)).contains(source.getControllerId())) {
            return false;
        }

        Card card = game.getCard(sourceId);
        if (card == null) {
            return false;
        }
        card = card.getMainCard();

        // card can be in exile or stack zones
        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            // exile zone
            return card.getCounters(game).getCount(CounterType.STASH) > 0;
        } else {
            // stack zone
            // you must look at exile zone (use LKI to see ice counters from the past)
            CardState cardState;
            if (card instanceof SplitCard) {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            } else if (card instanceof CardWithSpellOption) {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            } else if (card instanceof ModalDoubleFacedCard) {
                cardState = game.getLastKnownInformationCard(((ModalDoubleFacedCard) card).getLeftHalfCard().getId(), Zone.EXILED);
            } else {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            }
            return cardState != null && cardState.getCounters().getCount(CounterType.STASH) > 0;
        }
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getSourceObject() != null) {
            return mana.getFirstAvailable();
        }
        return null;
    }
}
