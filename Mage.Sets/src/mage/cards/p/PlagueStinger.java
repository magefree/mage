

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PlagueStinger extends CardImpl {

    public PlagueStinger (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }

    private PlagueStinger(final PlagueStinger card) {
        super(card);
    }

    @Override
    public PlagueStinger copy() {
        return new PlagueStinger(this);
    }

}
