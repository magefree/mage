package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class ReconfigureAbility extends ActivatedAbilityImpl {

    private final String manaString;

    public ReconfigureAbility(String manaString) {
        super(Zone.BATTLEFIELD, null);
        this.manaString = manaString;
        // TODO: Implement this
    }

    private ReconfigureAbility(final ReconfigureAbility ability) {
        super(ability);
        this.manaString = ability.manaString;
    }

    @Override
    public ReconfigureAbility copy() {
        return new ReconfigureAbility(this);
    }

    @Override
    public String getRule() {
        return "Reconfigure " + manaString + " (" + manaString
                + ": Attach to target creature you control; " +
                "or unattach from a creature. Reconfigure only as a sorcery. " +
                "While attached, this isn’t a creature.)";
    }
}
