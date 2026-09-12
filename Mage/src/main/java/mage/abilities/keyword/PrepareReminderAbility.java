package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/** Rules-only reminder text for preparation cards. */
public class PrepareReminderAbility extends StaticAbility {

    public PrepareReminderAbility() {
        super(Zone.ALL, null);
    }

    private PrepareReminderAbility(final PrepareReminderAbility ability) {
        super(ability);
    }

    @Override
    public PrepareReminderAbility copy() {
        return new PrepareReminderAbility(this);
    }

    @Override
    public String getRule() {
        return "<i>While it's prepared, you may cast a copy of its spell. Doing so unprepares it.</i>";
    }
}
