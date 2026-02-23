package mage.cards.h;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HidetsuguConsumesAll extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public HidetsuguConsumesAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{B}{R}",
                "Vessel of the All-Consuming",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.OGRE, SubType.SHAMAN}, "BR"
        );

        // Hidetsugu Consumes All
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Destroy each nonland permanent with mana value 1 or less.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_I, new DestroyAllEffect(filter)
                .setText("destroy each nonland permanent with mana value 1 or less"));

        // II — Exile all graveyards.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new ExileGraveyardAllPlayersEffect());

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        sagaAbility.addWatcher(VesselOfTheAllConsumingWatcher.makeWatcher());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Vessel of the All-Consuming
        this.getRightHalfCard().setPT(3, 3);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever Vessel of the All-Consuming deals damage, put a +1/+1 counter on it.
        this.getRightHalfCard().addAbility(new DealsDamageSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Whenever Vessel of the All-Consuming deals damage to a player, if it has dealt 10 or more damage to that player this turn, they lose the game.
        this.getRightHalfCard().addAbility(new DealsDamageToAPlayerTriggeredAbility(
                new LoseGameTargetPlayerEffect().setText("they lose the game"), false, true
        ).withInterveningIf(VesselOfTheAllConsumingCondition.instance));
    }

    private HidetsuguConsumesAll(final HidetsuguConsumesAll card) {
        super(card);
    }

    @Override
    public HidetsuguConsumesAll copy() {
        return new HidetsuguConsumesAll(this);
    }
}

enum VesselOfTheAllConsumingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return VesselOfTheAllConsumingWatcher.checkPermanent(game, source);
    }

    @Override
    public String toString() {
        return "it has dealt 10 or more damage to that player this turn";
    }
}

class VesselOfTheAllConsumingWatcher extends Watcher {

    private final Map<AbstractMap.SimpleImmutableEntry<MageObjectReference, UUID>, Integer> morMap = new HashMap<>();

    VesselOfTheAllConsumingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            int damage = event.getAmount();
            morMap.compute(new AbstractMap.SimpleImmutableEntry(new MageObjectReference(permanent, game), event.getTargetId()),
                    (u, i) -> i == null ? damage : Integer.sum(i, damage));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }

    static Watcher makeWatcher() {
        return new VesselOfTheAllConsumingWatcher();
    }

    static boolean checkPermanent(Game game, Ability source) {
        VesselOfTheAllConsumingWatcher watcher = game.getState().getWatcher(VesselOfTheAllConsumingWatcher.class);
        if (watcher == null) {
            return false;
        }
        AbstractMap.SimpleImmutableEntry<MageObjectReference, UUID> key = new AbstractMap.SimpleImmutableEntry<>(
                new MageObjectReference(game.getPermanent(source.getSourceId()), game),
                source.getEffects().get(0).getTargetPointer().getFirst(game, source));
        return watcher.morMap.getOrDefault(key, 0) >= 10;
    }
}
