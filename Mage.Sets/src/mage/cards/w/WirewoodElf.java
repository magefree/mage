
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Melkhior
 */
public final class WirewoodElf extends CardImpl {
    public WirewoodElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new GreenManaAbility());
    }

    private WirewoodElf(final WirewoodElf card) {
        super(card);
    }

    @Override
    public WirewoodElf copy() {
        return new WirewoodElf(this);
    }
}