package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.hint.common.ClassLevelHint;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class ClassReminderAbility extends StaticAbility {

    public ClassReminderAbility() {
        super(Zone.ALL, null);
        this.addHint(ClassLevelHint.instance);
    }

    private ClassReminderAbility(final ClassReminderAbility ability) {
        super(ability);
    }

    @Override
    public ClassReminderAbility copy() {
        return new ClassReminderAbility(this);
    }

    @Override
    public String getRule() {
        return "<i>(Gain the next level as a sorcery to add its ability.)</i>";
    }
}
