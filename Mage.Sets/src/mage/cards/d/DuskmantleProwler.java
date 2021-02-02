
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DuskmantleProwler extends CardImpl {

    public DuskmantleProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Exalted
        this.addAbility(new ExaltedAbility());
    }

    private DuskmantleProwler(final DuskmantleProwler card) {
        super(card);
    }

    @Override
    public DuskmantleProwler copy() {
        return new DuskmantleProwler(this);
    }
}
