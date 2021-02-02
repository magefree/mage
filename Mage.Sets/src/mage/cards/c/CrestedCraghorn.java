
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CrestedCraghorn extends CardImpl {

    public CrestedCraghorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.GOAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Provoke
        this.addAbility(new ProvokeAbility());
    }

    private CrestedCraghorn(final CrestedCraghorn card) {
        super(card);
    }

    @Override
    public CrestedCraghorn copy() {
        return new CrestedCraghorn(this);
    }
}
