

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class EthercasteKnight extends CardImpl {

    public EthercasteKnight (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);


        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.addAbility(new ExaltedAbility());
    }

    private EthercasteKnight(final EthercasteKnight card) {
        super(card);
    }

    @Override
    public EthercasteKnight copy() {
        return new EthercasteKnight(this);
    }

}
