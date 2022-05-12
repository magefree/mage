package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Storyweave extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAGA);

    public Storyweave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one —
        // • Put two +1/+1 counters on target creature you control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // • Put two lore counters on target Saga you control. The next time one or more enchantment creatures enter the battlefield under your control this turn, each enters with two additional +1/+1 counters on it.
        Mode mode = new Mode(new AddCountersTargetEffect(CounterType.LORE.createInstance(2)));
        mode.addEffect(new StoryweaveReplacementEffect());
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
        this.getSpellAbility().addWatcher(new StoryweaveWatcher());
    }

    private Storyweave(final Storyweave card) {
        super(card);
    }

    @Override
    public Storyweave copy() {
        return new Storyweave(this);
    }

    public static Ability makeAbility() {
        // for testing purposes
        Ability ability = new SimpleActivatedAbility(new StoryweaveReplacementEffect(), new GenericManaCost(0));
        ability.addWatcher(new StoryweaveWatcher());
        return ability;
    }
}

class StoryweaveReplacementEffect extends ReplacementEffectImpl {

    private int counter = 0;

    StoryweaveReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        staticText = "The next time one or more enchantment creatures enter the battlefield " +
                "under your control this turn, each enters with two additional +1/+1 counters on it";
    }

    StoryweaveReplacementEffect(StoryweaveReplacementEffect effect) {
        super(effect);
        this.counter = effect.counter;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.counter = StoryweaveWatcher.getCounter(game, source);
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (StoryweaveWatcher.getCounter(game, source) > counter) {
            discard();
            return false;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isEnchantment(game)
                && permanent.isCreature(game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(
                    CounterType.P1P1.createInstance(2),
                    source.getControllerId(), source,
                    game, event.getAppliedEffects()
            );
        }
        return false;
    }

    @Override
    public StoryweaveReplacementEffect copy() {
        return new StoryweaveReplacementEffect(this);
    }
}

class StoryweaveWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    StoryweaveWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        EntersTheBattlefieldEvent zEvent = ((EntersTheBattlefieldEvent) event);
        if (zEvent.getTarget().isEnchantment(game) && zEvent.getTarget().isCreature(game)) {
            playerMap.compute(zEvent.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.playerMap.clear();
    }

    static int getCounter(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(StoryweaveWatcher.class)
                .playerMap
                .getOrDefault(source.getControllerId(), 0);
    }
}
