
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BloodrageVampire extends CardImpl {

    public BloodrageVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(new BloodthirstAbility(1));
    }

    private BloodrageVampire(final BloodrageVampire card) {
        super(card);
    }

    @Override
    public BloodrageVampire copy() {
        return new BloodrageVampire(this);
    }
}
