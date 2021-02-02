
package mage.cards.v;

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
public final class ViashinoSpearhunter extends CardImpl {

    public ViashinoSpearhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private ViashinoSpearhunter(final ViashinoSpearhunter card) {
        super(card);
    }

    @Override
    public ViashinoSpearhunter copy() {
        return new ViashinoSpearhunter(this);
    }
}
