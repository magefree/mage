package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

public class BoastAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    public BoastAbility(Effect effect, ManaCosts cost) {
        super(Zone.BATTLEFIELD, effect, cost);
        this.maxActivationsPerTurn = 1;
        this.addWatcher(new AttackedThisTurnWatcher());
        this.condition = BoastAbilityCondition.instance;
    }

    public BoastAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
        this.maxActivationsPerTurn = 1;
        this.addWatcher(new AttackedThisTurnWatcher());
        this.condition = BoastAbilityCondition.instance;
    }

    public BoastAbility(Effect effect, Costs<Cost> costs) {
        super(Zone.BATTLEFIELD, effect, costs);
        this.maxActivationsPerTurn = 1;
        this.addWatcher(new AttackedThisTurnWatcher());
        this.condition = BoastAbilityCondition.instance;
    }

    public BoastAbility(BoastAbility ability) {
        super(ability);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ActivatedAbilityImpl copy() {
        return new BoastAbility(this);
    }

    @Override
    public String getRule() {
        return "Boast -- " + super.getRule() + " (Activate this ability only if this creature attacked this turn and only once each turn.)";
    }
}

enum BoastAbilityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield(source.getSourceId());
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (creature == null || watcher == null) {
            return false;
        }
        return watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(creature, game));
    }
}
