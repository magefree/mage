package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class HammerDropper extends CardImpl {

    public HammerDropper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Mentor
        this.addAbility(new MentorAbility());
    }

    private HammerDropper(final HammerDropper card) {
        super(card);
    }

    @Override
    public HammerDropper copy() {
        return new HammerDropper(this);
    }
}
