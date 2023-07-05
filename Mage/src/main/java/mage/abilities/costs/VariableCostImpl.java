package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.mana.ManaAbility;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;

import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class VariableCostImpl implements Cost, VariableCost {

    protected UUID id;
    protected VariableCostType costType;
    protected String text;
    protected boolean paid;
    protected Targets targets;
    protected int amountPaid;
    protected String xText;
    protected String actionText;

    public VariableCostImpl(VariableCostType costType, String actionText) {
        this(costType, "X", actionText);
    }

    /**
     * @param xText      string for the defined value
     * @param actionText what happens with the value (e.g. "to tap", "to exile
     *                   from your graveyard")
     */
    public VariableCostImpl(VariableCostType costType, String xText, String actionText) {
        this.id = UUID.randomUUID();
        this.costType = costType;
        this.paid = false;
        this.targets = new Targets();
        this.amountPaid = 0;
        this.xText = xText;
        this.actionText = actionText;
    }

    public VariableCostImpl(final VariableCostImpl cost) {
        this.id = cost.id;
        this.costType = cost.costType;
        this.text = cost.text;
        this.paid = cost.paid;
        this.targets = cost.targets.copy();
        this.xText = cost.xText;
        this.actionText = cost.actionText;
        this.amountPaid = cost.amountPaid;
    }

    @Override
    public VariableCostImpl setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getActionText() {
        return actionText;
    }

    public void addTarget(Target target) {
        if (target != null) {
            this.targets.add(target);
        }
    }

    @Override
    public Targets getTargets() {
        return this.targets;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void clearPaid() {
        paid = false;
        targets.clearChosen();
        amountPaid = 0;
    }

    @Override
    public void setPaid() {
        paid = true;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
        /* not used */
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return true;
        /* not used */
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setAmount(int xValue, int xPay, boolean isPayed) {
        amountPaid = xPay;
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return 0;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        int xValue = 0;
        Player controller = game.getPlayer(source.getControllerId());
        StackObject stackObject = game.getStack().getStackObject(source.getId());
        if (controller != null
                && (source instanceof ManaAbility
                || stackObject != null)) {
            xValue = controller.announceXCost(getMinValue(source, game), getMaxValue(source, game),
                    "Announce the number of " + actionText, game, source, this);
        }
        return xValue;
    }

    @Override
    public VariableCostType getCostType() {
        return this.costType;
    }

    @Override
    public void setCostType(VariableCostType costType) {
        this.costType = costType;
    }
}
