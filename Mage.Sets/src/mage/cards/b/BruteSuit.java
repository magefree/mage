package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BruteSuit extends CardImpl {

    public BruteSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private BruteSuit(final BruteSuit card) {
        super(card);
    }

    @Override
    public BruteSuit copy() {
        return new BruteSuit(this);
    }
}
