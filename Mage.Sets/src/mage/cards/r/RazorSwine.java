
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RazorSwine extends CardImpl {

    public RazorSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }

    private RazorSwine(final RazorSwine card) {
        super(card);
    }

    @Override
    public RazorSwine copy() {
        return new RazorSwine(this);
    }
}
