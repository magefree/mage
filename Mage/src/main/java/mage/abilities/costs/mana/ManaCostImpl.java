package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.mana.ManaOptions;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ManaCostImpl extends CostImpl implements ManaCost {

    protected Mana payment;
    protected Mana usedManaToPay;
    protected Mana cost;
    protected ManaOptions options;
    protected Filter sourceFilter;

    public ManaCostImpl() {
        payment = new Mana();
        usedManaToPay = new Mana();
        options = new ManaOptions();
    }

    public ManaCostImpl(final ManaCostImpl manaCost) {
        super(manaCost);
        this.payment = manaCost.payment.copy();
        this.usedManaToPay = manaCost.usedManaToPay.copy();
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
    public Mana getUsedManaToPay() {
        return usedManaToPay;
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
        usedManaToPay.clear();
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

    protected boolean assignColored(Ability ability, Game game, ManaPool pool, ColoredManaSymbol mana, Cost costToPay) {
        // first check special mana
        switch (mana) {
            case W:
                if (pool.pay(ManaType.WHITE, ability, sourceFilter, game, costToPay, usedManaToPay)) {
                    this.payment.increaseWhite();
                    return true;
                }
                break;
            case U:
                if (pool.pay(ManaType.BLUE, ability, sourceFilter, game, costToPay, usedManaToPay)) {
                    this.payment.increaseBlue();
                    return true;
                }
                break;
            case B:
                if (pool.pay(ManaType.BLACK, ability, sourceFilter, game, costToPay, usedManaToPay)) {
                    this.payment.increaseBlack();
                    return true;
                }
                break;
            case R:
                if (pool.pay(ManaType.RED, ability, sourceFilter, game, costToPay, usedManaToPay)) {
                    this.payment.increaseRed();
                    return true;
                }
                break;
            case G:
                if (pool.pay(ManaType.GREEN, ability, sourceFilter, game, costToPay, usedManaToPay)) {
                    this.payment.increaseGreen();
                    return true;
                }
                break;
        }
        return false;
    }

    protected void assignColorless(Ability ability, Game game, ManaPool pool, int mana, Cost costToPay) {
        int conditionalCount = pool.getConditionalCount(ability, game, null, costToPay);
        while (mana > payment.count() && (pool.count() > 0 || conditionalCount > 0)) {
            if (pool.pay(ManaType.COLORLESS, ability, sourceFilter, game, costToPay, usedManaToPay)) {
                this.payment.increaseColorless();
            }
            break;
        }
    }

    protected boolean assignGeneric(Ability ability, Game game, ManaPool pool, int mana, FilterMana filterMana, Cost costToPay) {
        int conditionalCount = pool.getConditionalCount(ability, game, filterMana, costToPay);
        while (mana > payment.count() && (pool.count() > 0 || conditionalCount > 0)) {
            // try to use different mana to pay (conditional mana will used in pool.pay)
            // filterMana can be null, uses for spells like "spend only black mana on X"

            // {C}
            if ((filterMana == null || filterMana.isColorless()) && pool.pay(
                    ManaType.COLORLESS, ability, sourceFilter, game, costToPay, usedManaToPay
            )) {
                this.payment.increaseColorless();
                continue;
            }

            // {B}
            if ((filterMana == null || filterMana.isBlack()) && pool.pay(
                    ManaType.BLACK, ability, sourceFilter, game, costToPay, usedManaToPay
            )) {
                this.payment.increaseBlack();
                continue;
            }

            // {U}
            if ((filterMana == null || filterMana.isBlue()) && pool.pay(
                    ManaType.BLUE, ability, sourceFilter, game, costToPay, usedManaToPay
            )) {
                this.payment.increaseBlue();
                continue;
            }

            // {W}
            if ((filterMana == null || filterMana.isWhite()) && pool.pay(
                    ManaType.WHITE, ability, sourceFilter, game, costToPay, usedManaToPay
            )) {
                this.payment.increaseWhite();
                continue;
            }

            // {G}
            if ((filterMana == null || filterMana.isGreen()) && pool.pay(
                    ManaType.GREEN, ability, sourceFilter, game, costToPay, usedManaToPay
            )) {
                this.payment.increaseGreen();
                continue;
            }

            // {R}
            if ((filterMana == null || filterMana.isRed()) && pool.pay(
                    ManaType.RED, ability, sourceFilter, game, costToPay, usedManaToPay
            )) {
                this.payment.increaseRed();
                continue;
            }

            // nothing to pay
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
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (noMana) {
            setPaid();
            return true;
        }
        Player player = game.getPlayer(controllerId);
        if (!player.getManaPool().isForcedToPay()) {
            assignPayment(game, ability, player.getManaPool(), costToPay != null ? costToPay : this);
        }
        game.getState().getSpecialActions().removeManaActions();
        while (player.canRespond() && !isPaid()) {
            ManaCost unpaid = this.getUnpaid();
            String promptText = ManaUtil.addSpecialManaPayAbilities(ability, game, unpaid);
            if (player.playMana(ability, unpaid, promptText, game)) {
                assignPayment(game, ability, player.getManaPool(), costToPay != null ? costToPay : this);
            } else {
                return false;
            }
            game.getState().getSpecialActions().removeManaActions();
        }
        return isPaid();
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
                this.options.add(Mana.BlackMana(1));
                break;
            case U:
                this.options.add(Mana.BlueMana(1));
                break;
            case W:
                this.options.add(Mana.WhiteMana(1));
                break;
            case R:
                this.options.add(Mana.RedMana(1));
                break;
            case G:
                this.options.add(Mana.GreenMana(1));
                break;
        }
    }

    @Override
    public String toString() {
        return getText();
    }
}
