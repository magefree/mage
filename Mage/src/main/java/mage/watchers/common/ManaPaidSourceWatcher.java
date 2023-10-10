package mage.watchers.common;

import mage.MageObject;
import mage.ObjectColor;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaPaidEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.util.Copyable;
import mage.watchers.Watcher;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Default watcher, no need to add it to ability
 *
 * @author TheElk801
 */
public class ManaPaidSourceWatcher extends Watcher {

    private static final class ManaPaidTracker implements Serializable, Copyable<ManaPaidTracker> {

        private int total = 0;
        private int whiteSnow = 0;
        private int blueSnow = 0;
        private int blackSnow = 0;
        private int redSnow = 0;
        private int greenSnow = 0;
        private int colorlessSnow = 0;
        private int treasure = 0;
        private int creature = 0;

        private ManaPaidTracker() {
            super();
        }

        private ManaPaidTracker(final ManaPaidTracker tracker) {
            this.total = tracker.total;
            this.whiteSnow = tracker.whiteSnow;
            this.blueSnow = tracker.blueSnow;
            this.blackSnow = tracker.blackSnow;
            this.redSnow = tracker.redSnow;
            this.greenSnow = tracker.greenSnow;
            this.colorlessSnow = tracker.colorlessSnow;
            this.treasure = tracker.treasure;
            this.creature = tracker.creature;
        }

        @Override
        public ManaPaidTracker copy() {
            return new ManaPaidTracker(this);
        }

        private void increment(MageObject sourceObject, ManaType manaType, Game game) {
            total++;
            if (sourceObject != null && sourceObject.hasSubtype(SubType.TREASURE, game)) {
                treasure++;
            }
            if (sourceObject != null && sourceObject.isCreature(game)) {
                creature++;
            }
            if (sourceObject != null && !sourceObject.isSnow(game)) {
                return;
            }
            switch (manaType) {
                case WHITE:
                    whiteSnow++;
                    break;
                case BLUE:
                    blueSnow++;
                    break;
                case BLACK:
                    blackSnow++;
                    break;
                case RED:
                    redSnow++;
                    break;
                case GREEN:
                    greenSnow++;
                    break;
                case COLORLESS:
                case GENERIC:
                    colorlessSnow++;
                    break;
            }
        }

        private int getSnow() {
            return whiteSnow + blueSnow + blackSnow + redSnow + greenSnow + colorlessSnow;
        }

        private boolean checkSnowColor(Spell spell, Game game) {
            ObjectColor color = spell.getColor(game);
            return color.isWhite() && whiteSnow > 0
                    || color.isBlue() && blueSnow > 0
                    || color.isBlack() && blackSnow > 0
                    || color.isRed() && redSnow > 0
                    || color.isGreen() && greenSnow > 0;
        }
    }

    private static final ManaPaidTracker emptyTracker = new ManaPaidTracker();
    private final Map<UUID, ManaPaidTracker> manaMap = new HashMap<>();

    public ManaPaidSourceWatcher() {
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
                manaMap.computeIfAbsent(manaEvent.getTargetId(), x -> new ManaPaidTracker())
                        .increment(manaEvent.getSourceObject(), manaEvent.getManaType(), game);
                manaMap.computeIfAbsent(manaEvent.getSourcePaidId(), x -> new ManaPaidTracker())
                        .increment(manaEvent.getSourceObject(), manaEvent.getManaType(), game);
        }
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
    }

    public static int getTotalPaid(UUID sourceId, Game game) {
        ManaPaidSourceWatcher watcher = game.getState().getWatcher(ManaPaidSourceWatcher.class);
        return watcher == null ? 0 : watcher.manaMap.getOrDefault(sourceId, emptyTracker).total;
    }

    public static int getTreasurePaid(UUID sourceId, Game game) {
        ManaPaidSourceWatcher watcher = game.getState().getWatcher(ManaPaidSourceWatcher.class);
        return watcher == null ? 0 : watcher.manaMap.getOrDefault(sourceId, emptyTracker).treasure;
    }

    public static int getCreaturePaid(UUID sourceId, Game game) {
        ManaPaidSourceWatcher watcher = game.getState().getWatcher(ManaPaidSourceWatcher.class);
        return watcher == null ? 0 : watcher.manaMap.getOrDefault(sourceId, emptyTracker).creature;
    }

    public static int getSnowPaid(UUID sourceId, Game game) {
        ManaPaidSourceWatcher watcher = game.getState().getWatcher(ManaPaidSourceWatcher.class);
        return watcher == null ? 0 : watcher.manaMap.getOrDefault(sourceId, emptyTracker).getSnow();
    }

    public static boolean checkSnowColor(Spell spell, Game game) {
        ManaPaidSourceWatcher watcher = game.getState().getWatcher(ManaPaidSourceWatcher.class);
        return watcher != null && watcher.manaMap.getOrDefault(spell.getSpellAbility().getId(), emptyTracker).checkSnowColor(spell, game);
    }

    public void testsIncrementManaAmount(Game game, MageObject mageObject) {
        // for tests only (logic here: change data in tracker like real event do)
        this.manaMap.getOrDefault(mageObject.getId(), null).increment(mageObject, ManaType.RED, game);
    }

    public int testsReturnTotal(MageObject mageObject) {
        // for tests only
        return this.manaMap.getOrDefault(mageObject.getId(), null).total;
    }
}
