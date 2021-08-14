package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.UseAttachedCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.Targets;
import mage.target.targetpointer.FixedTarget;

import java.util.Collections;

/**
 * @author TheElk801
 */
public class GainAbilityWithAttachmentEffect extends ContinuousEffectImpl {

    private final Effects effects = new Effects();
    private final Targets targets = new Targets();
    private final Costs<Cost> costs = new CostsImpl<>();
    protected final UseAttachedCost useAttachedCost;

    public GainAbilityWithAttachmentEffect(String rule, Effect effect, Target target, UseAttachedCost attachedCost, Cost... costs) {
        this(rule, new Effects(effect), new Targets(target), attachedCost, costs);
    }

    public GainAbilityWithAttachmentEffect(String rule, Effects effects, Targets targets, UseAttachedCost attachedCost, Cost... costs) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = rule;
        this.effects.addAll(effects);
        this.targets.addAll(targets);
        Collections.addAll(this.costs, costs);
        this.useAttachedCost = attachedCost;
        this.generateGainAbilityDependencies(makeAbility(null, null), null);
    }

    protected GainAbilityWithAttachmentEffect(final GainAbilityWithAttachmentEffect effect) {
        super(effect);
        this.effects.addAll(effect.effects);
        this.targets.addAll(effect.targets);
        this.costs.addAll(effect.costs);
        this.useAttachedCost = effect.useAttachedCost.copy();
    }

    @Override
    public GainAbilityWithAttachmentEffect copy() {
        return new GainAbilityWithAttachmentEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (affectedObjectsSet) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
            }
        }
        if (permanent == null) {
            return true;
        }
        Ability ability = makeAbility(game, source);
        ability.getEffects().setValue("attachedPermanent", game.getPermanent(source.getSourceId()));
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }

    protected Ability makeAbility(Game game, Ability source) {
        Ability ability = new SimpleActivatedAbility(null, null);
        for (Effect effect : effects) {
            if (effect == null) {
                continue;
            }
            ability.addEffect(effect.copy());
        }
        for (Target target : targets) {
            if (target == null) {
                continue;
            }
            ability.addTarget(target);
        }
        for (Cost cost : this.costs) {
            if (cost == null) {
                continue;
            }
            ability.addCost(cost.copy());
        }
        if (source != null && game != null) {
            ability.addCost(useAttachedCost.copy().setMageObjectReference(source, game));
        }
        return ability;
    }
}
