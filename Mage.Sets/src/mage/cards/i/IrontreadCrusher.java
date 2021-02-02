
package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class IrontreadCrusher extends CardImpl {

    public IrontreadCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private IrontreadCrusher(final IrontreadCrusher card) {
        super(card);
    }

    @Override
    public IrontreadCrusher copy() {
        return new IrontreadCrusher(this);
    }
}
