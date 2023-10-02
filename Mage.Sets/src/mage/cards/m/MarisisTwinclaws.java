

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MarisisTwinclaws extends CardImpl {

    public MarisisTwinclaws (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/W}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);



        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private MarisisTwinclaws(final MarisisTwinclaws card) {
        super(card);
    }

    @Override
    public MarisisTwinclaws copy() {
        return new MarisisTwinclaws(this);
    }

}
