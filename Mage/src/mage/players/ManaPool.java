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

import mage.ConditionalMana;
import mage.Constants.ManaType;
import mage.Mana;
import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPool implements Serializable {

    private List<ManaPoolItem> manaItems = new ArrayList<ManaPoolItem>();

	public ManaPool() {}

	public ManaPool(final ManaPool pool) {
        for (ManaPoolItem item: pool.manaItems) {
            manaItems.add(item.copy());
        }
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

    public boolean pay(ManaType manaType, Ability ability, Filter filter, Game game) {
        if (getConditional(manaType, ability, filter, game) > 0) {
            removeConditional(manaType, ability, game);
            return true;
        }
        for (ManaPoolItem mana : manaItems) {
            if (filter == null || filter.match(game.getObject(mana.getSourceId()), game)) {
                if (mana.get(manaType) > 0) {
                    game.fireEvent(new GameEvent(GameEvent.EventType.MANA_PAYED, ability.getId(), mana.getSourceId(), ability.getControllerId()));
                    mana.remove(manaType);
                    return true;
                }
            }
        }
        return false;
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
                if (filter == null || filter.match(game.getObject(mana.getSourceId()), game))
                    return mana.getConditionalMana().get(manaType);
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

	public int emptyPool() {
		int total = count();
        manaItems.clear();
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
            }
            else {
                total += item.count();
                it.remove();
            }
        }
        return total;
	}

    /**
     * remove all mana from pool that applies and that matches filter
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
                        if (c.count() == 0)
                            it.remove();
                    }
                }
            }
            else {
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
                if (item.count() == 0)
                    it.remove();
            }
        }
        return total;
	}

	public Mana getMana() {
		Mana m = new Mana();
        for (ManaPoolItem item: manaItems) {
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
		if (filter.isBlack()) m.setBlack(test.getBlack());
		if (filter.isBlue()) m.setBlue(test.getBlue());
		if (filter.isColorless()) m.setColorless(test.getColorless());
		if (filter.isGreen()) m.setGreen(test.getGreen());
		if (filter.isRed()) m.setRed(test.getRed());
		if (filter.isWhite()) m.setWhite(test.getWhite());
		return m;
	}

	public Mana getAllConditionalMana(Ability ability, Game game, FilterMana filter) {
		Mana m = new Mana();
		m.setColorless(getConditionalCount(ability, game, filter));
		return m;
	}

	public void addMana(Mana mana, Game game, Ability source) {
		if (mana instanceof ConditionalMana) {
            this.manaItems.add(new ManaPoolItem((ConditionalMana)mana, source.getSourceId()));
		} else {
            this.manaItems.add(new ManaPoolItem(mana.getRed(), mana.getGreen(), mana.getBlue(), mana.getWhite(), mana.getBlack(), mana.getColorless(), source.getSourceId()));
		}
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.MANA_ADDED, source.getSourceId(), source.getId(), source.getControllerId());
        event.setData(mana.toString());
        game.fireEvent(event);
	}

	public List<ConditionalMana> getConditionalMana() {
        List<ConditionalMana> conditionalMana = new ArrayList<ConditionalMana>();
        for (ManaPoolItem item: manaItems) {
            if (item.isConditional()) {
                conditionalMana.add(item.getConditionalMana());
            }
        }
		return conditionalMana;
	}

	public int count() {
        int x = 0;
        for (ManaPoolItem item: manaItems) {
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
                game.fireEvent(new GameEvent(GameEvent.EventType.MANA_PAYED, ability.getId(), mana.getManaProducerId(), ability.getControllerId()));
                break;
			}
		}
	}
}
