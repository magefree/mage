

package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;

/**
 * FAQ 2013/01/11
 * <p>
 * Bloodrush is an ability word that appears in italics at the beginning of abilities
 * of creature cards you can discard to give an attacking creature a bonus.
 * (An ability word has no rules meaning.)
 *
 * @author LevelX2
 */


public class BloodrushAbility extends ActivatedAbilityImpl {

    public BloodrushAbility(String manaString, Effect effect) {
        super(Zone.HAND, effect, new ManaCostsImpl<>(manaString));
        this.addCost(new DiscardSourceCost());
        this.addTarget(new TargetAttackingCreature());
        this.setAbilityWord(AbilityWord.BLOODRUSH);
    }

    public BloodrushAbility(final BloodrushAbility ability) {
        super(ability);
    }

    @Override
    public BloodrushAbility copy() {
        return new BloodrushAbility(this);
    }
}

