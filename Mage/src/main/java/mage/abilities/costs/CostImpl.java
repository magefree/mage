package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

import java.util.UUID;

public abstract class CostImpl implements Cost {

    protected UUID id;
    protected String text;
    protected boolean paid;
    private Targets targets;

    public CostImpl() {
        id = UUID.randomUUID();
        paid = false;
        targets = null; // rare usage, must be null by default for performance optimization
    }

    protected CostImpl(final CostImpl cost) {
        this.id = cost.id;
        this.text = cost.text;
        this.paid = cost.paid;
        this.targets = cost.targets == null ? null : cost.targets.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Cost setText(String text) {
        this.text = text;
        return this;
    }

    private void prepareTargets() {
        if (this.targets == null) {
            this.targets = new Targets();
        }
    }

    public void addTarget(Target target) {
        if (target == null) {
            throw new IllegalArgumentException("Wrong code usage: can't add nullable target to the cost");
        }
        prepareTargets();
        this.targets.add(target);
    }

    @Override
    public Targets getTargets() {
        prepareTargets();
        return this.targets;
    }

    @Override
    public boolean isPaid() {
        return paid;
    }

    @Override
    public void clearPaid() {
        paid = false;
        prepareTargets();
        targets.clearChosen();
    }

    @Override
    public void setPaid() {
        paid = true;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
