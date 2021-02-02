
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DauthiMarauder extends CardImpl {

    public DauthiMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.DAUTHI);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
    }

    private DauthiMarauder(final DauthiMarauder card) {
        super(card);
    }

    @Override
    public DauthiMarauder copy() {
        return new DauthiMarauder(this);
    }
}
