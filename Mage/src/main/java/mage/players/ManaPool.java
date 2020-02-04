package mage.players;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.TurnPhase;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.stack.Spell;

import java.io.Serializable;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPool implements Serializable {

    private final UUID playerId;

    private final List<ManaPoolItem> manaItems = new ArrayList<>();

    private boolean autoPayment; // auto payment from mana pool: true - mode is active
    private boolean autoPaymentRestricted; // auto payment from mana pool: true - if auto Payment is on, it will only pay if one kind of mana is in the pool
    private ManaType unlockedManaType; // type of mana that was selected to pay manually
    private boolean forcedToPay; // for Word of Command
    private final List<ManaPoolItem> poolBookmark = new ArrayList<>(); // mana pool bookmark for rollback purposes

    private final Set<ManaType> doNotEmptyManaTypes = new HashSet<>();

    public ManaPool(UUID playerId) {
        this.playerId = playerId;
        autoPayment = true;
        autoPaymentRestricted = true;
        unlockedManaType = null;
        forcedToPay = false;
    }

    public ManaPool(final ManaPool pool) {
        this.playerId = pool.playerId;
        for (ManaPoolItem item : pool.manaItems) {
            manaItems.add(item.copy());
        }
        this.autoPayment = pool.autoPayment;
        this.autoPaymentRestricted = pool.autoPaymentRestricted;
        this.unlockedManaType = pool.unlockedManaType;
        this.forcedToPay = pool.forcedToPay;
        for (ManaPoolItem item : pool.poolBookmark) {
            poolBookmark.add(item.copy());
        }
        this.doNotEmptyManaTypes.addAll(pool.doNotEmptyManaTypes);
    }

    public int getRed() {
        return get(ManaType.RED);
    }

    public int getGreen() {
        return get(ManaType.GREEN);
    }

    public int getBlue() {
        return get(ManaType.BLUE);
    }

    public int getWhite() {
        return get(ManaType.WHITE);
    }

    public int getBlack() {
        return get(ManaType.BLACK);
    }

    /**
     * @param manaType      the mana type that should be paid
     * @param ability
     * @param filter
     * @param game
     * @param costToPay     complete costs to pay (needed to check conditional mana)
     * @param usedManaToPay the information about what mana was paid
     * @return
     */
    public boolean pay(ManaType manaType, Ability ability, Filter filter, Game game, Cost costToPay, Mana usedManaToPay) {
        if (!isAutoPayment()
                && manaType != unlockedManaType) {
            // if manual payment and the needed mana type was not unlocked, nothing will be paid
            return false;
        }
        ManaType possibleAsThoughPoolManaType = null;
        if (isAutoPayment()
                && isAutoPaymentRestricted()
                && !wasManaAddedBeyondStock()
                && manaType != unlockedManaType) {
            // if automatic restricted payment and there is already mana in the pool
            // and the needed mana type was not unlocked, nothing will be paid
            if (unlockedManaType != null) {
                ManaPoolItem checkItem = new ManaPoolItem();
                checkItem.add(unlockedManaType, 1);
                possibleAsThoughPoolManaType = game.getContinuousEffects().asThoughMana(manaType, checkItem, ability.getSourceId(), ability, ability.getControllerId(), game);
            }
            // Check if it's possible to use mana as thought for the unlocked manatype in the mana pool for this ability
            if (possibleAsThoughPoolManaType == null
                    || possibleAsThoughPoolManaType != unlockedManaType) {
                return false; // if it's not possible return
            }
        }

        if (getConditional(manaType, ability, filter, game, costToPay) > 0) {
            removeConditional(manaType, ability, game, costToPay, usedManaToPay);
            lockManaType(); // pay only one mana if mana payment is set to manually
            return true;
        }

        for (ManaPoolItem mana : manaItems) {
            if (filter != null) {
                if (!filter.match(mana.getSourceObject(), game)) {
                    // Prevent that cost reduction by convoke is filtered out
                    if (!(mana.getSourceObject() instanceof Spell)
                            || ability.getSourceId().equals(mana.getSourceId())) {
                        continue;
                    }
                }
            }
            if (possibleAsThoughPoolManaType == null
                    && manaType != unlockedManaType
                    && isAutoPayment()
                    && isAutoPaymentRestricted()
                    && mana.count() == mana.getStock()) {
                // no mana added beyond the stock so don't auto pay this
                continue;
            }
            ManaType usableManaType = game.getContinuousEffects().asThoughMana(manaType, mana, ability.getSourceId(), ability, ability.getControllerId(), game);
            if (usableManaType == null) {
                continue;
            }
            if (mana.get(usableManaType) > 0) {
                GameEvent event = new GameEvent(GameEvent.EventType.MANA_PAID, ability.getId(), mana.getSourceId(), ability.getControllerId(), 0, mana.getFlag());
                event.setData(mana.getOriginalId().toString());
                game.fireEvent(event);
                usedManaToPay.increase(mana.getFirstAvailable());
                mana.remove(usableManaType);
                if (mana.count() == 0) { // so no items with count 0 stay in list
                    manaItems.remove(mana);
                }
                lockManaType(); // pay only one mana if mana payment is set to manually
                return true;
            }
        }
        return false;
    }

    public int get(ManaType manaType) {
        return getMana().get(manaType);
    }

    private int getConditional(ManaType manaType, Ability ability, Filter filter, Game game, Cost costToPay) {
        if (ability == null || getConditionalMana().isEmpty()) {
            return 0;
        }
        for (ManaPoolItem mana : manaItems) {
            if (mana.isConditional()
                    && mana.getConditionalMana().get(manaType) > 0
                    && mana.getConditionalMana().apply(ability, game, mana.getSourceId(), costToPay)) {
                if (filter == null
                        || filter.match(mana.getSourceObject(), game)) {
                    return mana.getConditionalMana().get(manaType);
                }
            }
        }
        return 0;
    }

    public int getConditionalCount(Ability ability, Game game, FilterMana filter, Cost costToPay) {
        if (ability == null
                || getConditionalMana().isEmpty()) {
            return 0;
        }
        int count = 0;
        for (ConditionalMana mana : getConditionalMana()) {
            if (mana.apply(ability, game, mana.getManaProducerId(), costToPay)) {
                count += mana.count(filter);
            }
        }
        return count;
    }

    public int getColorless() {
        return get(ManaType.COLORLESS);
    }

    public void clearEmptyManaPoolRules() {
        doNotEmptyManaTypes.clear();
    }

    public void addDoNotEmptyManaType(ManaType manaType) {
        doNotEmptyManaTypes.add(manaType);
    }

    public void init() {
        manaItems.clear();
    }

    public int emptyPool(Game game) {
        int total = 0;
        Iterator<ManaPoolItem> it = manaItems.iterator();
        while (it.hasNext()) {
            ManaPoolItem item = it.next();
            ConditionalMana conditionalItem = item.getConditionalMana();
            for (ManaType manaType : ManaType.values()) {
                if (!doNotEmptyManaTypes.contains(manaType)) {
                    if (item.get(manaType) > 0) {
                        if (item.getDuration() != Duration.EndOfTurn
                                || game.getPhase().getType() == TurnPhase.END) {
                            if (game.replaceEvent(new GameEvent(GameEvent.EventType.EMPTY_MANA_POOL, playerId, null, playerId))) {
                                int amount = item.get(manaType);
                                item.clear(manaType);
                                item.add(ManaType.COLORLESS, amount);
                            } else {
                                total += item.get(manaType);
                                item.clear(manaType);
                            }
                        }
                    }
                    if (conditionalItem != null) {
                        if (conditionalItem.get(manaType) > 0) {
                            if (item.getDuration() != Duration.EndOfTurn
                                    || game.getPhase().getType() == TurnPhase.END) {
                                if (game.replaceEvent(new GameEvent(GameEvent.EventType.EMPTY_MANA_POOL, playerId, null, playerId))) {
                                    int amount = conditionalItem.get(manaType);
                                    conditionalItem.clear(manaType);
                                    conditionalItem.add(ManaType.COLORLESS, amount);
                                } else {
                                    total += conditionalItem.get(manaType);
                                    conditionalItem.clear(manaType);
                                }
                            }
                        }
                    }
                }
            }
            if (item.count() == 0) {
                it.remove();
            }
        }
        return total;
    }

    public Mana getMana() {
        Mana m = new Mana();
        for (ManaPoolItem item : manaItems) {
            m.add(item.getMana());
        }
        return m;
    }

    public Mana getMana(FilterMana filter) {
        if (filter == null) {
            return getMana();
        }
        Mana test = getMana();
        Mana m = new Mana();
        if (filter.isBlack()) {
            m.setBlack(test.getBlack());
        }
        if (filter.isBlue()) {
            m.setBlue(test.getBlue());
        }
        if (filter.isGreen()) {
            m.setGreen(test.getGreen());
        }
        if (filter.isRed()) {
            m.setRed(test.getRed());
        }
        if (filter.isWhite()) {
            m.setWhite(test.getWhite());
        }
        if (filter.isColorless()) {
            m.setColorless(test.getColorless());
        }
        if (filter.isGeneric()) {
            m.setGeneric(test.getGeneric());
        }
        return m;
    }

    public void addMana(Mana manaToAdd, Game game, Ability source) {
        addMana(manaToAdd, game, source, false);
    }

    public void addMana(Mana manaToAdd, Game game, Ability source, boolean emptyOnTurnsEnd) {
        if (manaToAdd != null) {
            Mana mana = manaToAdd.copy();
            if (!game.replaceEvent(new ManaEvent(EventType.ADD_MANA, source.getId(), source.getSourceId(), playerId, mana))) {
                if (mana instanceof ConditionalMana) {
                    ManaPoolItem item = new ManaPoolItem((ConditionalMana) mana, source.getSourceObject(game),
                            ((ConditionalMana) mana).getManaProducerOriginalId() != null
                                    ? ((ConditionalMana) mana).getManaProducerOriginalId() : source.getOriginalId());
                    if (emptyOnTurnsEnd) {
                        item.setDuration(Duration.EndOfTurn);
                    }
                    this.manaItems.add(item);
                } else {
                    ManaPoolItem item = new ManaPoolItem(mana.getRed(), mana.getGreen(), mana.getBlue(), mana.getWhite(), mana.getBlack(), mana.getGeneric() + mana.getColorless(), source.getSourceObject(game), source.getOriginalId(), mana.getFlag());
                    if (emptyOnTurnsEnd) {
                        item.setDuration(Duration.EndOfTurn);
                    }
                    this.manaItems.add(item);
                }
                ManaEvent manaEvent = new ManaEvent(EventType.MANA_ADDED, source.getId(), source.getSourceId(), playerId, mana);
                manaEvent.setData(mana.toString());
                game.fireEvent(manaEvent);
            }
        }
    }

    public List<ConditionalMana> getConditionalMana() {
        List<ConditionalMana> conditionalMana = new ArrayList<>();
        for (ManaPoolItem item : manaItems) {
            if (item.isConditional()) {
                conditionalMana.add(item.getConditionalMana());
            }
        }
        return conditionalMana;
    }

    public boolean ConditionalManaHasManaType(ManaType manaType) {
        for (ManaPoolItem item : manaItems) {
            if (item.isConditional()) {
                if (item.getConditionalMana().get(manaType) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int count() {
        int x = 0;
        for (ManaPoolItem item : manaItems) {
            x += item.count();
        }
        return x;
    }

    public ManaPool copy() {
        return new ManaPool(this);
    }

    private void removeConditional(ManaType manaType, Ability ability, Game game, Cost costToPay, Mana usedManaToPay) {
        for (ConditionalMana mana : getConditionalMana()) {
            if (mana.get(manaType) > 0 && mana.apply(ability, game, mana.getManaProducerId(), costToPay)) {
                mana.set(manaType, mana.get(manaType) - 1);
                usedManaToPay.increase(manaType);
                GameEvent event = new GameEvent(GameEvent.EventType.MANA_PAID, ability.getId(), mana.getManaProducerId(), ability.getControllerId(), 0, mana.getFlag());
                event.setData(mana.getManaProducerOriginalId().toString());
                game.fireEvent(event);
                break;
            }
        }
    }

    public boolean isAutoPayment() {
        return autoPayment || forcedToPay;
    }

    public void setAutoPayment(boolean autoPayment) {
        this.autoPayment = autoPayment;
    }

    public boolean isAutoPaymentRestricted() {
        return autoPaymentRestricted || forcedToPay;
    }

    public void setAutoPaymentRestricted(boolean autoPaymentRestricted) {
        this.autoPaymentRestricted = autoPaymentRestricted;
    }

    public ManaType getUnlockedManaType() {
        return unlockedManaType;
    }

    public void unlockManaType(ManaType manaType) {
        this.unlockedManaType = manaType;
    }

    public void lockManaType() {
        this.unlockedManaType = null;
    }

    public void setStock() {
        for (ManaPoolItem mana : manaItems) {
            mana.setStock(mana.count());
        }
    }

    private boolean wasManaAddedBeyondStock() {
        for (ManaPoolItem mana : manaItems) {
            if (mana.getStock() < mana.count()) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return count() == 0;
    }

    public List<ManaPoolItem> getManaItems() {
        List<ManaPoolItem> itemsCopy = new ArrayList<>();
        for (ManaPoolItem manaItem : manaItems) {
            itemsCopy.add(manaItem.copy());
        }
        return itemsCopy;
    }

    public void setForcedToPay(boolean forcedToPay) {
        this.forcedToPay = forcedToPay;
    }

    public boolean isForcedToPay() {
        return forcedToPay;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void storeMana() {
        poolBookmark.clear();
        poolBookmark.addAll(getManaItems());
    }

    public List<ManaPoolItem> getPoolBookmark() {
        List<ManaPoolItem> itemsCopy = new ArrayList<>();
        for (ManaPoolItem manaItem : poolBookmark) {
            itemsCopy.add(manaItem.copy());
        }
        return itemsCopy;
    }

    public void restoreMana(List<ManaPoolItem> manaList) {
        manaItems.clear();
        if (!manaList.isEmpty()) {
            List<ManaPoolItem> itemsCopy = new ArrayList<>();
            for (ManaPoolItem manaItem : manaList) {
                itemsCopy.add(manaItem.copy());
            }
            manaItems.addAll(itemsCopy);
        }
    }
}
