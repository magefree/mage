package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CosimaGodOfTheVoyage extends ModalDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VEHICLE, "a Vehicle you control");

    public CosimaGodOfTheVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{U}",
                "The Omenkeel",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "{1}{U}"
        );

        // 1.
        // Cosima, God of the Voyage
        // Legendary Creature - God
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(4));

        // At the beginning of your upkeep, you may exile Cosima. If you do, it gains "Whenever a land enters the battlefield under your control, if Cosima is exiled, you may put a voyage counter on it. If you don't, return Cosima to the battlefield with X +1/+1 counters on it and draw X cards, where X is the number of voyage counters on it.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CosimaGodOfTheVoyageEffect(), TargetController.YOU, true
        ));

        // 2.
        // The Omenkeel
        // Legendary Artifact - Vehicle
        this.getRightHalfCard().setPT(new MageInt(3), new MageInt(3));

        // Whenever a Vehicle you control deals combat damage to a player, that player exiles that many cards from the top of their library. You may play lands from among those cards for as long as they remain exiled.
        this.getRightHalfCard().addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new TheOmenkeelEffect(), filter, false,
                SetTargetPointer.PLAYER, true, true
        ));

        // Crew 1
        this.getRightHalfCard().addAbility(new CrewAbility(1));
    }

    private CosimaGodOfTheVoyage(final CosimaGodOfTheVoyage card) {
        super(card);
    }

    @Override
    public CosimaGodOfTheVoyage copy() {
        return new CosimaGodOfTheVoyage(this);
    }
}

class CosimaGodOfTheVoyageEffect extends OneShotEffect {

    CosimaGodOfTheVoyageEffect() {
        super(Outcome.Benefit);
        staticText = "exile {this}. If you do, it gains " +
                "\"Whenever a land enters the battlefield under your control, if {this} is exiled, " +
                "you may put a voyage counter on it. If you don't, return {this} to the battlefield " +
                "with X +1/+1 counters on it and draw X cards, where X is the number of voyage counters on it.\"";
    }

    private CosimaGodOfTheVoyageEffect(final CosimaGodOfTheVoyageEffect effect) {
        super(effect);
    }

    @Override
    public CosimaGodOfTheVoyageEffect copy() {
        return new CosimaGodOfTheVoyageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Card card = game.getCard(permanent.getId());
        if (card == null) {
            return false;
        }

        String exileName = "Exiled to the voyage";
        player.moveCardsToExile(permanent, source, game, true, source.getSourceId(), exileName);
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        game.addEffect(new CosimaGodOfTheVoyageGainAbilityEffect(new MageObjectReference(card, game)), source);
        return true;
    }
}

class CosimaGodOfTheVoyageGainAbilityEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    CosimaGodOfTheVoyageGainAbilityEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
    }

    private CosimaGodOfTheVoyageGainAbilityEffect(final CosimaGodOfTheVoyageGainAbilityEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public CosimaGodOfTheVoyageGainAbilityEffect copy() {
        return new CosimaGodOfTheVoyageGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = mor.getCard(game);
        if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            Ability ability = new CosimaGodOfTheVoyageTriggeredAbility();
            ability.setSourceId(card.getId());
            ability.setControllerId(source.getControllerId());
            game.getState().addOtherAbility(card, ability);
        } else {
            discard();
        }
        return true;
    }
}

class CosimaGodOfTheVoyageTriggeredAbility extends TriggeredAbilityImpl {

    CosimaGodOfTheVoyageTriggeredAbility() {
        super(Zone.EXILED, new CosimaGodOfTheVoyageReturnEffect());
    }

    private CosimaGodOfTheVoyageTriggeredAbility(final CosimaGodOfTheVoyageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isLand(game) && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getState().getZone(getSourceId()) == Zone.EXILED;
    }

    @Override
    public CosimaGodOfTheVoyageTriggeredAbility copy() {
        return new CosimaGodOfTheVoyageTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield under your control, if {this} is exiled, " +
                "you may put a voyage counter on it. If you don't, return {this} to the battlefield " +
                "with X +1/+1 counters on it and draw X cards, where X is the number of voyage counters on it.";
    }
}

class CosimaGodOfTheVoyageReturnEffect extends OneShotEffect {

    CosimaGodOfTheVoyageReturnEffect() {
        super(Outcome.Benefit);
    }

    private CosimaGodOfTheVoyageReturnEffect(final CosimaGodOfTheVoyageReturnEffect effect) {
        super(effect);
    }

    @Override
    public CosimaGodOfTheVoyageReturnEffect copy() {
        return new CosimaGodOfTheVoyageReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }

        // AI hint to return card on 2+ counters
        int currentCount = card.getCounters(game).getCount(CounterType.VOYAGE);
        Outcome aiOutcome = (currentCount >= 2) ? Outcome.Benefit : Outcome.Detriment;

        if (player.chooseUse(aiOutcome, "Add a voyage counter (current: " + currentCount + ")?", null,
                "Yes, add counter", "No, return to battlefield", source, game)
                && card.addCounters(CounterType.VOYAGE.createInstance(), player.getId(), source, game)) {
            return true;
        }

        // return to battle
        int newCount = card.getCounters(game).getCount(CounterType.VOYAGE);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (newCount < 1) {
            return true;
        }

        // draw and boost
        player.drawCards(newCount, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(newCount), player.getId(), source, game);
        }
        return true;
    }
}

class TheOmenkeelEffect extends OneShotEffect {

    TheOmenkeelEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles that many cards from the top of their library. " +
                "You may play lands from among those cards for as long as they remain exiled.";
    }

    private TheOmenkeelEffect(final TheOmenkeelEffect effect) {
        super(effect);
    }

    @Override
    public TheOmenkeelEffect copy() {
        return new TheOmenkeelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, damage));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        cards.removeIf(uuid -> !game.getCard(uuid).isLand(game));
        game.addEffect(new TheOmenkeelPlayFromExileEffect(cards, game), source);
        return true;
    }
}

class TheOmenkeelPlayFromExileEffect extends AsThoughEffectImpl {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    TheOmenkeelPlayFromExileEffect(Cards cards, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.morSet.addAll(cards.stream().map(uuid -> new MageObjectReference(uuid, game)).collect(Collectors.toSet()));
    }

    private TheOmenkeelPlayFromExileEffect(final TheOmenkeelPlayFromExileEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheOmenkeelPlayFromExileEffect copy() {
        return new TheOmenkeelPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || game.getState().getZone(sourceId) != Zone.EXILED) {
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null
                && card.isLand(game)
                && morSet.stream().anyMatch(mor -> mor.refersTo(card, game));
    }
}
