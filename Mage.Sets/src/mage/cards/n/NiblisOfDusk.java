
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class NiblisOfDusk extends CardImpl {

    public NiblisOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private NiblisOfDusk(final NiblisOfDusk card) {
        super(card);
    }

    @Override
    public NiblisOfDusk copy() {
        return new NiblisOfDusk(this);
    }
}
