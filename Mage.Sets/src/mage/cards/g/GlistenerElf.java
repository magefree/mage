
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GlistenerElf extends CardImpl {

    public GlistenerElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(InfectAbility.getInstance());
    }

    private GlistenerElf(final GlistenerElf card) {
        super(card);
    }

    @Override
    public GlistenerElf copy() {
        return new GlistenerElf(this);
    }
}
