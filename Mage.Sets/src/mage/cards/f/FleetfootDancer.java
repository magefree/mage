package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FleetfootDancer extends CardImpl {

    public FleetfootDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private FleetfootDancer(final FleetfootDancer card) {
        super(card);
    }

    @Override
    public FleetfootDancer copy() {
        return new FleetfootDancer(this);
    }
}
