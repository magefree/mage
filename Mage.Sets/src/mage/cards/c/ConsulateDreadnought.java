
package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class ConsulateDreadnought extends CardImpl {

    public ConsulateDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(11);

        // Crew 6
        this.addAbility(new CrewAbility(6));
    }

    private ConsulateDreadnought(final ConsulateDreadnought card) {
        super(card);
    }

    @Override
    public ConsulateDreadnought copy() {
        return new ConsulateDreadnought(this);
    }
}
