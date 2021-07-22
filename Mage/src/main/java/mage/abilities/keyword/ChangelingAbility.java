package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.continuous.IsAllCreatureTypesSourceEffect;
import mage.constants.Zone;

/**
 * October 1, 2012
 * 702.71. Changeling
 * 702.71a Changeling is a characteristic-defining ability. "Changeling" means "This object
 * is every creature type." This ability works everywhere, even outside the game. See rule 604.3.
 * 702.71b Multiple instances of changeling on the same object are redundant.
 *
 * @author nantuko
 */
public class ChangelingAbility extends StaticAbility {

    private final boolean changelingText;

    public ChangelingAbility() {
        this(true);
    }

    public ChangelingAbility(boolean changelingText) {
        super(Zone.ALL, new IsAllCreatureTypesSourceEffect());
        this.changelingText = changelingText;
    }

    private ChangelingAbility(final ChangelingAbility ability) {
        super(ability);
        this.changelingText = ability.changelingText;
    }

    @Override
    public String getRule() {
        return changelingText ? "Changeling <i>(This card is every creature type.)</i>" : super.getRule();
    }

    @Override
    public ChangelingAbility copy() {
        return new ChangelingAbility(this);
    }
}
