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
package mage.abilities.costs.mana;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.util.ManaUtil;

public abstract class ManaCostImpl extends CostImpl implements ManaCost {

    protected Mana payment;
    protected Mana cost;
    protected ManaOptions options;
    protected Filter sourceFilter;

    public ManaCostImpl() {
        payment = new Mana();
        options = new ManaOptions();
    }

    public ManaCostImpl(final ManaCostImpl manaCost) {
        super(manaCost);
        this.payment = manaCost.payment.copy();
        this.cost = manaCost.cost.copy();
        this.options = manaCost.options.copy();
        if (manaCost.sourceFilter != null) {
            this.sourceFilter = manaCost.sourceFilter.copy();
        }
    }

    @Override
    public Mana getPayment() {
        return payment;
    }

    @Override
    public Mana getMana() {
        return cost;
    }

    @Override
    public List<Mana> getManaOptions() {
        List<Mana> manaList = new ArrayList<>();
        manaList.add(cost);
        return manaList;
    }

    @Override
    public ManaOptions getOptions() {
        return options;
    }

    @Override
    public void clearPaid() {
        payment.clear();
        super.clearPaid();
    }

    @Override
    public Filter getSourceFilter() {
        return this.sourceFilter;
    }

    /*
     * Restrict the allowed mana sources to pay the cost
     *
     * e.g. Spend only mana produced by basic lands to cast Imperiosaur.
     * uses:
     *     private static final FilterLandPermanent filter = new FilterLandPermanent();
     *       static { filter.add(new SupertypePredicate("Basic")); }
     *
     * It will be cecked in ManaPool.pay method
     *
     */
    @Override
    public void setSourceFilter(Filter filter) {
        this.sourceFilter = filter;
    }

    protected boolean assignColored(Ability ability, Game game, ManaPool pool, ColoredManaSymbol mana) {
        // first check special mana
        switch (mana) {
            case B:
                if (pool.pay(ManaType.BLACK, ability, sourceFilter, game)) {
                    this.payment.increaseBlack();
                    return true;
                }
                break;
            case U:
                if (pool.pay(ManaType.BLUE, ability, sourceFilter, game)) {
                    this.payment.increaseBlue();
                    return true;
                }
                break;
            case W:
                if (pool.pay(ManaType.WHITE, ability, sourceFilter, game)) {
                    this.payment.increaseWhite();
                    return true;
                }
                break;
            case G:
                if (pool.pay(ManaType.GREEN, ability, sourceFilter, game)) {
                    this.payment.increaseGreen();
                    return true;
                }
                break;
            case R:
                if (pool.pay(ManaType.RED, ability, sourceFilter, game)) {
                    this.payment.increaseRed();
                    return true;
                }
                break;
        }
        return false;
    }

    protected boolean assignColorless(Ability ability, Game game, ManaPool pool, int mana) {
        int conditionalCount = pool.getConditionalCount(ability, game, null);
        while (mana > payment.count() && (pool.count() > 0 || conditionalCount > 0)) {
            if (pool.pay(ManaType.COLORLESS, ability, sourceFilter, game)) {
                this.payment.increaseColorless();
                continue;
            }
            if (pool.pay(ManaType.BLACK, ability, sourceFilter, game)) {
                this.payment.increaseBlack();
                continue;
            }
            if (pool.pay(ManaType.BLUE, ability, sourceFilter, game)) {
                this.payment.increaseBlue();
                continue;
            }
            if (pool.pay(ManaType.WHITE, ability, sourceFilter, game)) {
                this.payment.increaseWhite();
                continue;
            }
            if (pool.pay(ManaType.GREEN, ability, sourceFilter, game)) {
                this.payment.increaseGreen();
                continue;
            }
            if (pool.pay(ManaType.RED, ability, sourceFilter, game)) {
                this.payment.increaseRed();
                continue;
            }
            break;
        }
        return mana > payment.count();
    }

    protected boolean isColoredPaid(ColoredManaSymbol mana) {
        switch (mana) {
            case B:
                return this.payment.getBlack() > 0;
            case U:
                return this.payment.getBlue() > 0;
            case W:
                return this.payment.getWhite() > 0;
            case G:
                return this.payment.getGreen() > 0;
            case R:
                return this.payment.getRed() > 0;
        }
        return false;
    }

    protected boolean isColorlessPaid(int mana) {
        return this.payment.count() >= mana;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (noMana) {
            setPaid();
            return true;
        }
        Player player = game.getPlayer(controllerId);
        assignPayment(game, ability, player.getManaPool());
        game.getState().getSpecialActions().removeManaActions();
        while (!isPaid()) {
            ManaCost unpaid = this.getUnpaid();
            String promptText = ManaUtil.addSpecialManaPayAbilities(ability, game, unpaid);
            if (player.playMana(ability, unpaid, promptText, game)) {
                assignPayment(game, ability, player.getManaPool());
            } else {
                return false;
            }
            game.getState().getSpecialActions().removeManaActions();
        }
        return true;
    }

    @Override
    public void setPaid() {
        this.paid = true;
    }

    @Override
    public void setPayment(Mana mana) {
        this.payment.add(mana);
    }

    protected void addColoredOption(ColoredManaSymbol symbol) {
        switch (symbol) {
            case B:
                this.options.add(Mana.BlackMana);
                break;
            case U:
                this.options.add(Mana.BlueMana);
                break;
            case W:
                this.options.add(Mana.WhiteMana);
                break;
            case R:
                this.options.add(Mana.RedMana);
                break;
            case G:
                this.options.add(Mana.GreenMana);
                break;
        }
    }

}
