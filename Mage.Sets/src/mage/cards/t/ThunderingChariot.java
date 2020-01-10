package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderingChariot extends CardImpl {

    public ThunderingChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private ThunderingChariot(final ThunderingChariot card) {
        super(card);
    }

    @Override
    public ThunderingChariot copy() {
        return new ThunderingChariot(this);
    }
}
