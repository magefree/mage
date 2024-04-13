package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaPaidEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.TreasureToken;
import mage.util.Copyable;
import mage.watchers.Watcher;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class CataclysmicProspecting extends CardImpl {

    private static final Hint hint = new ValueHint("Mana spent from a Desert", CataclysmicProspectingValue.instance);

    public CataclysmicProspecting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Cataclysmic Prospecting deals X damage to each creature. For each mana from a Desert spent to cast this spell, create a tapped Treasure token.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), CataclysmicProspectingValue.instance, true, false)
                .setText("For each mana from a Desert spent to cast this spell, create a tapped Treasure token."));
        this.getSpellAbility().addWatcher(new CataclysmicProspectingWatcher());
        this.getSpellAbility().addHint(hint);
    }

    private CataclysmicProspecting(final CataclysmicProspecting card) {
        super(card);
    }

    @Override
    public CataclysmicProspecting copy() {
        return new CataclysmicProspecting(this);
    }
}

enum CataclysmicProspectingValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return sourceAbility == null ? 0 : CataclysmicProspectingWatcher.getDesertsAmount(sourceAbility.getSourceId(), game);
    }

    @Override
    public CataclysmicProspectingValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "mana from a Desert spent to cast it";
    }

}

/**
 * Inspired by {@link mage.watchers.common.ManaPaidSourceWatcher}
 * If more cards like Cataclysmic care for mana produced by Deserts in the future, best to refactor the tracking there.
 * For now the assumption is that it is a 1of, so don't want to track it in any game.
 */
class CataclysmicProspectingWatcher extends Watcher {

    private static final class DesertManaPaidTracker implements Serializable, Copyable<DesertManaPaidTracker> {
        private int desertMana = 0;

        private DesertManaPaidTracker() {
            super();
        }

        private DesertManaPaidTracker(final DesertManaPaidTracker tracker) {
            this.desertMana = tracker.desertMana;
        }

        @Override
        public DesertManaPaidTracker copy() {
            return new DesertManaPaidTracker(this);
        }

        private void increment(MageObject sourceObject, Game game) {
            if (sourceObject != null && sourceObject.hasSubtype(SubType.DESERT, game)) {
                desertMana++;
            }
        }
    }

    private static final DesertManaPaidTracker emptyTracker = new DesertManaPaidTracker();
    private final Map<UUID, DesertManaPaidTracker> manaMap = new HashMap<>();

    public CataclysmicProspectingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ZONE_CHANGE:
                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                        // Bug #9943 Memory Deluge cast from graveyard during the same turn
                        || ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
                    manaMap.remove(event.getTargetId());
                }
                return;
            case MANA_PAID:
                ManaPaidEvent manaEvent = (ManaPaidEvent) event;
                manaMap.computeIfAbsent(manaEvent.getTargetId(), x -> new DesertManaPaidTracker())
                        .increment(manaEvent.getSourceObject(), game);
                manaMap.computeIfAbsent(manaEvent.getSourcePaidId(), x -> new DesertManaPaidTracker())
                        .increment(manaEvent.getSourceObject(), game);
        }
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
    }

    public static int getDesertsAmount(UUID sourceId, Game game) {
        CataclysmicProspectingWatcher watcher = game.getState().getWatcher(CataclysmicProspectingWatcher.class);
        return watcher == null ? 0 : watcher.manaMap.getOrDefault(sourceId, emptyTracker).desertMana;
    }
}
