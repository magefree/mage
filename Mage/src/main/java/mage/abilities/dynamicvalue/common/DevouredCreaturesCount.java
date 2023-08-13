
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.keyword.DevourAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class DevouredCreaturesCount implements DynamicValue {

    int multiplier;

    public DevouredCreaturesCount() {
        this(1);
    }

    public DevouredCreaturesCount(int multiplier) {
        super();
        this.multiplier = multiplier;
    }

    protected DevouredCreaturesCount(final DevouredCreaturesCount dynamicValue) {
        super();
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent != null) {
            for (Ability ability : sourcePermanent.getAbilities()) {
                if (ability instanceof DevourAbility) {
                    for (Effect abilityEffect : ability.getEffects()) {
                        if (abilityEffect instanceof DevourEffect) {
                            DevourEffect devourEffect = (DevourEffect) abilityEffect;
                            return devourEffect.getDevouredCreaturesAmount(game, sourcePermanent.getId()) * multiplier;
                        }
                    }

                }
            }
        }
        return 0;
    }

    @Override
    public DevouredCreaturesCount copy() {
        return new DevouredCreaturesCount(this);
    }

    @Override
    public String toString() {
        return Integer.toString(multiplier);
    }

    @Override
    public String getMessage() {
        return "creature it devoured";
    }
}
