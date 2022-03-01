package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
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
 * @author TheElk801
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
                new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
                HotheadedGiantWatcher::checkSpell, null,
                "\"with two -1/-1 counters on it unless you've cast another red spell this turn\""
        ), new HotheadedGiantWatcher());
    }

    private HotheadedGiant(final HotheadedGiant card) {
        super(card);
    }

    @Override
    public HotheadedGiant copy() {
        return new HotheadedGiant(this);
    }
}

class HotheadedGiantWatcher extends Watcher {

    private final Map<UUID, List<MageObjectReference>> spellMap = new HashMap<>();
    private static final List<MageObjectReference> emptyList = new ArrayList<>();

    HotheadedGiantWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.getColor(game).isRed()) {
            spellMap.computeIfAbsent(event.getPlayerId(), x -> new ArrayList<>())
                    .add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellMap.clear();
    }

    static boolean checkSpell(Game game, Ability source) {
        return game.getState()
                .getWatcher(HotheadedGiantWatcher.class)
                .spellMap
                .getOrDefault(source.getControllerId(), emptyList)
                .stream()
                .noneMatch(mor -> !mor.refersTo(source, game));
    }
}
