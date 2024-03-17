package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class BrotherhoodVertibird extends CardImpl {

    public BrotherhoodVertibird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(*);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Brotherhood Vertibird's power is equal to the number of artifacts you control.
        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private BrotherhoodVertibird(final BrotherhoodVertibird card) {
        super(card);
    }

    @Override
    public BrotherhoodVertibird copy() {
        return new BrotherhoodVertibird(this);
    }
}
