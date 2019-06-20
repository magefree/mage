package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Targets;
import mage.util.ManaUtil;

import java.util.*;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class ManaCostsImpl<T extends ManaCost> extends ArrayList<T> implements ManaCosts<T> {

    protected final UUID id;
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
    public Mana getUsedManaToPay() {
        Mana manaTotal = new Mana();
        for (ManaCost cost : this) {
            manaTotal.add(cost.getUsedManaToPay());
        }
        return manaTotal;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        return pay(ability, game, sourceId, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (this.isEmpty() || noMana) {
            setPaid();
            return true;
        }

        Player player = game.getPlayer(controllerId);
        if (!player.getManaPool().isForcedToPay()) {
            assignPayment(game, ability, player.getManaPool(), this);
        }
        game.getState().getSpecialActions().removeManaActions();
        while (!isPaid()) {
            ManaCost unpaid = this.getUnpaid();
            String promptText = ManaUtil.addSpecialManaPayAbilities(ability, game, unpaid);
            if (player.playMana(ability, unpaid, promptText, game)) {
                assignPayment(game, ability, player.getManaPool(), this);
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
        handlePhyrexianManaCosts(payingPlayerId, ability, game);
        if (pay(ability, game, sourceId, payingPlayerId, false, null)) {
            game.removeBookmark(bookmark);
            return true;
        }
        game.restoreState(bookmark, ability.getRule());
        return false;
    }

    private void handlePhyrexianManaCosts(UUID payingPlayerId, Ability source, Game game) {
        Player player = game.getPlayer(payingPlayerId);
        if (this == null || player == null) {
            return; // nothing to be done without any mana costs. prevents NRE from occurring here
        }
        Iterator<T> manaCostIterator = this.iterator();
        Costs<PayLifeCost> tempCosts = new CostsImpl<>();

        while (manaCostIterator.hasNext()) {
            ManaCost manaCost = manaCostIterator.next();
            if (manaCost instanceof PhyrexianManaCost) {
                PhyrexianManaCost phyrexianManaCost = (PhyrexianManaCost) manaCost;
                PayLifeCost payLifeCost = new PayLifeCost(2);
                if (payLifeCost.canPay(source, source.getSourceId(), player.getId(), game)
                        && player.chooseUse(Outcome.LoseLife, "Pay 2 life instead of " + phyrexianManaCost.getBaseText() + '?', source, game)) {
                    manaCostIterator.remove();
                    tempCosts.add(payLifeCost);
                }
            }
        }

        tempCosts.pay(source, game, source.getSourceId(), player.getId(), false, null);
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
    public boolean containsX() {
        return !getVariableCosts().isEmpty();
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
    public void setX(int xValue, int xPay) {
        List<VariableCost> variableCosts = getVariableCosts();
        if (!variableCosts.isEmpty()) {
            variableCosts.get(0).setAmount(xValue, xPay, false);
        }
    }

    @Override
    public void setPayment(Mana mana) {
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        boolean wasUnlockedManaType = (pool.getUnlockedManaType() != null);
        if (!pool.isAutoPayment() && !wasUnlockedManaType) {
            // if auto payment is inactive and no mana type was clicked manually - do nothing
            return;
        }
        ManaCosts referenceCosts = null;
        if (pool.isForcedToPay()) {
            referenceCosts = this.copy();
        }
        // attempt to pay colorless costs (not generic) mana costs first
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof ColorlessManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }
        //attempt to pay colored costs first
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof ColoredManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof HybridManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // Mono Hybrid mana costs
        // First try only to pay colored mana or conditional colored mana with the pool
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                if (((cost.containsColor(ColoredManaSymbol.W)) && (pool.getWhite() > 0 || pool.ConditionalManaHasManaType(ManaType.WHITE)))
                        || ((cost.containsColor(ColoredManaSymbol.B)) && (pool.getBlack() > 0 || pool.ConditionalManaHasManaType(ManaType.BLACK)))
                        || ((cost.containsColor(ColoredManaSymbol.R)) && (pool.getRed() > 0 || pool.ConditionalManaHasManaType(ManaType.RED)))
                        || ((cost.containsColor(ColoredManaSymbol.G)) && (pool.getGreen() > 0 || pool.ConditionalManaHasManaType(ManaType.GREEN)))
                        || ((cost.containsColor(ColoredManaSymbol.U)) && (pool.getBlue() > 0) || pool.ConditionalManaHasManaType(ManaType.BLUE))) {
                    cost.assignPayment(game, ability, pool, costToPay);
                    if (pool.isEmpty() && pool.getConditionalMana().isEmpty()) {
                        return;
                    }
                }
            }
        }
        // if colored didn't fit pay colorless with the mana
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof SnowManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof GenericManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof VariableManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
            }
        }
        // stop using mana of the clicked mana type
        pool.lockManaType();
        if (!wasUnlockedManaType) {
            handleForcedToPayOnlyForCurrentPayment(game, pool, referenceCosts);
        }
    }

    private void handleForcedToPayOnlyForCurrentPayment(Game game, ManaPool pool, ManaCosts referenceCosts) {
        // for Word of Command
        if (pool.isForcedToPay()) {
            if (referenceCosts != null && this.getText().equals(referenceCosts.getText())) {
                UUID playerId = pool.getPlayerId();
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    game.undo(playerId);
                    this.clearPaid();

                    // TODO: checks Word of Command with Unbound Flourishing's X multiplier
                    // TODO: checks Word of Command with {X}{X} cards
                    int xValue = referenceCosts.getX();
                    this.setX(xValue, xValue);

                    player.getManaPool().restoreMana(pool.getPoolBookmark());
                    game.bookmarkState();
                }
            }
        }
    }

    public void forceManaRollback(Game game, ManaPool pool) {
        // for Word of Command
        handleForcedToPayOnlyForCurrentPayment(game, pool, this);
    }

    @Override
    public final void load(String mana) {
        this.clear();
        if (costs.containsKey(mana)) {
            ManaCosts<ManaCost> savedCosts = costs.get(mana);
            for (ManaCost cost : savedCosts) {
                this.add(cost.copy());
            }
        } else {
            if (mana == null || mana.isEmpty()) {
                return;
            }
            String[] symbols = mana.split("^\\{|}\\{|}$");
            int modifierForX = 0;
            for (String symbol : symbols) {
                if (!symbol.isEmpty()) {
                    if (symbol.length() == 1 || isNumeric(symbol)) {
                        if (Character.isDigit(symbol.charAt(0))) {
                            this.add(new GenericManaCost(Integer.valueOf(symbol)));
                        } else if (symbol.equals("S")) {
                            this.add(new SnowManaCost());
                        } else if (symbol.equals("C")) {
                            this.add(new ColorlessManaCost(1));
                        } else if (!symbol.equals("X")) {
                            this.add(new ColoredManaCost(ColoredManaSymbol.lookup(symbol.charAt(0))));
                        } else // check X wasn't added before
                            if (modifierForX == 0) {
                                // count X occurence
                                for (String s : symbols) {
                                    if (s.equals("X")) {
                                        modifierForX++;
                                    }
                                }
                                this.add(new VariableManaCost(modifierForX));
                            } //TODO: handle multiple {X} and/or {Y} symbols
                    } else if (Character.isDigit(symbol.charAt(0))) {
                        this.add(new MonoHybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(2))));
                    } else if (symbol.contains("P")) {
                        this.add(new PhyrexianManaCost(ColoredManaSymbol.lookup(symbol.charAt(0))));
                    } else {
                        this.add(new HybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(0)), ColoredManaSymbol.lookup(symbol.charAt(2))));
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
        if (this.isEmpty()) {
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
