
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BloodOgre extends CardImpl {

    public BloodOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.OGRE, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new BloodthirstAbility(1));
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private BloodOgre(final BloodOgre card) {
        super(card);
    }

    @Override
    public BloodOgre copy() {
        return new BloodOgre(this);
    }
}
