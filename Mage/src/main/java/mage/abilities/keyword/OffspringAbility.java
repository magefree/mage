package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Zone;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class OffspringAbility extends StaticAbility {

    public OffspringAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public OffspringAbility(Cost cost) {
        super(Zone.ALL, null);
    }

    private OffspringAbility(final OffspringAbility ability) {
        super(ability);
    }

    @Override
    public OffspringAbility copy() {
        return new OffspringAbility(this);
    }

    @Override
    public String getRule() {
        return "Offspring";
    }
}
