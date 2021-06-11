
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SpinelessThug extends CardImpl {

    public SpinelessThug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new CantBlockAbility());
    }

    private SpinelessThug(final SpinelessThug card) {
        super(card);
    }

    @Override
    public SpinelessThug copy() {
        return new SpinelessThug(this);
    }
}
