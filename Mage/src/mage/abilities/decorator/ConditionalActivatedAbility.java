/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.decorator;

import java.util.UUID;

import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.EffectType;
import mage.game.Game;

/**
 *
 * @author LevelX
 */
public class ConditionalActivatedAbility extends ActivatedAbilityImpl<ConditionalActivatedAbility> {

    private Condition condition;
    private String ruleText = "";

    private static final Effects emptyEffects = new Effects();

    public ConditionalActivatedAbility(Zone zone, Effect effect, ManaCosts cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.ruleText = rule;
    }

    public ConditionalActivatedAbility(Zone zone, Effect effect, Costs costs, Condition condition, String rule) {
        super(zone, effect, costs);
        this.condition = condition;
        this.ruleText = rule;
    }

    public ConditionalActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.ruleText = rule;
    }

    public ConditionalActivatedAbility(final ConditionalActivatedAbility ability) {
        super(ability);
        this.condition = ability.condition;
        this.ruleText = ability.ruleText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!condition.apply(game, this)) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public ConditionalActivatedAbility copy() {
        return new ConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
