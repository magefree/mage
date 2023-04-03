package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.keyword.TransformAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class SiegeInfoAbility extends StaticAbility {

    public SiegeInfoAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addSubAbility(new TransformAbility());
    }

    private SiegeInfoAbility(final SiegeInfoAbility ability) {
        super(ability);
    }

    @Override
    public SiegeInfoAbility copy() {
        return new SiegeInfoAbility(this);
    }

    @Override
    public String getRule() {
        return "<i>(As a Siege enters, choose an opponent to protect it. You and others " +
                "can attack it. When it's defeated, exile it, then cast it transformed.)</i>";
    }
}
