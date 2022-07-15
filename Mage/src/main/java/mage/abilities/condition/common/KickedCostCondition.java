package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.Game;

import java.util.Objects;

/**
 * Describes condition when specific KickerCosts were paid.
 *
 * @author LevelX2
 */
public class KickedCostCondition implements Condition {

    protected String kickerCostText;

    public KickedCostCondition(String kickerCostText) {
        this.kickerCostText = kickerCostText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof KickerAbility) {
                    return ((KickerAbility) ability).isKicked(game, source, kickerCostText);
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kickerCostText);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KickedCostCondition other = (KickedCostCondition) obj;
        return Objects.equals(this.kickerCostText, other.kickerCostText);
    }
}
