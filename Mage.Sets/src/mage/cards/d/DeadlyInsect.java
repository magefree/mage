
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DeadlyInsect extends CardImpl {

    public DeadlyInsect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
    }

    private DeadlyInsect(final DeadlyInsect card) {
        super(card);
    }

    @Override
    public DeadlyInsect copy() {
        return new DeadlyInsect(this);
    }
}
