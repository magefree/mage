package mage.abilities.common;

import mage.abilities.effects.common.InfoEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class AttachableToRestrictedAbility extends SimpleStaticAbility {

    private final Target attachable;

    public AttachableToRestrictedAbility(Target target) {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} can be attached only to a " + target.getTargetName()));
        this.attachable = target.copy();

        // verify check: make sure filter don't have controller predicate cause it used in code without controller info
        List<Predicate> list = new ArrayList<>();
        Predicates.collectAllComponents(target.getFilter().getPredicates(), target.getFilter().getExtraPredicates(), list);
        if (list.stream().anyMatch(TargetController.ControllerPredicate.class::isInstance)) {
            throw new IllegalArgumentException("Wrong code usage: attachable restriction filter must not contain controller predicate");
        }
    }

    private AttachableToRestrictedAbility(AttachableToRestrictedAbility ability) {
        super(ability);
        this.attachable = ability.attachable.copy();
    }

    public boolean canEquip(UUID toEquip, Game game) {
        return attachable.canTarget(toEquip, null, game);
    }

    @Override
    public AttachableToRestrictedAbility copy() {
        return new AttachableToRestrictedAbility(this);
    }
}
