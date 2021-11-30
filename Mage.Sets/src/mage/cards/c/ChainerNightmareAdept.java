package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.CastFromHandWatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author goesta
 */
public final class ChainerNightmareAdept extends CardImpl {

    public ChainerNightmareAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Discard a card: You may cast a creature card from your graveyard this turn. 
        // Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new ChainerNightmareAdeptContinuousEffect(), new DiscardCardCost()
        ), new ChainerNightmareAdeptWatcher());

        // Whenever a nontoken creature enters the battlefield under your control, 
        // if you didn't cast it from your hand, it gains haste until your next turn.
        this.addAbility(new ChainerNightmareAdeptTriggeredAbility());
    }

    private ChainerNightmareAdept(final ChainerNightmareAdept card) {
        super(card);
    }

    @Override
    public ChainerNightmareAdept copy() {
        return new ChainerNightmareAdept(this);
    }
}

class ChainerNightmareAdeptContinuousEffect extends AsThoughEffectImpl {

    ChainerNightmareAdeptContinuousEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may cast a creature spell from your graveyard this turn";
    }

    private ChainerNightmareAdeptContinuousEffect(final ChainerNightmareAdeptContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ChainerNightmareAdeptContinuousEffect copy() {
        return new ChainerNightmareAdeptContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        ChainerNightmareAdeptWatcher watcher = game.getState().getWatcher(ChainerNightmareAdeptWatcher.class);
        if (watcher != null) {
            watcher.addPlayable(source, game);
        }
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        ChainerNightmareAdeptWatcher watcher = game.getState().getWatcher(ChainerNightmareAdeptWatcher.class);
        if (watcher == null || !watcher.checkPermission(
                affectedControllerId, source, game
        ) || game.getState().getZone(sourceId) != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null
                && card.getOwnerId().equals(affectedControllerId)
                && card.isCreature(game)
                && !card.isLand(game);
    }
}

class ChainerNightmareAdeptWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();

    ChainerNightmareAdeptWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (event.getAdditionalReference() == null) {
                return;
            }
            morMap.computeIfAbsent(event.getAdditionalReference().getApprovingMageObjectReference(), m -> new HashMap<>())
                    .compute(event.getPlayerId(), (u, i) -> i == null ? 0 : Integer.sum(i, -1));
            return;
        }
    }

    @Override
    public void reset() {
        morMap.clear();
        super.reset();
    }

    boolean checkPermission(UUID playerId, Ability source, Game game) {
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        );
        return morMap.computeIfAbsent(mor, m -> new HashMap<>()).getOrDefault(playerId, 0) > 0;
    }

    void addPlayable(Ability source, Game game) {
        MageObjectReference mor = new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        );
        morMap.computeIfAbsent(mor, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }
}

class ChainerNightmareAdeptTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    private final static String abilityText = "Whenever a nontoken creature "
            + "enters the battlefield under your control, "
            + "if you didn't cast it from your hand, it gains haste until your next turn.";
    private final static ContinuousEffect gainHasteUntilNextTurnEffect
            = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.UntilYourNextTurn);
    private final static FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    ChainerNightmareAdeptTriggeredAbility() {
        super(Zone.BATTLEFIELD, gainHasteUntilNextTurnEffect, filter, false,
                SetTargetPointer.PERMANENT, abilityText);
        this.addWatcher(new CastFromHandWatcher());
    }

    private ChainerNightmareAdeptTriggeredAbility(final ChainerNightmareAdeptTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public ChainerNightmareAdeptTriggeredAbility copy() {
        return new ChainerNightmareAdeptTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        return watcher != null && !watcher.spellWasCastFromHand(event.getTargetId());
    }
}
