
package mage.cards.o;

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
 *
 * @author emerald000
 */
public final class OvalchaseDragster extends CardImpl {

    public OvalchaseDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private OvalchaseDragster(final OvalchaseDragster card) {
        super(card);
    }

    @Override
    public OvalchaseDragster copy() {
        return new OvalchaseDragster(this);
    }
}
