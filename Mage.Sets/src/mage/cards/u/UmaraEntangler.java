
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class UmaraEntangler extends CardImpl {

    public UmaraEntangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private UmaraEntangler(final UmaraEntangler card) {
        super(card);
    }

    @Override
    public UmaraEntangler copy() {
        return new UmaraEntangler(this);
    }
}
