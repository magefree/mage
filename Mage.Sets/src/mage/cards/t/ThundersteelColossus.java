package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
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
public final class ThundersteelColossus extends CardImpl {

    public ThundersteelColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ThundersteelColossus(final ThundersteelColossus card) {
        super(card);
    }

    @Override
    public ThundersteelColossus copy() {
        return new ThundersteelColossus(this);
    }
}
