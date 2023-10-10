package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class GrolnokTheOmnivore extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.FROG);

    public GrolnokTheOmnivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Frog you control attacks, mill 3 cards.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new MillCardsControllerEffect(3), false, filter));

        // Whenever a permanent card is put into your graveyard from your library, exile it with a croak counter on it.
        this.addAbility(new GrolnokTheOmnivoreTriggeredAbility());

        // You may play lands and cast spells from among cards you own in exile with croak counters on them.
        this.addAbility(new SimpleStaticAbility(new GrolnokTheOmnivorePlayEffect()));
    }

    private GrolnokTheOmnivore(final GrolnokTheOmnivore card) {
        super(card);
    }

    @Override
    public GrolnokTheOmnivore copy() {
        return new GrolnokTheOmnivore(this);
    }
}

class GrolnokTheOmnivoreTriggeredAbility extends TriggeredAbilityImpl {

    public GrolnokTheOmnivoreTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GrolnokTheOmnivoreExileEffect());
        setTriggerPhrase("Whenever a permanent card is put into your graveyard from your library, ");
    }

    private GrolnokTheOmnivoreTriggeredAbility(final GrolnokTheOmnivoreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrolnokTheOmnivoreTriggeredAbility copy() {
        return new GrolnokTheOmnivoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Card card = game.getCard(zEvent.getTargetId());
        if (card != null && zEvent.getToZone() == Zone.GRAVEYARD && zEvent.getFromZone() == Zone.LIBRARY
                && card.isOwnedBy(controllerId) && card.isPermanent(game)) {
            getEffects().setTargetPointer(new FixedTarget(card, game));
            return true;
        }
        return false;
    }
}

class GrolnokTheOmnivoreExileEffect extends OneShotEffect {

    public GrolnokTheOmnivoreExileEffect() {
        super(Outcome.Exile);
        staticText = "exile it with a croak counter on it";
    }

    private GrolnokTheOmnivoreExileEffect(final GrolnokTheOmnivoreExileEffect effect) {
        super(effect);
    }

    @Override
    public GrolnokTheOmnivoreExileEffect copy() {
        return new GrolnokTheOmnivoreExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (controller != null && card != null) {
            return CardUtil.moveCardWithCounter(game, source, controller, card, Zone.EXILED, CounterType.CROAK.createInstance());
        }
        return false;
    }
}

class GrolnokTheOmnivorePlayEffect extends AsThoughEffectImpl {

    public GrolnokTheOmnivorePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play lands and cast spells from among cards you own in exile with croak counters on them";
    }

    private GrolnokTheOmnivorePlayEffect(final GrolnokTheOmnivorePlayEffect effect) {
        super(effect);
    }

    @Override
    public GrolnokTheOmnivorePlayEffect copy() {
        return new GrolnokTheOmnivorePlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                Card mainCard = card.getMainCard();
                return game.getState().getZone(mainCard.getId()).equals(Zone.EXILED) && mainCard.isOwnedBy(source.getControllerId())
                        && mainCard.getCounters(game).containsKey(CounterType.CROAK);
            }
        }
        return false;
    }
}
