package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.constants.SubType;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class WojekBodyguard extends CardImpl {

    public WojekBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mentor
        this.addAbility(new MentorAbility());

        // Wojek Bodyguard can't attack or block alone.
        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private WojekBodyguard(final WojekBodyguard card) {
        super(card);
    }

    @Override
    public WojekBodyguard copy() {
        return new WojekBodyguard(this);
    }
}
