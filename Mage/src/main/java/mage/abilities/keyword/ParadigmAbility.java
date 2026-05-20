package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

/**
 * TODO: Implement this
 *
 * @author TheElk801
 */
public class ParadigmAbility extends SimpleStaticAbility {

    public ParadigmAbility() {
        super(Zone.STACK, null);
    }

    protected ParadigmAbility(final ParadigmAbility ability) {
        super(ability);
    }

    @Override
    public ParadigmAbility copy() {
        return new ParadigmAbility(this);
    }

    @Override
    public String getRule() {
        return "Paradigm <i> (Then exile this spell. After you first resolve a spell with this name, " +
                "you may cast a copy of it from exile without paying its mana cost " +
                "at the beginning of each of your first main phases.)</i>";
    }
}
