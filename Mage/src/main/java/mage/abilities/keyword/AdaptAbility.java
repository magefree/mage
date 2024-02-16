

package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class AdaptAbility extends ActivatedAbilityImpl {

    public AdaptAbility(int adaptNumber, String manaCost) {
        super(Zone.BATTLEFIELD, new AdaptEffect(adaptNumber), new ManaCostsImpl<>(manaCost));
    }

    private AdaptAbility(final AdaptAbility ability) {
        super(ability);
    }

    @Override
    public AdaptAbility copy() {
        return new AdaptAbility(this);
    }
}
