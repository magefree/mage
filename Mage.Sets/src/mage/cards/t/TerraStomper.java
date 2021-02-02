
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class TerraStomper extends CardImpl {

    public TerraStomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantBeCounteredSourceEffect()));
        this.addAbility(TrampleAbility.getInstance());
    }

    private TerraStomper(final TerraStomper card) {
        super(card);
    }

    @Override
    public TerraStomper copy() {
        return new TerraStomper(this);
    }
}
