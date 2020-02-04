
package mage.abilities.costs;

import java.util.UUID;
import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

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
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        return pay(ability, game, sourceId, controllerId, noMana, this);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
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

}
