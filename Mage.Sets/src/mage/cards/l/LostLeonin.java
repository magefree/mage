

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LostLeonin extends CardImpl {

    public LostLeonin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
    }

    private LostLeonin(final LostLeonin card) {
        super(card);
    }

    @Override
    public LostLeonin copy() {
        return new LostLeonin(this);
    }

}
