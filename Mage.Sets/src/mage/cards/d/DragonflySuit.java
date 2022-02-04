package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonflySuit extends CardImpl {

    public DragonflySuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private DragonflySuit(final DragonflySuit card) {
        super(card);
    }

    @Override
    public DragonflySuit copy() {
        return new DragonflySuit(this);
    }
}
