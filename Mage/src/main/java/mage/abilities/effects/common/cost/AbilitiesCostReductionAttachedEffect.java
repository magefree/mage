package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.UUID;

public class AbilitiesCostReductionAttachedEffect extends AbilitiesCostReductionEffect {

    public AbilitiesCostReductionAttachedEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount) {
        super(activatedAbility, activatedAbilityName, amount);
    }

    protected AbilitiesCostReductionAttachedEffect(final AbilitiesCostReductionAttachedEffect effect) {
        super(effect);
    }

    @Override
    protected void createStaticText(String activatedAbilityName) {
        staticText = activatedAbilityName + " you activate that target enchanted creature cost {" + amount + "} less to activate";
    }

    @Override
    protected boolean checkAbilityCondition(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }

        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }

        UUID attachedId = sourcePermanent.getAttachedTo();
        if (game.inCheckPlayableState()) {
            return !abilityToModify.getTargets().isEmpty() &&
                    abilityToModify.getTargets().get(0).canTarget(attachedId, abilityToModify, game);
        } else {
            Permanent attached = game.getPermanent(attachedId);
            return attached != null && abilityToModify.getTargets()
                    .stream()
                    .map(target -> target.getTargets())
                    .flatMap(Collection::stream)
                    .anyMatch(targetId -> targetId.equals(attachedId));
        }
    }

    @Override
    public AbilitiesCostReductionAttachedEffect copy() {
        return new AbilitiesCostReductionAttachedEffect(this);
    }
}