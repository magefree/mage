
package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author jeffwadsworth
 */
public final class HotheadedGiant extends CardImpl {

    public HotheadedGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Hotheaded Giant enters the battlefield with two -1/-1 counters on it unless you've cast another red spell this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
                        CastRedSpellThisTurnCondition.instance, ""
                ), "with two -1/-1 counters on it unless you've cast another red spell this turn"
        ), new HotHeadedGiantWatcher());
    }

    private HotheadedGiant(final HotheadedGiant card) {
        super(card);
    }

    @Override
    public HotheadedGiant copy() {
        return new HotheadedGiant(this);
    }
}

enum CastRedSpellThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        HotHeadedGiantWatcher watcher = game.getState().getWatcher(HotHeadedGiantWatcher.class);
        if (watcher != null) {
            return watcher.conditionMet(source, game);
        }
        return true;
    }
}

class HotHeadedGiantWatcher extends Watcher {
    private final Map<UUID, Set<MageObjectReference>> playerMap = new HashMap();

    HotHeadedGiantWatcher() {
        super(HotHeadedGiantWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    private HotHeadedGiantWatcher(final HotHeadedGiantWatcher watcher) {
        super(watcher);
        this.playerMap.putAll(watcher.playerMap);
    }

    @Override
    public HotHeadedGiantWatcher copy() {
        return new HotHeadedGiantWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell.getColor(game).isRed()) {
                playerMap.putIfAbsent(event.getPlayerId(), new HashSet());
                playerMap.get(event.getPlayerId()).add(new MageObjectReference(spell, game));
            }
        }
    }

    public boolean conditionMet(Ability source, Game game) {
        if (!playerMap.containsKey(source.getControllerId())) {
            return true;
        }
        for (MageObjectReference mor : playerMap.get(source.getControllerId())) {
            if (!mor.refersTo(source.getSourceId(), game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }
}
