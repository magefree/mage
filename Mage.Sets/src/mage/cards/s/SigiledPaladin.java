

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SigiledPaladin extends CardImpl {

    public SigiledPaladin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new ExaltedAbility());
    }

    private SigiledPaladin(final SigiledPaladin card) {
        super(card);
    }

    @Override
    public SigiledPaladin copy() {
        return new SigiledPaladin(this);
    }
}
