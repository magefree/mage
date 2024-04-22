

package mage.cards.s;

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
public final class ScourgeServant extends CardImpl {

    public ScourgeServant (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(InfectAbility.getInstance());
    }

    private ScourgeServant(final ScourgeServant card) {
        super(card);
    }

    @Override
    public ScourgeServant copy() {
        return new ScourgeServant(this);
    }

}
