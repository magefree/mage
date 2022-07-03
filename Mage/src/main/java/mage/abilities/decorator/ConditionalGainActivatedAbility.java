
package mage.abilities.decorator;

import java.util.UUID;
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

/**
 * The card / permanent has the ability only, if the condition is true.
 *
 * @author LevelX
 */
public class ConditionalGainActivatedAbility extends ActivatedAbilityImpl {

    private String staticText = "";

    private static final Effects emptyEffects = new Effects();

    public ConditionalGainActivatedAbility(Zone zone, Effect effect, ManaCosts cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.staticText = rule;
    }

    public ConditionalGainActivatedAbility(Zone zone, Effect effect, Costs costs, Condition condition, String rule) {
        super(zone, effect, costs);
        this.condition = condition;
        this.staticText = rule;
    }

    public ConditionalGainActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.staticText = rule;
    }

    public ConditionalGainActivatedAbility(ConditionalGainActivatedAbility ability) {
        super(ability);
        this.staticText = ability.staticText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (!condition.apply(game, this)) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public ConditionalGainActivatedAbility copy() {
        return new ConditionalGainActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
