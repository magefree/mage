
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SandbarCrocodile extends CardImpl {

    public SandbarCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Phasing
        this.addAbility(PhasingAbility.getInstance());
    }

    private SandbarCrocodile(final SandbarCrocodile card) {
        super(card);
    }

    @Override
    public SandbarCrocodile copy() {
        return new SandbarCrocodile(this);
    }
}
