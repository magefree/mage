
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.keyword.DevourAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Describes condition about how many creatures were devoured
 *
 * @author LevelX2
 */
public class DevouredCreaturesCondition extends IntCompareCondition {

    public DevouredCreaturesCondition(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int devouredCreatures = 0;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            for (Ability ability : sourcePermanent.getAbilities()) {
                if (ability instanceof DevourAbility) {
                    for (Effect effect: ability.getEffects()) {
                        if (effect instanceof DevourEffect) {
                            DevourEffect devourEffect = (DevourEffect) effect;
                            devouredCreatures = devourEffect.getDevouredCreaturesAmount(game, sourcePermanent.getId());
                        }
                    }
                }
            }
        }
        return devouredCreatures;
    }
}
