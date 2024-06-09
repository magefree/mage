package mage.abilities.keyword;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class DevoidAbility extends SimpleStaticAbility {

    public DevoidAbility(ObjectColor color) {
        super(Zone.ALL, null);
        color.setBlack(false);
        color.setWhite(false);
        color.setGreen(false);
        color.setBlue(false);
        color.setRed(false);
        setRuleAtTheTop(true);
    }

    protected DevoidAbility(final DevoidAbility ability) {
        super(ability);
    }

    @Override
    public DevoidAbility copy() {
        return new DevoidAbility(this);
    }

    @Override
    public String getRule() {
        return "Devoid <i>(This card has no color.)</i>";
    }

}
