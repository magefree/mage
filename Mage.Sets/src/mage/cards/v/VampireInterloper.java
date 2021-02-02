
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class VampireInterloper extends CardImpl {

    public VampireInterloper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        // Vampire Interloper can't block.
        this.addAbility(new CantBlockAbility());
    }

    private VampireInterloper(final VampireInterloper card) {
        super(card);
    }

    @Override
    public VampireInterloper copy() {
        return new VampireInterloper(this);
    }
}
