
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class AnabaBodyguard extends CardImpl {

    public AnabaBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private AnabaBodyguard(final AnabaBodyguard card) {
        super(card);
    }

    @Override
    public AnabaBodyguard copy() {
        return new AnabaBodyguard(this);
    }
}
