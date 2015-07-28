/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.TurnPhase;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPool implements Serializable {

    private final UUID playerId;

    private final List<ManaPoolItem> manaItems = new ArrayList<>();

    private boolean autoPayment; // auto payment from mana pool: true - mode is active
    private boolean autoPaymentRestricted; // auto payment from mana pool: true - if auto Payment is on, it will only pay if one kind of mana is in the pool
    private ManaType unlockedManaType; // type of mana that was selected to pay manually

    private final Set<ManaType> doNotEmptyManaTypes = new HashSet<>();

    public ManaPool(UUID playerId) {
        this.playerId = playerId;
        autoPayment = true;
        autoPaymentRestricted = true;
        unlockedManaType = null;
    }

    public ManaPool(final ManaPool pool) {
        this.playerId = pool.playerId;
        for (ManaPoolItem item : pool.manaItems) {
            manaItems.add(item.copy());
        }
        this.autoPayment = pool.autoPayment;
        this.autoPaymentRestricted = pool.autoPaymentRestricted;
        this.unlockedManaType = pool.unlockedManaType;
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
     *
     * @param manaType the mana type that should be paid
     * @param ability
     * @param filter
     * @param game
     * @return
     */
    public boolean pay(ManaType manaType, Ability ability, Filter filter, Game game) {
        if (!autoPayment && !manaType.equals(unlockedManaType)) {
            // if manual payment and the needed mana type was not unlocked, nothing will be paid
            return false;
        }
        if (autoPayment && autoPaymentRestricted && !wasManaAddedBeyondStock() && !manaType.equals(unlockedManaType)) {
            // if automatic restricted payment and there is laready mana in the pool
            // and the needed mana type was not unlocked, nothing will be paid
            return false;
        }

        if (getConditional(manaType, ability, filter, game) > 0) {
            removeConditional(manaType, ability, game);
            lockManaType(); // pay only one mana if mana payment is set to manually
            return true;
        }
        for (ManaPoolItem mana : manaItems) {
            if (filter != null) {
                MageObject sourceObject = game.getObject(mana.getSourceId());
                if (!filter.match(sourceObject, game)) {
                    continue;
                }
            }
            if (!manaType.equals(unlockedManaType) && autoPayment && autoPaymentRestricted && mana.count() == mana.getStock()) {
                // no mana added beyond the stock so don't auto pay this
                continue;
            }
            boolean spendAnyMana = spendAnyMana(ability, game);
            if (mana.get(manaType) > 0 || (spendAnyMana && mana.count() > 0)) {
                GameEvent event = new GameEvent(GameEvent.EventType.MANA_PAYED, ability.getId(), mana.getSourceId(), ability.getControllerId(), 0, mana.getFlag());
                event.setData(mana.getOriginalId().toString());
                game.fireEvent(event);
                if (spendAnyMana) {
                    mana.removeAny();
                } else {
                    mana.remove(manaType);
                }
                if (mana.count() == 0) { // so no items with count 0 stay in list
                    manaItems.remove(mana);
                }
                lockManaType(); // pay only one mana if mana payment is set to manually
                return true;
            }
        }
        return false;
    }

    // check if any mana can be spend to cast the mana cost of an ability
    private boolean spendAnyMana(Ability ability, Game game) {
        return game.getContinuousEffects().asThough(ability.getSourceId(), AsThoughEffectType.SPEND_ANY_MANA, ability, ability.getControllerId(), game);
    }

    public int get(ManaType manaType) {
        return getMana().get(manaType);
    }

    private int getConditional(ManaType manaType, Ability ability, Filter filter, Game game) {
        if (ability == null || getConditionalMana().isEmpty()) {
            return 0;
        }
        for (ManaPoolItem mana : manaItems) {
            if (mana.isConditional() && mana.getConditionalMana().get(manaType) > 0 && mana.getConditionalMana().apply(ability, game, mana.getSourceId())) {
                if (filter == null || filter.match(game.getObject(mana.getSourceId()), game)) {
                    return mana.getConditionalMana().get(manaType);
                }
            }
        }
        return 0;
    }

    public int getConditionalCount(Ability ability, Game game, FilterMana filter) {
        if (ability == null || getConditionalMana().isEmpty()) {
            return 0;
        }
        int count = 0;
        for (ConditionalMana mana : getConditionalMana()) {
            if (mana.apply(ability, game, mana.getManaProducerId())) {
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

    public int emptyPool(Game game) {
        int total = 0;
        Iterator<ManaPoolItem> it = manaItems.iterator();
        while (it.hasNext()) {
            ManaPoolItem item = it.next();
            ConditionalMana conditionalItem = item.getConditionalMana();
            for (ManaType manaType : ManaType.values()) {
                if (!doNotEmptyManaTypes.contains(manaType)) {
                    if (item.get(manaType) > 0) {
                        if (!item.getDuration().equals(Duration.EndOfTurn) || game.getPhase().getType().equals(TurnPhase.END)) {
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
                            if (!item.getDuration().equals(Duration.EndOfTurn) || game.getPhase().getType().equals(TurnPhase.END)) {
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

    private int payX(Ability ability, Game game) {
        int total = 0;
        Iterator<ManaPoolItem> it = manaItems.iterator();
        while (it.hasNext()) {
            ManaPoolItem item = it.next();
            if (item.isConditional()) {
                ConditionalMana cm = item.getConditionalMana();
                if (cm.apply(ability, game, cm.getManaProducerId())) {
                    total += item.count();
                    it.remove();
                }
            } else {
                total += item.count();
                it.remove();
            }
        }
        return total;
    }

    /**
     * remove all mana from pool that applies and that matches filter
     *
     * @param ability
     * @param game
     * @param filter
     * @return
     */
    public int payX(Ability ability, Game game, FilterMana filter) {
        if (filter == null) {
            return payX(ability, game);
        }
        int total = 0;
        Iterator<ManaPoolItem> it = manaItems.iterator();
        while (it.hasNext()) {
            ManaPoolItem item = it.next();
            if (item.isConditional()) {
                ConditionalMana c = item.getConditionalMana();
                if (c.apply(ability, game, c.getManaProducerId())) {
                    int count = c.count(filter);
                    if (count > 0) {
                        total += count;
                        c.removeAll(filter);
                        if (c.count() == 0) {
                            it.remove();
                        }
                    }
                }
            } else {
                if (filter.isBlack()) {
                    total += item.getBlack();
                    item.removeBlack();
                }
                if (filter.isBlue()) {
                    total += item.getBlue();
                    item.removeBlue();
                }
                if (filter.isWhite()) {
                    total += item.getWhite();
                    item.removeWhite();
                }
                if (filter.isRed()) {
                    total += item.getRed();
                    item.removeRed();
                }
                if (filter.isGreen()) {
                    total += item.getGreen();
                    item.removeGreen();
                }
                if (filter.isColorless()) {
                    total += item.getColorless();
                    item.removeColorless();
                }
                if (item.count() == 0) {
                    it.remove();
                }
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
        if (filter.isColorless()) {
            m.setColorless(test.getColorless());
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
        return m;
    }

    public Mana getAllConditionalMana(Ability ability, Game game, FilterMana filter) {
        Mana m = new Mana();
        m.setColorless(getConditionalCount(ability, game, filter));
        return m;
    }

    public void addMana(Mana manaToAdd, Game game, Ability source) {
        addMana(manaToAdd, game, source, false);
    }

    public void addMana(Mana manaToAdd, Game game, Ability source, boolean emptyOnTurnsEnd) {
        Mana mana = manaToAdd.copy();
        if (!game.replaceEvent(new ManaEvent(EventType.ADD_MANA, source.getId(), source.getSourceId(), playerId, mana))) {
            if (mana instanceof ConditionalMana) {
                ManaPoolItem item = new ManaPoolItem((ConditionalMana) mana, source.getSourceId(), source.getOriginalId());
                if (emptyOnTurnsEnd) {
                    item.setDuration(Duration.EndOfTurn);
                }
                this.manaItems.add(item);
            } else {
                ManaPoolItem item = new ManaPoolItem(mana.getRed(), mana.getGreen(), mana.getBlue(), mana.getWhite(), mana.getBlack(), mana.getColorless(), source.getSourceId(), source.getOriginalId(), mana.getFlag());
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

    public List<ConditionalMana> getConditionalMana() {
        List<ConditionalMana> conditionalMana = new ArrayList<>();
        for (ManaPoolItem item : manaItems) {
            if (item.isConditional()) {
                conditionalMana.add(item.getConditionalMana());
            }
        }
        return conditionalMana;
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

    private void removeConditional(ManaType manaType, Ability ability, Game game) {
        for (ConditionalMana mana : getConditionalMana()) {
            if (mana.get(manaType) > 0 && mana.apply(ability, game, mana.getManaProducerId())) {
                mana.set(manaType, mana.get(manaType) - 1);
                GameEvent event = new GameEvent(GameEvent.EventType.MANA_PAYED, ability.getId(), mana.getManaProducerId(), ability.getControllerId(), 0, mana.getFlag());
                event.setData(mana.getManaProducerOriginalId().toString());
                game.fireEvent(event);
                break;
            }
        }
    }

    public boolean isAutoPayment() {
        return autoPayment;
    }

    public void setAutoPayment(boolean autoPayment) {
        this.autoPayment = autoPayment;
    }

    public void setAutoPaymentRestricted(boolean autoPaymentRestricted) {
        this.autoPaymentRestricted = autoPaymentRestricted;
    }

    public boolean isAutoPaymentRestricted() {
        return autoPaymentRestricted;
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
}
