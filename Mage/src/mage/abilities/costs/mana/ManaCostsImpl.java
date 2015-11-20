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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Targets;
import mage.util.ManaUtil;

/**
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public class ManaCostsImpl<T extends ManaCost> extends ArrayList<T> implements ManaCosts<T> {

    protected UUID id;
    protected String text = null;

    private static Map<String, ManaCosts> costs = new HashMap<>();

    public ManaCostsImpl() {
        this.id = UUID.randomUUID();
    }

    public ManaCostsImpl(String mana) {
        this.id = UUID.randomUUID();
        load(mana);
    }

    public ManaCostsImpl(final ManaCostsImpl<T> costs) {
        this.id = costs.id;
        for (T cost : costs) {
            this.add(cost.copy());
        }
    }

    @Override
    public final boolean add(ManaCost cost) {
        if (cost instanceof ManaCosts) {
            for (ManaCost manaCost : (ManaCosts<T>) cost) {
                super.add((T) manaCost);
            }
            return true;
        } else {
            return super.add((T) cost);
        }
    }

    @Override
    public int convertedManaCost() {
        int total = 0;
        for (ManaCost cost : this) {
            total += cost.convertedManaCost();
        }
        return total;
    }

    @Override
    public Mana getMana() {
        Mana mana = new Mana();
        for (ManaCost cost : this) {
            mana.add(cost.getMana());
        }
        return mana;
    }

    @Override
    public List<Mana> getManaOptions() {
        List<Mana> manaVariants = new ArrayList<>();
        for (ManaCost cost : this) {
            manaVariants.addAll(cost.getManaOptions());
        }
        return manaVariants;
    }

    @Override
    public Mana getPayment() {
        Mana manaTotal = new Mana();
        for (ManaCost cost : this) {
            manaTotal.add(cost.getPayment());
        }
        return manaTotal;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (this.size() == 0 || noMana) {
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

    /**
     * bookmarks the current state and restores it if player doesn't pay the
     * mana cost
     *
     * @param ability
     * @param game
     * @param sourceId
     * @param payingPlayerId
     * @return true if the cost was paid
     */
    @Override
    public boolean payOrRollback(Ability ability, Game game, UUID sourceId, UUID payingPlayerId) {
        int bookmark = game.bookmarkState();
        if (pay(ability, game, sourceId, payingPlayerId, false)) {
            game.removeBookmark(bookmark);
            return true;
        }
        game.restoreState(bookmark, ability.getRule());
        return false;
    }

    @Override
    public ManaCosts<T> getUnpaid() {
        ManaCosts<T> unpaid = new ManaCostsImpl<>();
        for (T cost : this) {
            if (!(cost instanceof VariableManaCost) && !cost.isPaid()) {
                unpaid.add((T) cost.getUnpaid());
            }
        }
        return unpaid;
    }

    @Override
    public ManaCosts<T> getUnpaidVariableCosts() {
        ManaCosts<T> unpaid = new ManaCostsImpl<>();
        for (ManaCost cost : this) {
            if (cost instanceof VariableManaCost && !cost.isPaid()) {
                unpaid.add((T) cost.getUnpaid());
            }
        }
        return unpaid;
    }

    @Override
    public List<VariableCost> getVariableCosts() {
        List<VariableCost> variableCosts = new ArrayList<>();
        for (ManaCost cost : this) {
            if (cost instanceof VariableCost) {
                variableCosts.add((VariableCost) cost);
            }
        }
        return variableCosts;
    }

    @Override
    public int getX() {
        int amount = 0;
        List<VariableCost> variableCosts = getVariableCosts();
        if (!variableCosts.isEmpty()) {
            amount = variableCosts.get(0).getAmount();
        }
        return amount;
    }

    @Override
    public void setX(int x) {
        List<VariableCost> variableCosts = getVariableCosts();
        if (!variableCosts.isEmpty()) {
            variableCosts.get(0).setAmount(x);
        }
    }

    @Override
    public void setPayment(Mana mana) {
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool) {
        if (!pool.isAutoPayment() && pool.getUnlockedManaType() == null) {
            // if auto payment is inactive and no mana type was clicked manually - do nothing
            return;
        }

        //attempt to pay colored costs first
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof ColoredManaCost) {
                cost.assignPayment(game, ability, pool);
                if (pool.count() == 0) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof HybridManaCost) {
                cost.assignPayment(game, ability, pool);
                if (pool.count() == 0) {
                    return;
                }
            }
        }

        // Mono Hybrid mana costs
        // First try only to pay colored mana with the pool
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                if (((((MonoHybridManaCost) cost).containsColor(ColoredManaSymbol.W)) && pool.getWhite() > 0)
                        || ((((MonoHybridManaCost) cost).containsColor(ColoredManaSymbol.B)) && pool.getBlack() > 0)
                        || ((((MonoHybridManaCost) cost).containsColor(ColoredManaSymbol.R)) && pool.getRed() > 0)
                        || ((((MonoHybridManaCost) cost).containsColor(ColoredManaSymbol.G)) && pool.getGreen() > 0)
                        || ((((MonoHybridManaCost) cost).containsColor(ColoredManaSymbol.U)) && pool.getBlue() > 0)) {
                    cost.assignPayment(game, ability, pool);
                    if (pool.count() == 0) {
                        return;
                    }
                }
            }
        }
        // if colored didn't fit pay colorless with the mana
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                cost.assignPayment(game, ability, pool);
                if (pool.count() == 0) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof SnowManaCost) {
                cost.assignPayment(game, ability, pool);
                if (pool.count() == 0) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof GenericManaCost) {
                cost.assignPayment(game, ability, pool);
                if (pool.count() == 0) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof VariableManaCost) {
                cost.assignPayment(game, ability, pool);
            }
        }
        // stop using mana of the clicked mana type
        pool.lockManaType();
    }

    @Override
    public final void load(String mana) {
        this.clear();
        if (costs.containsKey(mana)) {
            ManaCosts<T> savedCosts = costs.get(mana);
            for (ManaCost cost : savedCosts) {
                this.add((T) cost.copy());
            }
        } else {
            if (mana == null || mana.length() == 0) {
                return;
            }
            String[] symbols = mana.split("^\\{|\\}\\{|\\}$");
            int modifierForX = 0;
            for (String symbol : symbols) {
                if (symbol.length() > 0) {
                    if (symbol.length() == 1 || isNumeric(symbol)) {
                        if (Character.isDigit(symbol.charAt(0))) {
                            this.add((T) new GenericManaCost(Integer.valueOf(symbol)));
                        } else {
                            if (!symbol.equals("X")) {
                                this.add((T) new ColoredManaCost(ColoredManaSymbol.lookup(symbol.charAt(0))));
                            } else {
                                // check X wasn't added before
                                if (modifierForX == 0) {
                                    // count X occurence
                                    for (String s : symbols) {
                                        if (s.equals("X")) {
                                            modifierForX++;
                                        }
                                    }
                                    this.add((T) new VariableManaCost(modifierForX));
                                }
                            }
                            //TODO: handle multiple {X} and/or {Y} symbols
                        }
                    } else {
                        if (symbol.equals("snow")) {
                            this.add((T) new SnowManaCost());
                        } else if (Character.isDigit(symbol.charAt(0))) {
                            this.add((T) new MonoHybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(2))));
                        } else if (symbol.contains("P")) {
                            this.add((T) new PhyrexianManaCost(ColoredManaSymbol.lookup(symbol.charAt(0))));
                        } else {
                            this.add((T) new HybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(0)), ColoredManaSymbol.lookup(symbol.charAt(2))));
                        }
                    }
                }
            }
            costs.put(mana, this.copy());
        }
    }

    private boolean isNumeric(String symbol) {
        try {
            Integer.parseInt(symbol);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<String> getSymbols() {
        List<String> symbols = new ArrayList<>();
        for (ManaCost cost : this) {
            symbols.add(cost.getText());
        }
        return symbols;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        if (text != null) {
            return text;
        }
        if (this.size() == 0) {
            return "";
        }

        StringBuilder sbText = new StringBuilder();
        for (ManaCost cost : this) {
            if (cost instanceof GenericManaCost) {
                sbText.append(cost.getText());
            }
        }
        for (ManaCost cost : this) {
            if (!(cost instanceof GenericManaCost)) {
                sbText.append(cost.getText());
            }
        }
        return sbText.toString();
    }

    @Override
    public ManaOptions getOptions() {
        ManaOptions options = new ManaOptions();
        for (ManaCost cost : this) {
            options.addMana(cost.getOptions());
        }
        return options;
    }

    @Override
    public boolean testPay(Mana testMana) {
        for (ManaCost cost : this) {
            if (cost.testPay(testMana)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        for (T cost : this) {
            if (!cost.canPay(ability, sourceId, controllerId, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPaid() {
        for (T cost : this) {
            if (!(cost instanceof VariableManaCost) && !cost.isPaid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearPaid() {
        for (T cost : this) {
            cost.clearPaid();
        }
    }

    @Override
    public void setPaid() {
        for (T cost : this) {
            cost.setPaid();
        }
    }

    @Override
    public Targets getTargets() {
        Targets targets = new Targets();
        for (T cost : this) {
            targets.addAll(cost.getTargets());
        }
        return targets;
    }

    @Override
    public ManaCosts<T> copy() {
        return new ManaCostsImpl<>(this);
    }

    @Override
    public Filter getSourceFilter() {
        for (T cost : this) {
            if (cost.getSourceFilter() != null) {
                return cost.getSourceFilter();
            }
        }
        return null;
    }

    @Override
    public void setSourceFilter(Filter filter) {
        for (T cost : this) {
            cost.setSourceFilter(filter);
        }
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        for (ManaCost manaCost : this) {
            if (manaCost.containsColor(coloredManaSymbol)) {
                return true;
            }
        }
        return false;
    }
}
