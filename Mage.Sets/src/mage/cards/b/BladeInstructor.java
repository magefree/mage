package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class BladeInstructor extends CardImpl {

    public BladeInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Mentor
        this.addAbility(new MentorAbility());
    }

    private BladeInstructor(final BladeInstructor card) {
        super(card);
    }

    @Override
    public BladeInstructor copy() {
        return new BladeInstructor(this);
    }
}
