package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;
import org.checkerframework.checker.units.qual.C;

import java.util.Objects;
import java.util.UUID;

public abstract class CostImpl implements Cost {

    protected UUID id;
    protected String text;
    protected boolean paid;
    protected Targets targets;

    public CostImpl() {
        id = UUID.randomUUID();
        paid = false;
        targets = new Targets();
    }

    public CostImpl(final CostImpl cost) {
        this.id = cost.id;
        this.text = cost.text;
        this.paid = cost.paid;
        this.targets = cost.targets.copy();
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
    public boolean equivalent(Object obj, Game game) {
        if (!this.equalsInner(obj)) {
            return false;
        }
        CostImpl that = (CostImpl) obj;
        // this.id is not checked since that WILL be different for different costs
        if (!(this.targets == null ^ that.targets == null)
                || this.targets == null) {
            return false;
        }

        return this.targets.equivalent(that.targets, game);
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.equalsInner(obj)) {
            return false;
        }
        CostImpl that = (CostImpl) obj;

        // TODO: If id is compared it will always return false since each instance has a unique id
//        if (!Objects.equals(this.id, that.id)) {
//            return false;
//        }

        return Objects.deepEquals(this.targets, that.targets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, paid, targets);
    }

    private boolean equalsInner(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        CostImpl that = (CostImpl) obj;

        return this.paid == that.paid && Objects.equals(this.text, that.text);
    }
}
