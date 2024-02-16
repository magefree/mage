package mage.abilities.costs;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class UseAttachedCost extends CostImpl {

    protected MageObjectReference mageObjectReference;
    protected String name = "{this}";

    protected UseAttachedCost() {
        super();
    }

    protected UseAttachedCost(final UseAttachedCost cost) {
        super(cost);
        this.mageObjectReference = cost.mageObjectReference;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        if (mageObjectReference == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null
                && permanent
                .getAttachments()
                .stream()
                .anyMatch(uuid -> mageObjectReference.refersTo(uuid, game));
    }

    public UseAttachedCost setMageObjectReference(Ability source, Game game) {
        this.mageObjectReference = new MageObjectReference(source.getSourceId(), game);
        MageObject object = game.getObject(source);
        if (object != null) {
            this.name = object.getName();
        }
        return this;
    }

    @Override
    public abstract UseAttachedCost copy();
}
