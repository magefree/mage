package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.UUID;

public class SpellsCostReductionAttachedEffect extends SpellsCostReductionEffect {

    public SpellsCostReductionAttachedEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        this(filter, manaCostsToReduce, StaticValue.get(1));
    }

    public SpellsCostReductionAttachedEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce, DynamicValue amount) {
        super(filter, manaCostsToReduce, amount);
        createStaticText("you cast that target enchanted creature");
    }

    public SpellsCostReductionAttachedEffect(FilterCard filter, int amount) {
        this(filter, StaticValue.get(amount));
    }

    public SpellsCostReductionAttachedEffect(FilterCard filter, DynamicValue amount) {
        super(filter, amount);
        createStaticText("you cast that target enchanted creature");
    }

    protected SpellsCostReductionAttachedEffect(final SpellsCostReductionAttachedEffect effect) {
        super(effect);
    }

    @Override
    protected boolean checkSourceCondition(Ability abilityToModify, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        return sourcePermanent != null && sourcePermanent.getAttachedTo() != null;
    }

    @Override
    protected boolean checkSpellCondition(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.getControllerId().equals(source.getControllerId())) {
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
    public SpellsCostReductionAttachedEffect copy() {
        return new SpellsCostReductionAttachedEffect(this);
    }
}