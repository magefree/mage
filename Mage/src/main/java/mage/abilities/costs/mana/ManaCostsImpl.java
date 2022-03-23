package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.*;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Targets;
import mage.util.CardUtil;
import mage.util.ManaUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class ManaCostsImpl<T extends ManaCost> extends ArrayList<T> implements ManaCosts<T> {

    protected final UUID id;
    protected String text = null;
    protected boolean phyrexian = false;
    private int phyrexianPaid = 0;

    private static final Map<String, ManaCosts> costsCache = new ConcurrentHashMap<>(); // must be thread safe, can't use nulls

    public ManaCostsImpl() {
        this.id = UUID.randomUUID();
    }

    public ManaCostsImpl(String mana) {
        this.id = UUID.randomUUID();
        load(mana);
    }

    public ManaCostsImpl(final ManaCostsImpl<T> costs) {
        this.id = costs.id;
        this.text = costs.text;
        for (T cost : costs) {
            this.add(cost.copy());
        }
        this.phyrexian = costs.phyrexian;
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
    public int manaValue() {
        int total = 0;
        for (ManaCost cost : this) {
            total += cost.manaValue();
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
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID playerId, boolean noMana, Cost costToPay) {
        if (this.isEmpty() || noMana) {
            setPaid();
            return true;
        }

        Player player = game.getPlayer(playerId);
        if (player == null) {
            return false;
        }

        handleLikePhyrexianManaCosts(player, ability, game); // e.g. K'rrik, Son of Yawgmoth

        if (!player.getManaPool().isForcedToPay()) {
            assignPayment(game, ability, player.getManaPool(), this);
        }
        game.getState().getSpecialActions().removeManaActions();
        while (player.canRespond() && !isPaid()) {
            ManaCost unpaid = this.getUnpaid();
            String promptText = ManaUtil.addSpecialManaPayAbilities(ability, game, unpaid);
            if (player.playMana(ability, unpaid, promptText, game)) {
                assignPayment(game, ability, player.getManaPool(), this);
            } else {
                return false;
            }
            game.getState().getSpecialActions().removeManaActions();
        }
        return isPaid();
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
    public boolean payOrRollback(Ability ability, Game game, Ability source, UUID payingPlayerId) {
        Player payingPlayer = game.getPlayer(payingPlayerId);
        if (payingPlayer != null) {
            int bookmark = game.bookmarkState();
            handlePhyrexianManaCosts(ability, payingPlayer, source, game);
            if (pay(ability, game, source, payingPlayerId, false, null)) {
                game.removeBookmark(bookmark);
                return true;
            }
            payingPlayer.restoreState(bookmark, ability.getRule(), game);
        }
        return false;
    }

    private void handlePhyrexianManaCosts(Ability abilityToPay, Player payingPlayer, Ability source, Game game) {
        if (this.isEmpty()) {
            return; // nothing to be done without any mana costs. prevents NRE from occurring here
        }
        Iterator<T> manaCostIterator = this.iterator();
        Costs<PayLifeCost> tempCosts = new CostsImpl<>();

        while (manaCostIterator.hasNext()) {
            ManaCost manaCost = manaCostIterator.next();
            if (!manaCost.isPhyrexian()) {
                continue;
            }
            PayLifeCost payLifeCost = new PayLifeCost(2);
            if (payLifeCost.canPay(abilityToPay, source, payingPlayer.getId(), game)
                    && payingPlayer.chooseUse(Outcome.LoseLife, "Pay 2 life instead of " + manaCost.getText().replace("/P", "") + '?', source, game)) {
                manaCostIterator.remove();
                tempCosts.add(payLifeCost);
                this.incrPhyrexianPaid();
            }
        }

        tempCosts.pay(source, game, source, payingPlayer.getId(), false, null);
    }


    private void handleLikePhyrexianManaCosts(Player player, Ability source, Game game) {
        if (this.isEmpty()) {
            return; // nothing to be done without any mana costs. prevents NRE from occurring here
        }
        FilterMana phyrexianColors = player.getPhyrexianColors();
        if (player.getPhyrexianColors() == null) {
            return;
        }
        Costs<PayLifeCost> tempCosts = new CostsImpl<>();

        Iterator<T> manaCostIterator = this.iterator();
        while (manaCostIterator.hasNext()) {
            ManaCost manaCost = manaCostIterator.next();
            Mana mana = manaCost.getMana();

            /* find which color mana is in the cost and set it in the temp Phyrexian cost */
            if ((!phyrexianColors.isWhite() || mana.getWhite() <= 0)
                    && (!phyrexianColors.isBlue() || mana.getBlue() <= 0)
                    && (!phyrexianColors.isBlack() || mana.getBlack() <= 0)
                    && (!phyrexianColors.isRed() || mana.getRed() <= 0)
                    && (!phyrexianColors.isGreen() || mana.getGreen() <= 0)) {
                continue;
            }
            PayLifeCost payLifeCost = new PayLifeCost(2);
            if (payLifeCost.canPay(source, source, player.getId(), game)
                    && player.chooseUse(Outcome.LoseLife, "Pay 2 life (using an active ability) instead of " + manaCost.getMana() + '?', source, game)) {
                manaCostIterator.remove();
                tempCosts.add(payLifeCost);
            }
        }
        tempCosts.pay(source, game, source, player.getId(), false, null);
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

    private boolean canPayColoredManaFromPool(ManaType needColor, ManaCost cost, ManaType canUseManaType, ManaPool pool) {
        if (canUseManaType == null || canUseManaType.equals(needColor)) {
            return cost.containsColor(CardUtil.manaTypeToColoredManaSymbol(needColor))
                    && (pool.getColoredAmount(needColor) > 0 || pool.ConditionalManaHasManaType(needColor));
        }
        return false;
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        // try to assign mana from pool to payment in priority order (color first)

        // auto-payment allows to use any mana type, if not then only unlocked can be used (mana type that were clicked in mana pool)
        ManaType canUseManaType;
        if (pool.isAutoPayment()) {
            canUseManaType = null; // can use any type
        } else {
            canUseManaType = pool.getUnlockedManaType();
            if (canUseManaType == null) {
                // auto payment is inactive and no mana type was clicked manually - do nothing
                return;
            }
        }

        ManaCosts referenceCosts = null;
        if (pool.isForcedToPay()) {
            referenceCosts = this.copy();
        }

        // colorless costs (not generic)
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof ColorlessManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // colored
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof ColoredManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // hybrid
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof HybridManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // monohybrid
        // try to pay colored part
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                if (canPayColoredManaFromPool(ManaType.WHITE, cost, canUseManaType, pool)
                        || canPayColoredManaFromPool(ManaType.BLACK, cost, canUseManaType, pool)
                        || canPayColoredManaFromPool(ManaType.RED, cost, canUseManaType, pool)
                        || canPayColoredManaFromPool(ManaType.GREEN, cost, canUseManaType, pool)
                        || canPayColoredManaFromPool(ManaType.BLUE, cost, canUseManaType, pool)) {
                    cost.assignPayment(game, ability, pool, costToPay);
                    if (pool.isEmpty() && pool.getConditionalMana().isEmpty()) {
                        return;
                    }
                }
            }
        }
        // try to pay generic part
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // snow
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof SnowManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // generic
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof GenericManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
                if (pool.isEmpty()) {
                    return;
                }
            }
        }

        // variable (generic)
        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof VariableManaCost) {
                cost.assignPayment(game, ability, pool, costToPay);
            }
        }

        // stop using mana of the clicked mana type
        pool.lockManaType();
        if (canUseManaType == null) {
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
    public final void load(String mana, boolean extractMonoHybridGenericValue) {
        this.clear();
        if (mana == null || mana.isEmpty()) {
            return;
        }
        if (!mana.startsWith("{") || !mana.endsWith("}")) {
            throw new IllegalArgumentException("mana costs should start and end with braces");
        }

        if (!extractMonoHybridGenericValue && costsCache.containsKey(mana)) {
            ManaCosts<ManaCost> savedCosts = costsCache.get(mana);
            for (ManaCost cost : savedCosts) {
                this.add(cost.copy());
            }
            return;
        }
        String[] symbols = mana.split("^\\{|}\\{|}$");
        int modifierForX = 0;
        for (String symbol : symbols) {
            if (symbol.isEmpty()) {
                continue;
            }
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
                        this.add(new VariableManaCost(VariableCostType.NORMAL, modifierForX));
                    } //TODO: handle multiple {X} and/or {Y} symbols
            } else if (Character.isDigit(symbol.charAt(0))) {
                MonoHybridManaCost cost;
                if (extractMonoHybridGenericValue) {
                    // for tests only, no usage in real game
                    cost = new MonoHybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(2)), Integer.parseInt(symbol.substring(0, 1)));
                } else {
                    cost = new MonoHybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(2)));
                }
                this.add(cost);
            } else {
                boolean phyrexian = symbol.contains("/P");
                String without = symbol.replace("/P", "");
                ManaCost cost;
                if (without.length() == 1) {
                    cost = new ColoredManaCost(ColoredManaSymbol.lookup(without.charAt(0)));
                } else {
                    cost = new HybridManaCost(ColoredManaSymbol.lookup(without.charAt(0)), ColoredManaSymbol.lookup(without.charAt(2)));
                }
                cost.setPhyrexian(phyrexian);
                this.add(cost);
            }
        }
        if (!extractMonoHybridGenericValue) {
            costsCache.put(mana, this.copy());
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
    public UUID getId() {
        return this.id;
    }

    @Override
    public ManaCostsImpl<T> setText(String text) {
        this.text = text;
        return this;
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
            sbText.append(cost.getText());
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
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        for (T cost : this) {
            if (!cost.canPay(ability, source, controllerId, game)) {
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
    public boolean isPhyrexian() {
        return phyrexian;
    }

    @Override
    public void setPhyrexian(boolean phyrexian) {
        this.phyrexian = phyrexian;
        for (T cost : this) {
            cost.setPhyrexian(phyrexian);
        }
    }

    @Override
    public void incrPhyrexianPaid() {
        this.phyrexianPaid++;
    }

    @Override
    public int getPhyrexianPaid() {
        return phyrexianPaid;
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
